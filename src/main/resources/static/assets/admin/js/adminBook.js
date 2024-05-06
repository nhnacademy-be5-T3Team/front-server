
/*
 * TOAST UI Editor 생성 JavaScript 코드
 */
var bookIndexEditor = new toastui.Editor({
    el: document.querySelector('#bookIndexEditor'),
    previewStyle: 'vertical',
    height: '500px',
});

var bookDescEditor = new toastui.Editor({
    el: document.querySelector('#bookDescEditor'),
    previewStyle: 'vertical',
    height: '500px',
});

var indexViewer = new toastui.Editor.factory({
    el: document.querySelector('#indexViewer'),
    viewer: true,
    initialValue: document.querySelector('#indexViewer').getAttribute('value')
});

var descViewer = new toastui.Editor.factory({
    el: document.querySelector('#descViewer'),
    viewer: true,
    initialValue: document.querySelector('#descViewer').getAttribute('value')
});


/*
 * 문서가 로드되었을 때 실행되는 이벤트 핸들러
 * 폼 데이터를 유효성 검사하고, 유효한 경우 폼을 제출
 * @author Yujin-nKim(김유진)
 */
document.addEventListener("DOMContentLoaded", function() {
    var bookRegisterForm = document.getElementsByName('BookRegisterForm')[0];
    // input 값
    var titleInput = document.getElementById('bookTitle');
    var isbnInput = document.getElementById('bookIsbn');
    var priceInput = document.getElementById('bookPrice');
    var discountRateInput = document.getElementById('bookDiscountRate');

    // validation message를 표시할 div 영역
    var titleValidationMessage = document.getElementById('titleValidationMessage');
    var isbnValidationMessage = document.getElementById('isbnValidationMessage');
    var priceValidationMessage = document.getElementById('priceValidationMessage');
    var discountRateValidationMessage = document.getElementById('discountRateValidationMessage');

    // form의 submit 버튼 클릭시 입력값 유효성 검사 후 폼에 데이터 추가해서 전송
    bookRegisterForm.addEventListener('submit', function(event) {
        var selectedValue = getSelectedRadioValue(); // 포장 여부 선택 값
        var bookIndexInput = bookIndexEditor.getMarkdown(); // 도서 목차 입력 값
        var bookDescInput = bookDescEditor.getMarkdown(); // 도서 설명 입력 값

        var publisherIdInput = document.getElementById('selected-value-publisher');
        var [isPublisherIdInputValid, publisherIdValue] = isPublisherIdValid(publisherIdInput);

        var count = document.getElementById('participantCount').value;
        var selectedParticipants = document.querySelectorAll('.selected-value-participant');
        var selectedParticipantRoles = document.querySelectorAll('.selected-value-participantRole');
        var [isParticipantMapInputValid, newParticipantList, newParticipantRoleList] = isParticipantMapValid(parseInt(count), selectedParticipants, selectedParticipantRoles);

        var selectedCategories = document.querySelectorAll('.selected-value-category');
        var [isCategoryInputValid, newCategoryList] = isCategoryValid(selectedCategories);

        var selectedTags = document.querySelectorAll('.selected-value-tag');
        var [isTagInputValid, newTagList] = isTagValid(selectedTags);

        // 폼 유효성 검사
        var isFormValid = isTitleValid() && isIsbnValid() && isPriceValid() && isDiscountRateValid() && selectedValue != null &&
            isBookIndexValid(bookIndexInput) && isBookDescValid(bookDescInput) && isPublisherIdInputValid && isParticipantMapInputValid &&
            isCategoryInputValid && isTagInputValid;

        // 유효성 검사 통과 시 폼을 제출
        if (!isFormValid) {
            event.preventDefault();
            return;
        }

        /**
         * 주어진 부모 요소에 hidden input 요소를 추가하는 함수
         * @param {HTMLElement} parent - hidden input 요소를 추가할 부모 요소
         * @param {string} id - hidden input 요소의 id 속성 값
         * @param {string} name - hidden input 요소의 name 속성 값
         * @param {string} value - hidden input 요소의 value 속성 값
         * @author Yujin-nKim(김유진)
         */
        function addHiddenInput(parent, id, name, value) {
            var hiddenInput = document.createElement('input');
            hiddenInput.type = 'hidden';
            hiddenInput.id = id;
            hiddenInput.name = name;
            hiddenInput.value = value;
            parent.appendChild(hiddenInput);
        }

        // 폼 데이터 추가
        addHiddenInput(this, 'packagingAvailableStatus', 'packagingAvailableStatus', selectedValue); // 포장 가능 여부
        addHiddenInput(this, 'bookIndex', 'bookIndex', bookIndexInput); // 도서 목차
        addHiddenInput(this, 'bookDesc', 'bookDesc', bookDescInput); // 도서 설명
        addHiddenInput(this, 'publisherId', 'publisherId', publisherIdValue); // 출판사 ID

        // participantMap List 추가
        for (var i = 0; i < parseInt(count); i++) {
            this.appendChild(newParticipantList[i]);
            this.appendChild(newParticipantRoleList[i]);
        }

        // categoryList 추가
        newCategoryList.forEach(function(categoryInput) {
            this.appendChild(categoryInput);
        }, this);

        // tagList 추가
        newTagList.forEach(function(tagInput) {
            this.appendChild(tagInput);
        }, this);

    });

    /**
     * 라디오 버튼 중 선택된 값을 반환하는 함수
     * @returns {string|null} 선택된 라디오 버튼의 값
     * @author Yujin-nKim(김유진)
     */
    function getSelectedRadioValue() {
        // 선택된 라디오 버튼 요소 가져오기
        var selectedRadioButton = document.querySelector('input[name="gridRadios"]:checked');
        // 선택된 값이 있다면 해당 값 반환, 없다면 null 반환
        return selectedRadioButton ? selectedRadioButton.value : null;
    }

    /**
     * 도서 제목 입력값의 유효성을 검사하고 결과를 반환
     * @returns {boolean} - 도서 제목의 유효성 여부
     * @author Yujin-nKim(김유진)
     */
    function isTitleValid() {
        var title = titleInput.value.trim();
        if (title === "") {
            titleValidationMessage.textContent = "도서 제목을 입력해주세요.";
            titleValidationMessage.style.color = "red";
            return false;
        } else if (title.length > 255) {
            titleValidationMessage.textContent = "도서 제목은 255자 이하여야 합니다.";
            titleValidationMessage.style.color = "red";
            return false;
        } else {
            titleValidationMessage.textContent = "";
            return true;
        }
    }

    /**
     * 도서 ISBN 입력값의 유효성을 검사하고 결과를 반환
     * @returns {boolean} - ISBN의 유효성 여부
     * @author Yujin-nKim(김유진)
     */
    function isIsbnValid() {
        var isbn = isbnInput.value.trim();
        // ISBN이 숫자로 이루어진 13자리의 숫자인지 확인
        if (!/^\d{13}$/.test(isbn)) {
            isbnValidationMessage.textContent = "숫자로만 이루어진 13자리 숫자이어야 합니다.";
            isbnValidationMessage.style.color = "red";
            return false;
        } else {
            isbnValidationMessage.textContent = "";
            return true;
        }
    }

    /**
     * 도서 정가 입력값의 유효성을 검사하고 결과를 반환
     * @returns {boolean} - 도서 정가의 유효성 여부
     * @author Yujin-nKim(김유진)
     */
    function isPriceValid() {
        var price = priceInput.value.trim();
        // 입력값이 숫자로 이루어져 있는지 확인하는 정규표현식
        var regex = /^\d+$/;

        if (!regex.test(price) || parseInt(price) < 0) {
            priceValidationMessage.textContent = "올바른 도서 정가를 입력해주세요.";
            priceValidationMessage.style.color = "red";
            return false;
        } else {
            priceValidationMessage.textContent = "";
            return true;
        }
    }

    /**
     * 도서 할인율 입력값의 유효성을 검사하고 결과를 반환
     * @returns {boolean} - 도서 할인율의 유효성 여부
     * @author Yujin-nKim(김유진)
     */
    function isDiscountRateValid() {
        var discountRate = discountRateInput.value.trim();
        // 입력값이 숫자로 이루어져 있는지 확인하는 정규표현식
        var regex = /^\d+$/;
        // 숫자로만 이루어져 있고 1 이상 99 이하의 값인지 확인
        if (!regex.test(discountRate) || parseInt(discountRate) < 1 || parseInt(discountRate) > 99) {
            discountRateValidationMessage.textContent = "할인율은 숫자로 이루어진 1 이상 99 이하의 정수값이어야 합니다.";
            discountRateValidationMessage.style.color = "red";
            return false;
        } else {
            discountRateValidationMessage.textContent = "";
            return true;
        }
    }

    /**
     * 도서 목차 입력값의 유효성을 검사하고 결과를 반환
     * @param {string} bookIndex - 도서 목차 입력 값
     * @returns {boolean} - 도서 목차의 유효성 여부
     * @author Yujin-nKim(김유진)
     */
    function isBookIndexValid(bookIndex) {
        if(bookIndex.trim() === '') {
            alert("도서 목차를 적어주세요.");
            return false;
        }else {
            return true;
        }
    }

    /**
     * 도서 설명 입력값의 유효성을 검사하고 결과를 반환
     * @param {string} bookDesc - 도서 설명 입력 값
     * @returns {boolean} - 도서 설명의 유효성 여부
     * @author Yujin-nKim(김유진)
     */
    function isBookDescValid(bookDesc) {
        if(bookDesc.trim() === '') {
            alert("도서 설명을 적어주세요.");
            return false;
        }else {
            return true;
        }
    }

    /**
     * 출판사 선택 입력값의 유효성을 검사하고 결과를 반환
     * @param {HTMLElement} publisherIdInput - 출판사 선택 입력 요소
     * @returns {[boolean, string]} - 출판사 ID의 유효성 여부와 값
     * @author Yujin-nKim(김유진)
     */
    function isPublisherIdValid(publisherIdInput) {
        if (publisherIdInput) {
            // publisherIdValue = publisherIdInput.split(',')[0];
            publisherIdValue = publisherIdInput.value;
            return [true, publisherIdValue];
        } else {
            alert("출판사를 선택해주세요.");
            event.preventDefault();
            return false;
        }
    }

    /**
     * 도서 참여자와 역할 매핑 입력값의 유효성을 검사하고, 유효한 경우 hidden input을 생성
     *
     * @param {number} count 도서 참여자 수
     * @param {NodeListOf<Element>} participantList 도서 참여자 목록
     * @param {NodeListOf<Element>} participantRoleList 도서 참여자 역할 목록
     * @returns {[boolean, Array, Array]} 유효성 여부, hidden input 리스트
     * @author Yujin-nKim(김유진)
     */
    function isParticipantMapValid(count, participantList, participantRoleList) {
        if (participantList.length !== count || participantRoleList.length !== count) {
            alert("도서 참여자를 선택해주세요.");
            event.preventDefault();
            return false;
        }

        var pairSet = new Set();
        var newParticipantList = [];
        var newParticipantRoleList = [];
        // participantList와 participantRoleList를 돌면서 각 요소를 짝지어서 확인
        for (var i = 0; i < count; i++) {

            var participantValue = participantList[i].value;
            var participantRoleValue = participantRoleList[i].value;
            var pair = participantValue + ':' + participantRoleValue;

            // 이미 존재하는 짝인지 확인하고 있다면 false를 반환
            if (pairSet.has(pair)) {
                alert("도서 참여자에 중복된 값이 존재합니다.");
                event.preventDefault();
                return false;
            }
            // 셋에 짝을 추가
            pairSet.add(pair);

            // hidden 타입의 input 태그 생성 및 새로운 리스트에 추가
            var participantInput = document.createElement('input');
            participantInput.type = 'hidden';
            participantInput.id = "participantId" + i;
            participantInput.name = 'participantMapList[' + i + '].participantId';
            participantInput.value = participantValue;
            newParticipantList.push(participantInput);

            var participantRoleInput = document.createElement('input');
            participantRoleInput.type = 'hidden';
            participantRoleInput.id = "participantRoleId" + i;
            participantRoleInput.name = 'participantMapList[' + i + '].participantRoleId';
            participantRoleInput.value = participantRoleValue;
            newParticipantRoleList.push(participantRoleInput);
        }
        return [true, newParticipantList, newParticipantRoleList];
    }

    /**
     * 카테고리 선택 입력값의 유효성을 검사하고, 유효한 경우 hidden input을 생성
     * @param {NodeListOf<Element>} categoryList 선택된 카테고리 목록
     * @returns {[boolean, Array]} 유효성 여부, hidden input 리스트
     * @author Yujin-nKim(김유진)
     */
    function isCategoryValid(categoryList) {
        if (categoryList.length == 0 ) {
            alert("카테고리를 선택해주세요.");
            event.preventDefault();
            return false;
        }

        var newCategoryList = [];
        for(var i = 0; i < categoryList.length; i++) {
            var categoryValue = categoryList[i].value;

            var categoryInput = document.createElement('input');
            categoryInput.type = 'hidden';
            categoryInput.id = 'categoryId' + i;
            categoryInput.name = 'categoryList[' + i + ']';
            categoryInput.value = categoryValue;
            newCategoryList.push(categoryInput);
        }

        return[true, newCategoryList];
    }

    /**
     * 태그 선택 입력값의 유효성을 검사하고, 유효한 경우 hidden input을 생성
     * @param {NodeListOf<Element>} tagList 선택된 태그 목록
     * @returns {[boolean, Array]} 유효성 여부, hidden input 리스트
     * @author Yujin-nKim(김유진)
     */
    function isTagValid(tagList) {
        if (tagList.length == 0 ) {
            alert("태그를 선택해주세요.");
            event.preventDefault();
            return false;
        }

        var newTagList = [];
        for(var i = 0; i < tagList.length; i++) {
            var tagValue = tagList[i].value;

            var tagInput = document.createElement('input');
            tagInput.type = 'hidden';
            tagInput.id = 'tagId' + i;
            tagInput.name = 'tagList[' + i + ']';
            tagInput.value = tagValue;
            newTagList.push(tagInput);
        }

        return[true, newTagList];
    }
});


