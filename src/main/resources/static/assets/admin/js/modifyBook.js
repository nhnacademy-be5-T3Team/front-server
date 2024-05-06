document.addEventListener("DOMContentLoaded", function() {
    var bookModifyForm = document.getElementsByName('BookDetailModifyForm')[0];

    // 변경 전 값
    var beforeTitle = document.getElementById('beforeTitle').textContent;
    var beforeIndex = document.querySelector('#indexViewer').getAttribute('value');
    var beforeDesc = document.querySelector('#descViewer').getAttribute('value');
    var beforeIsbn = document.getElementById('beforeIsbn').textContent;
    var beforePrice = document.getElementById('beforePrice').textContent;
    var beforeDiscountRate = document.getElementById('beforeDiscountRate').textContent;
    var beforePackagingAvailableStatus = document.getElementById('beforePackagingAvailableStatus').getAttribute('value');
    var beforePublished = document.getElementById('beforePublished').textContent;
    var beforeStock = document.getElementById('beforeStock').textContent;

    // input 값
    var titleInput = document.getElementById('bookTitle');
    var isbnInput = document.getElementById('bookIsbn');
    var priceInput = document.getElementById('bookPrice');
    var discountRateInput = document.getElementById('bookDiscountRate');
    var publishedInput = document.getElementById('bookPublished');
    var stockInput = document.getElementById('bookStock');

    // validation message를 표시할 div 영역
    var titleValidationMessage = document.getElementById('titleValidationMessage');
    var isbnValidationMessage = document.getElementById('isbnValidationMessage');
    var priceValidationMessage = document.getElementById('priceValidationMessage');
    var discountRateValidationMessage = document.getElementById('discountRateValidationMessage');

    // form의 submit 버튼 클릭시 입력값 유효성 검사 후 폼에 데이터 추가해서 전송
    bookModifyForm.addEventListener('submit', function(event) {

        // 폼 유효성 검사
        var isFormValid = isTitleValid() && isIsbnValid() && isPriceValid() && isDiscountRateValid();

        // 유효성 검사 통과 시 폼을 제출
        if (!isFormValid) {
            event.preventDefault();
            return;
        }

        // 값이 변경되었다면 변경된 값을, 변경되지 않았다면 변경 이전의 값을 담음
        titleInput.value = titleInput.value === '' ? beforeTitle : titleInput.value;
        isbnInput.value = isbnInput.value === '' ? beforeIsbn : isbnInput.value;
        priceInput.value = priceInput.value === '' ? beforePrice : priceInput.value;
        discountRateInput.value = discountRateInput.value === '' ? beforeDiscountRate : discountRateInput.value;
        publishedInput.value = publishedInput.value === '' ? beforePublished : publishedInput.value;
        stockInput.value = stockInput.value === '' ? beforeStock : stockInput.value;

        var selectedValue = getSelectedRadioValue(); // 포장 여부
        var bookIndexInput = bookIndexEditor.getMarkdown().trim() === '' ? beforeIndex : bookIndexEditor.getMarkdown(); // 도서 목차
        var bookDescInput = bookDescEditor.getMarkdown().trim() === '' ? beforeDesc : bookDescEditor.getMarkdown(); // 도서 설명

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
        addHiddenInput(this, 'packagingAvailableStatus', 'packagingAvailableStatus', selectedValue);
        addHiddenInput(this, 'bookIndex', 'bookIndex', bookIndexInput);
        addHiddenInput(this, 'bookDesc', 'bookDesc', bookDescInput);

    });

    /**
     * 라디오 버튼 중 선택된 값을 반환하는 함수
     * @returns {string|null} 선택된 라디오 버튼의 값
     * @author Yujin-nKim(김유진)
     */
    function getSelectedRadioValue() {
        // 선택된 라디오 버튼 요소 가져오기
        var selectedRadioButton = document.querySelector('input[name="gridRadios"]:checked');
        // 선택된 값이 있다면 해당 값 반환, 없다면 기존값 반환
        return selectedRadioButton ? selectedRadioButton.value : beforePackagingAvailableStatus;
    }

    /**
     * 도서 제목 입력값의 유효성을 검사하고 결과를 반환
     * @returns {boolean} - 도서 제목의 유효성 여부
     * @author Yujin-nKim(김유진)
     */
    function isTitleValid() {
        var title = titleInput.value.trim();
        if (title !== '' && title.length > 255) {
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
        if (isbn !== '' && !/^\d{13}$/.test(isbn)) {
            isbnValidationMessage.textContent = "숫자로만 이루어진 13자리 숫자이어야 합니다.";
            isbnValidationMessage.style.color = "red";
            return false;
        }  else {
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
        if (price !== '' && !regex.test(price) || parseInt(price) < 0) {
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
        if (discountRate !== '' && !regex.test(discountRate) || parseInt(discountRate) < 1 || parseInt(discountRate) > 99) {
            discountRateValidationMessage.textContent = "할인율은 숫자로 이루어진 1 이상 99 이하의 정수값이어야 합니다.";
            discountRateValidationMessage.style.color = "red";
            return false;
        } else {
            discountRateValidationMessage.textContent = "";
            return true;
        }
    }
});

/**
 * '출판사 변경 요청' 버튼 클릭시 실행되는 이벤트 핸들러
 * @author Yujin-nKim(김유진)
 */
document.getElementById('modifyPublisherBtn').addEventListener('click', function() {
    if (!document.getElementById('selected-value-publisher')) {
        alert('출판사를 선택하세요.');
    } else {
        var publisherId = document.getElementById('selected-value-publisher').value;
        var bookId = document.getElementById('bookId').value;
        sendPublisherRequest(bookId, publisherId);
    }
});