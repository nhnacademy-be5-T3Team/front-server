
/*
 * TOAST UI Editor 생성 JavaScript 코드
 */
const bookIndexEditor = new toastui.Editor({
    el: document.querySelector('#bookIndexEditor'),
    previewStyle: 'vertical',
    height: '500px',
    initialValue: '도서 목차를 작성해주세요.'
});

const bookDescEditor = new toastui.Editor({
    el: document.querySelector('#bookDescEditor'),
    previewStyle: 'vertical',
    height: '500px',
    initialValue: '도서의 설명을 작성해주세요.'
});

/*
 * 도서 등록 페이지 유효성 검사
 */
document.addEventListener("DOMContentLoaded", function() {
    var form = document.querySelector('form');
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

    form.addEventListener('submit', function(event) {

        var selectedValue = getSelectedRadioValue();

        // 유효성 검사 통과 시 폼을 제출
        if (!isTitleValid() || !isIsbnValid() || !isPriceValid() || !isDiscountRateValid() || selectedValue == null) {
            event.preventDefault();
            return;
        }

        // 폼 데이터에 포장 가능 여부 추가
        var hiddenInputPackaging = document.createElement('input');
        hiddenInputPackaging.type = 'hidden';
        hiddenInputPackaging.id = 'packagingAvailableStatus';
        hiddenInputPackaging.name = 'packagingAvailableStatus';
        hiddenInputPackaging.value = selectedValue;
        this.appendChild(hiddenInputPackaging);
    });

    function getSelectedRadioValue() {
        // 선택된 라디오 버튼 요소 가져오기
        var selectedRadioButton = document.querySelector('input[name="gridRadios"]:checked');

        // 선택된 값이 있다면 해당 값 반환, 없다면 null 반환
        return selectedRadioButton ? selectedRadioButton.value : null;
    }

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

    function isPriceValid() {
        var price = priceInput.value.trim();
        // 숫자로만 이루어져 있는지 확인하는 정규표현식
        var regex = /^\d+$/;
        // 도서 정가가 정수값인지 확인
        if (!Number.isInteger(parseFloat(price)) || parseInt(price) < 0 || !regex.test(price)) {
            priceValidationMessage.textContent = "도서 정가는 정수값이어야 합니다.";
            priceValidationMessage.style.color = "red";
            return false;
        } else {
            priceValidationMessage.textContent = "";
            return true;
        }
    }

    function isDiscountRateValid() {
        var discountRate = discountRateInput.value.trim();
        // 할인율이 정수값이고 1~99 사이의 값인지 확인
        if (!Number.isInteger(parseFloat(discountRate)) || parseInt(discountRate) < 1 || parseInt(discountRate) > 99) {
            discountRateValidationMessage.textContent = "할인율은 정수값이고 1~99 사이의 값이어야 합니다.";
            discountRateValidationMessage.style.color = "red";
            return false;
        } else {
            discountRateValidationMessage.textContent = "";
            return true;
        }
    }
});

/*
 * Table Update 함수
 */

// 응답받은 데이터에 맞게 출판사 테이블의 내용을 업데이트하는 함수
function updatePublisherTable(data) {
    var tableBody = $('#publisherTable tbody');
    tableBody.empty();
    data.forEach(function(publisher) {
        var row = $('<tr>');
        // 각 행에 출판사 ID와 이름을 데이터로 저장
        row.data('id', publisher.publisherId);
        row.data('name', publisher.publisherName);

        var checkboxCell = $('<td>');
        var checkbox = $('<input>').attr('type', 'checkbox').attr('name', 'publisherCheckbox');
        // checkbox에 클릭 이벤트 핸들러 등록 (하나만 선택될 수 있도록)
        checkbox.on('click', function() {
            $('input[name="publisherCheckbox"]').not(this).prop('checked', false);
        });
        checkboxCell.append(checkbox);
        row.append(checkboxCell);
        row.append($('<td>').text(publisher.publisherId));
        row.append($('<td>').text(publisher.publisherName));
        row.append($('<td>').text(publisher.publisherEmail));
        tableBody.append(row);
    });
}