/**
 * 확인 버튼을 클릭했을 때 실행되는 이벤트 핸들러
 * 참여자 선택 버튼과 참여자 역할 선택 버튼을 생성하고 테이블에 추가
 * @author Yujin-nKim(김유진)
 */
document.getElementById('confirmBtn').addEventListener('click', function() {

    var count = document.getElementById('participantCount').value;
    var tableBody = document.getElementById('participantButtonTable').getElementsByTagName('tbody')[0];
    tableBody.innerHTML = '';

    for (var i = 1; i <= count; i++) {
        var row = document.createElement('tr');
        // 첫 번째 열 : 참여자 선택 버튼
        var participantCell = document.createElement('td');
        var participantButton = document.createElement('button');
        participantButton.type = 'button';
        participantButton.id = 'participantButton_' + i;
        participantButton.innerText = i + '번 참여자 선택';
        participantButton.classList.add('btn', 'btn-secondary');
        participantButton.dataset.bsToggle = 'modal';
        participantButton.dataset.bsTarget = '#participantModal';
        participantButton.dataset.index = i;
        participantButton.addEventListener('click', function() {
            $('#participantModal').data('index', this.dataset.index);
            fetchParticipantsAndUpdateModal();
        });
        participantCell.appendChild(participantButton);
        row.appendChild(participantCell);

        // 두 번째 열 : 참여자 역할 선택 버튼
        var roleCell = document.createElement('td');
        var roleButton = document.createElement('button');
        roleButton.type = 'button';
        roleButton.id = 'participantRoleButton_' + i;
        roleButton.innerText = i + '번 참여자 역할 선택';
        roleButton.classList.add('btn', 'btn-secondary');
        roleButton.dataset.bsToggle = 'modal';
        roleButton.dataset.bsTarget = '#participantRoleModal';
        roleButton.dataset.index = i;
        roleButton.addEventListener('click', function() {
            $('#participantRoleModal').data('index', this.dataset.index);
            fetchParticipantRolesAndUpdateModal();
        });
        roleCell.appendChild(roleButton);
        row.appendChild(roleCell);

        tableBody.appendChild(row);
    }
});


