
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