// 응답받은 데이터에 맞게 참여자 선택 테이블의 내용을 업데이트하는 함수
function updateParticipantTable(data) {
    var tableBody = $('#participantTable tbody');
    tableBody.empty();
    data.forEach(function(participant) {
        var row = $('<tr>');
        // 각 행에 참여자 ID와 이름을 데이터로 저장
        row.data('id', participant.id);
        row.data('name', participant.name);

        var checkboxCell = $('<td>');
        var checkbox = $('<input>').attr('type', 'checkbox').attr('name', 'participantCheckbox');
        // checkbox에 클릭 이벤트 핸들러 등록 (하나만 선택될 수 있도록)
        checkbox.on('click', function() {
            $('input[name="participantCheckbox"]').not(this).prop('checked', false);
        });
        checkboxCell.append(checkbox);
        row.append(checkboxCell);
        row.append($('<td>').text(participant.id));
        row.append($('<td>').text(participant.name));
        row.append($('<td>').text(participant.email));
        tableBody.append(row);
    });
}

// 응답받은 데이터에 맞게 참여자 역할 선택 테이블의 내용을 업데이트하는 함수
function updateParticipantRoleTable(data) {
    var tableBody = $('#participantRoleTable tbody');
    tableBody.empty();
    data.forEach(function(participantRole) {
        var row = $('<tr>');
        // 각 행에 참여자 역할 ID와 한국어 이름을 데이터로 저장
        row.data('id', participantRole.id);
        row.data('name', participantRole.roleNameKr);

        var checkboxCell = $('<td>');
        var checkbox = $('<input>').attr('type', 'checkbox').attr('name', 'participantRoleCheckbox');
        // checkbox에 클릭 이벤트 핸들러 등록 (하나만 선택될 수 있도록)
        checkbox.on('click', function() {
            $('input[name="participantRoleCheckbox"]').not(this).prop('checked', false);
        });
        checkboxCell.append(checkbox);
        row.append(checkboxCell);
        row.append($('<td>').text(participantRole.id));
        row.append($('<td>').text(participantRole.roleNameKr));
        row.append($('<td>').text(participantRole.roleNameEn));
        tableBody.append(row);
    });
}

// 응답받은 데이터에 맞게 카테고리 선택 테이블의 내용을 업데이트하는 함수
function updateCategoryTable(startDepth, maxDepth, data) {
    var tableHead = $('#categoryTable thead');
    var tableBody = $('#categoryTable tbody');

    // 첫 번째 열 :  checkbox
    var headerRow = $('<tr>');
    headerRow.append('<th>Checkbox</th>');

    // startDepth부터 maxDepth까지의 열을 추가
    for (var depth = startDepth; depth <= maxDepth; depth++) {
        headerRow.append('<th>' + depth + ' Depth</th>');
    }

    // thead에 행을 추가
    tableHead.empty().append(headerRow);

    tableBody.empty();

    // 데이터를 처리하는 재귀 함수
    function processCategories(categories, parentNames, parentId) {
        categories.forEach(function(category) {
            var row = $('<tr>');

            // 카테고리 id를 설정합니다.
            var categoryId = category.categoryId || parentId; // 자식 카테고리가 없는 경우 parentId를 사용
            row.attr('data-category-id', categoryId);
            row.attr('data-category-name', category.categoryName); // 카테고리 이름을 데이터로 추가

            // 각 행에 첫 번째 열에는 checkbox를 추가
            var checkboxCell = $('<td>');
            var checkbox = $('<input>').attr('type', 'checkbox').attr('name', 'categoryCheckbox');
            checkbox.on('click', function() {
                var index = $(this).closest('tr').attr('data-category-id');
                var value = $(this).closest('tr').attr('data-category-name');

                var selectedCategoriesCount = $('#selectedCategoryInModal h6').length;
                var isDuplicate = $('#selectedCategoryInModal').find('#' + index).length > 0;

                if($(this).is(':checked')) {
                    if (selectedCategoriesCount >= 10) {
                        alert('10개 이상의 태그를 선택할 수 없습니다.');
                        $(this).prop('checked', false);
                    } else if (isDuplicate) {
                        alert('이미 선택한 태그입니다.');
                        $(this).prop('checked', false);
                    } else {
                        $('#selectedCategoryInModal').append('<h6 id="' + index + '" value="'+ value  +'">' + index + ' | ' + value + '</h6>');
                    }
                } else {
                    $('#selectedCategoryInModal').find('#' + index).remove();
                }
            });
            checkboxCell.append(checkbox);
            row.append(checkboxCell);

            // 각 행에 카테고리 이름을 추가
            parentNames.forEach(function(parentName) {
                var parentCategoryCell = $('<td>').text(parentName);
                row.append(parentCategoryCell);
            });
            var categoryNameCell = $('<td>').text(category.categoryName);
            row.append(categoryNameCell);



            tableBody.append(row);

            // 해당 카테고리에 하위 항목이 있는 경우 재귀적으로 처리
            if (category.children && category.children.length > 0) {
                var newParentNames = parentNames.slice();
                newParentNames.push(category.categoryName);
                processCategories(category.children, newParentNames, categoryId);
            }
        });
    }
    processCategories(data, [], null);
}