// '출판사 선택' 버튼 클릭 이벤트 핸들러
document.getElementById('openPublisherModal').addEventListener('click', function() {
    fetchPublishersAndUpdateModal();
})

// '카테고리 선택' 버튼 클릭 이벤트 핸들러
document.getElementById('openCategoryModal').addEventListener('click', function() {
    fetchCategoriesAndUpdateModal(1, 2);
})

// '태그 선택' 버튼 클릭 이벤트 핸들러
document.getElementById('openTagModal').addEventListener('click', function() {
    fetchTagsAndUpdateModal();
})


/*
 * '도서 관련 정보 선택' 모달이 닫힐때 실행되는 함수
 */

/**
 * 출판사 선택 모달이 숨겨질 때 실행되는 이벤트 핸들러
 * 선택된 출판사를 표시하는 컨테이너를 업데이트
 * @author Yujin-nKim(김유진)
 */
document.getElementById('publisherModal').addEventListener('hidden.bs.modal', function () {
    const selectedPublisherContainer = $('#selectedPublisher');
    selectedPublisherContainer.empty();

    $('input[name="publisherCheckbox"]:checked').each(function() {
        const $checkbox = $(this);
        const id = $checkbox.closest('tr').data('id'); // 선택한 출판사 항목의 id
        const name = $checkbox.closest('tr').data('name'); // 선택한 출판사 항목의 name

        selectedPublisherContainer.append(`
            <h6>선택한 출판사</h6>
            <li id="selected-value-publisher" class="list-group-item" value="${id}">${id} | ${name}</li>
        `);
    });
    currentPage = 0;
});

