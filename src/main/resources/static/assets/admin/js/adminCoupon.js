/**
 * 확인 버튼을 클릭했을 때 실행되는 이벤트 핸들러
 * 회원 선택 버튼과 쿠폰 선택 버튼을 생성하고 테이블에 추가
 * @author joohyun1996(이주현)
 */
document.getElementById('confirmBtn').addEventListener('click', function () {

    var count = document.getElementById('memberCount').value;
    var tableBody = document.getElementById('memberButtonTable').getElementsByTagName('tbody')[0];
    tableBody.innerHTML = '';

    for (var i = 1; i <= count; i++) {
        var row = document.createElement('tr');
        // 첫 번째 열 : 참여자 선택 버튼
        var memberCell = document.createElement('td');
        var memberButton = document.createElement('button');
        memberButton.type = 'button';
        memberButton.id = 'participantButton_' + i;
        memberButton.innerText = '회원 선택';
        memberButton.classList.add('btn', 'btn-secondary');
        memberButton.dataset.bsToggle = 'modal';
        memberButton.dataset.bsTarget = '#memberModal';
        memberButton.dataset.index = i;
        memberButton.addEventListener('click', function () {
            $('#memberModal').data('index', this.dataset.index);
        });
        memberCell.appendChild(memberButton);
        row.appendChild(memberCell);

        // 두 번째 열 : 쿠폰 선택 버튼
        var couponCell = document.createElement('td');
        var couponButton = document.createElement('button');
        couponButton.type = 'button';
        couponButton.id = 'couponButton_' + i;
        couponButton.innerText = '쿠폰 선택';
        couponButton.classList.add('btn', 'btn-secondary');
        couponButton.dataset.bsToggle = 'modal';
        couponButton.dataset.bsTarget = '#couponModal';
        couponButton.dataset.index = i;
        couponButton.addEventListener('click', function () {
            $('#couponModal').data('index', this.dataset.index);
            fetchCouponsAndUpdateModal();
        });
        couponCell.appendChild(couponButton);
        row.appendChild(couponCell);

        tableBody.appendChild(row);

        // 세 번째 열 : 선택 버튼
        var selectCell = document.createElement('td');
        var selectButton = document.createElement('button');
        selectButton.type = 'button';
        selectButton.id = 'selectButton_' + i;
        selectButton.innerText = '등록';
        selectButton.classList.add('btn', 'btn-success');
        selectButton.addEventListener('click', function () {
            var selectedMember = document.querySelector('#memberTable input[type="radio"]:checked');
            var selectedCoupon = document.querySelector('#couponTable input[type="radio"]:checked');

            if (selectedMember && selectedCoupon) {
                // 선택된 회원의 이름과 쿠폰의 종류를 가져옵니다.
                var memberId = selectedMember.closest('tr').children[1].innerText;
                var couponType = selectedCoupon.closest('tr').children[1].innerText;

                // 쿠폰의 종류에 따라 다른 URL로 POST 요청을 보냅니다.
                var url = '/coupon/' + couponType + '/' + memberId;
                sendPostRequest(url, { index: i });
            } else {
                console.log(selectedMember);
                console.log(selectedCoupon);
                alert('회원과 쿠폰을 선택해주세요.');
            }
        });
        selectCell.appendChild(selectButton);
        row.appendChild(selectCell);

        tableBody.appendChild(row);
    }
});

document.getElementById('searchButton').addEventListener('click', function () {
    var searchInput = document.getElementById('memberSearchInput').value;

    // 여기서 searchInput을 사용하여 회원을 검색합니다.
    // 이 예제에서는 검색 결과를 담은 배열인 searchResults를 사용하겠습니다.
    fetch('/members?name=' + searchInput, {
        method: "GET",
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(response => {
            if (!response.ok) {
                throw new Error("Network response not ok")
            }
            let a = response.data;
            return response.json();
        })
        .then(data => {
            console.log(data)
            let list = data.content;
            console.log(list)
            var tableBody = document.getElementById('memberTable').getElementsByTagName('tbody')[0];
            tableBody.innerHTML = '';

            if(Array.isArray(data)) {
                data.forEach(result => {
                    var row = document.createElement('tr');

                    var radioCell = document.createElement('td');
                    var radioButton = document.createElement('input');
                    radioButton.type = 'radio';
                    radioButton.name = "memberName";
                    radioCell.appendChild(radioButton);
                    row.appendChild(radioCell);

                    var idCell = document.createElement('td');
                    idCell.innerText = result.id;
                    row.appendChild(idCell);

                    var nameCell = document.createElement('td');
                    nameCell.innerText = result.name;
                    row.appendChild(nameCell);

                    var emailCell = document.createElement('td');
                    emailCell.innerText = result.email;
                    row.appendChild(emailCell);

                    tableBody.appendChild(row);
                });
            }
        })
        .catch(error => console.error('Error:', error));
});

function fetchCouponsAndUpdateModal() {
    var tableBody = document.getElementById('couponTable').getElementsByTagName('tbody')[0];
    tableBody.innerHTML = '';

    var couponTypes = ['general_coupon', 'book_coupon', 'category_coupon'];

    couponTypes.forEach((couponType, index) => {
        var row = document.createElement('tr');

        var radioCell = document.createElement('td');
        var radioButton = document.createElement('input');
        radioButton.type = 'radio';
        radioButton.name = "couponType";
        radioCell.appendChild(radioButton);
        row.appendChild(radioCell);

        var couponNameCell = document.createElement('td');
        couponNameCell.innerText = couponType;
        row.appendChild(couponNameCell);
        tableBody.appendChild(row);
    });
}

function sendPostRequest(url, data) {
    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
    .then(response => response.json())
    .then(data => console.log(data))
    .catch(error => console.error('Error:', error));
}