// 응답받은 데이터에 맞게 태그 선택 테이블의 내용을 업데이트하는 함수
function updateTagTable(data) {
    var tableBody = $('#tagTable tbody');
    tableBody.empty();
    data.forEach(function(tag) {
        var row = $('<tr>');
        // 각 행에 참여자 ID와 이름을 데이터로 저장
        row.data('id', tag.id);
        row.data('name', tag.name);

        var checkboxCell = $('<td>');
        var checkbox = $('<input>').attr('type', 'checkbox').attr('name', 'tagCheckbox').attr('value', tag.name).attr('index', tag.id);
        checkbox.on('click', function() {
            var index = $(this).attr('index');
            var value = $(this).attr('value');
            var selectedTagsCount = $('#selectedTagInModal h6').length;
            var isDuplicate = $('#selectedTagInModal').find('#' + index).length > 0;

            if($(this).is(':checked')) {
                if (selectedTagsCount >= 10) {
                    alert('10개 이상의 태그를 선택할 수 없습니다.');
                    $(this).prop('checked', false);
                } else if (isDuplicate) {
                    alert('이미 선택한 태그입니다.');
                    $(this).prop('checked', false);
                } else {
                    $('#selectedTagInModal').append('<h6 id="' + index + '" value="'+ value  +'">' + index + ' | ' + value + '</h6>');
                }
            } else {
                $('#selectedTagInModal').find('#' + index).remove();
            }
        });
        checkboxCell.append(checkbox);
        row.append(checkboxCell);
        row.append($('<td>').text(tag.id));
        row.append($('<td>').text(tag.name));
        tableBody.append(row);
    });
}

/*
 * Pagination을 위한 전역 변수
 */
var currentPage = 0;
var pageSize = 10;

// 응답받은 데이터에 맞게 pagination 부분을 업데이트하는 함수
function updatePagination(name, totalPages, action) {
    // 매개변수로 받은 name에 해당하는 모달창의 pagination 부분을 가져온다.
    var pagination = $('#'+name+'_pagination');
    pagination.empty();

    var maxPageButtons = 10; // 최대 페이지 버튼 수
    var startPage = Math.max(1, currentPage - Math.floor(maxPageButtons / 2));
    var endPage = Math.min(totalPages, startPage + maxPageButtons - 1);

    var paginationList = $('<ul>').addClass('pagination');

    // Previous 버튼 추가
    var previousButton = $('<li>').addClass('page-item');
    var previousLink = $('<a>').addClass('page-link').attr('href', '#').text('Previous').click(function() {
        if (currentPage > 0) {
            currentPage--;
            action();
        }
    });
    previousButton.append(previousLink);
    paginationList.append(previousButton);

    // 페이지 버튼 추가
    for (var i = startPage; i <= endPage; i++) {
        var pageButton = $('<li>').addClass('page-item');
        var pageLink = $('<a>').addClass('page-link').attr('href', '#').text(i).click(function() {
            currentPage = parseInt($(this).text()) - 1;
            action();
        });
        if (i === currentPage + 1) {
            pageButton.addClass('active');
        }
        pageButton.append(pageLink);
        paginationList.append(pageButton);
    }

    // Next 버튼 추가
    var nextButton = $('<li>').addClass('page-item');
    var nextLink = $('<a>').addClass('page-link').attr('href', '#').text('Next').click(function() {
        if (currentPage < totalPages - 1) {
            currentPage++;
            action();
        }
    });
    nextButton.append(nextLink);
    paginationList.append(nextButton);

    pagination.append(paginationList);
}