/**
 * 참여자 선택 모달이 숨겨질 때 실행되는 이벤트 핸들러
 * 선택된 참여자를 표시하는 컨테이너를 업데이트
 * @author Yujin-nKim(김유진)
 */
document.getElementById('participantModal').addEventListener('hidden.bs.modal', function () {
    // '선택' 버튼을 눌렀던 자리에 선택한 데이터 표시
    const index = $('#participantModal').data('index');
    const buttonCell = $('#participantButtonTable tr:eq(' + index + ') td:first');
    buttonCell.find('li.selected-value-participant').remove();

    $('input[name="participantCheckbox"]:checked').each(function() {
        const $checkbox = $(this);
        const id = $checkbox.closest('tr').data('id'); // 선택한 참여자 항목의 id
        const name = $checkbox.closest('tr').data('name'); // 선택한 참여자 항목의 name

        const button = buttonCell.find('button');
        let buttonText = button.text();
        buttonText = buttonText.replace('선택', '변경');
        button.text(buttonText);

        buttonCell.append(`
            <li class="selected-value-participant" value="${id}" style="margin-top: 10px">${id} | ${name}</li>
        `);
    });
    currentPage = 0;
});

/**
 * 참여자 역할 선택 모달이 숨겨질 때 실행되는 이벤트 핸들러
 * 선택된 참여자 역할을 표시하는 컨테이너를 업데이트
 * @author Yujin-nKim(김유진)
 */
document.getElementById('participantRoleModal').addEventListener('hidden.bs.modal', function () {
    // '선택' 버튼을 눌렀던 자리에 선택한 데이터 표시
    const index = $('#participantRoleModal').data('index');
    const buttonCell = $('#participantButtonTable tr:eq(' + index + ') td:nth-child(2)');
    buttonCell.find('li.selected-value-participantRole').remove();

    $('input[name="participantRoleCheckbox"]:checked').each(function() {
        const $checkbox = $(this);
        const id = $checkbox.closest('tr').data('id'); // 선택한 참여자 역할 항목의 id
        const name = $checkbox.closest('tr').data('name'); // 선택한 참여자 역할 항목의 name

        const button = buttonCell.find('button');
        let buttonText = button.text();
        buttonText = buttonText.replace('선택', '변경');
        button.text(buttonText);

        buttonCell.append(`
            <li class="selected-value-participantRole" value="${id}" style="margin-top: 10px">${id} | ${name}</li>
        `);
    });
    currentPage = 0;
});

/**
 * 카테고리 모달이 숨겨질 때 실행되는 이벤트 핸들러
 * 선택된 카테고리를 표시하는 컨테이너를 업데이트
 * @author Yujin-nKim(김유진)
 */
document.getElementById('categoryModal').addEventListener('hidden.bs.modal', function () {
    const selectedCategoryContainer = $('#selectedCategory');
    selectedCategoryContainer.empty();

    if($('#selectedCategoryInModal h6').length != 0) {
        selectedCategoryContainer.append('<h6>선택한 카테고리</h6>');
    }

    $('#selectedCategoryInModal h6').each(function() {
        const $item = $(this);
        const id = $item.attr('id'); // 선택한 카테고리 항목의 id
        const name = $item.attr('value'); // 선택한 카테고리 항목의 name

        selectedCategoryContainer.append(`
            <li class="selected-value-category list-group-item" value="${id}">${id} | ${name}</li>
        `);
    });
    currentPage = 0;
});

/**
 * 태그 모달이 숨겨질 때 실행되는 이벤트 핸들러
 * 선택된 태그를 표시하는 컨테이너를 업데이트
 * @author Yujin-nKim(김유진)
 */
document.getElementById('tagModal').addEventListener('hidden.bs.modal', function () {
    const selectedTagContainer = $('#selectedTag');
    selectedTagContainer.empty();

    if($('#selectedTagInModal h6').length != 0) {
        selectedTagContainer.append('<h6>선택한 태그</h6>');
    }

    $('#selectedTagInModal h6').each(function() {
        const $item = $(this);
        const id = $item.attr('id'); // 선택한 태그 항목의 id
        const name = $item.attr('value'); // 선택한 태그 항목의 name

        selectedTagContainer.append(`
            <li class="selected-value-tag list-group-item" value="${id}">${id} | ${name}</li>
        `);
    });
    currentPage = 0;
});