/**
 * '도서 참여자 수 입력' 확인 버튼에 이벤트 리스너를 추가
 * 버튼 클릭시 입력 필드에서 참여자 수를 가져와 참여자 수 별로 '참여자 선택 버튼'과 '참여자 역할 선택 버튼'으로 이루어진 행을 동적으로 생성
 * '참여자 선택' 버튼을 클릭하면 {@link fetchParticipantsAndUpdateModal} 함수가 실행됨
 * '참여자 역할 선택' 버튼을 클릭하면 {@link fetchParticipantRolesAndUpdateModal} 함수가 실행됨
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

// 출판사 모달이 닫힐 때 선택된 항목을 form에 표시하는 함수
document.getElementById('publisherModal').addEventListener('hidden.bs.modal', function () {
    $('#selectedPublisher').empty();
    $('input[name="publisherCheckbox"]:checked').each(function() {
        var id = $(this).closest('tr').data('id');
        var name = $(this).closest('tr').data('name');
        $('#selectedPublisher').append('<h6>선택한 출판사</h6>')
        $('#selectedPublisher').append('<li class="list-group-item">' + id + " | " + name + '</li>');
    });
    currentPage = 0;
});

// 참여자 선택 모달이 닫힐 때 선택된 항목을 form에 표시하는 함수
document.getElementById('participantModal').addEventListener('hidden.bs.modal', function () {
    $('input[name="participantCheckbox"]:checked').each(function() {
        var id = $(this).closest('tr').data('id');
        var name = $(this).closest('tr').data('name');
        var index = $('#participantModal').data('index');

        var buttonCell = $('#participantButtonTable tr:eq(' + index + ') td:first');
        buttonCell.find('h6.selected-value-participant').remove();
        buttonCell.append('<h6 class="selected-value-participant" style="margin-top: 10px">' + id + " | " + name + '</h6>');
    });
    currentPage = 0;
});

// 참여자 역할 선택 모달이 닫힐 때 선택된 항목을 form에 표시하는 함수
document.getElementById('participantRoleModal').addEventListener('hidden.bs.modal', function () {
    $('input[name="participantRoleCheckbox"]:checked').each(function() {
        var id = $(this).closest('tr').data('id');
        var name = $(this).closest('tr').data('name');
        var index = $('#participantRoleModal').data('index');

        var buttonCell = $('#participantButtonTable tr:eq(' + index + ') td:nth-child(2)');
        buttonCell.find('h6.selected-value-participantRole').remove();
        buttonCell.append('<h6 class="selected-value-participantRole" style="margin-top: 10px">' + id + " | " + name + '</h6>');
    });
    currentPage = 0;
});

// 카테고리 선택 모달이 닫힐 때 선택된 항목을 form에 표시하는 함수
document.getElementById('categoryModal').addEventListener('hidden.bs.modal', function () {
    $('#selectedCategory').empty();

    $('#selectedCategoryInModal h6').each(function() {
        var id = $(this).attr('id');
        var value = $(this).attr('value');
        $('#selectedCategory').append('<li class="list-group-item">' + id + ' | ' + value + '</li>');
    });

    currentPage = 0;
});

// 태그 선택 모달이 닫힐 때 선택된 항목을 form에 표시하는 함수
document.getElementById('tagModal').addEventListener('hidden.bs.modal', function () {
    $('#selectedTag').empty();

    $('#selectedTagInModal h6').each(function() {
        var id = $(this).attr('id');
        var value = $(this).attr('value');
        $('#selectedTag').append('<li class="list-group-item">' + id + ' | ' + value + '</li>');
    });

    currentPage = 0;
});