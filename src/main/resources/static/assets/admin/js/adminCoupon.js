/**
 * 확인 버튼을 클릭했을 때 실행되는 이벤트 핸들러
 * 회원 선택 버튼과 쿠폰 선택 버튼을 생성하고 테이블에 추가
 * @author joohyun1996(이주현)
 */
document.getElementById('confirmBtn').addEventListener('click', function() {

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
        memberButton.innerText = i + '번 회원 선택';
        memberButton.classList.add('btn', 'btn-secondary');
        memberButton.dataset.bsToggle = 'modal';
        memberButton.dataset.bsTarget = '#memberModal';
        memberButton.dataset.index = i;
        memberButton.addEventListener('click', function() {
            $('#memberModal').data('index', this.dataset.index);
            fetchParticipantsAndUpdateModal();
        });
        memberCell.appendChild(memberButton);
        row.appendChild(memberCell);

        // 두 번째 열 : 참여자 역할 선택 버튼
        var couponCell = document.createElement('td');
        var couponButton = document.createElement('button');
        couponButton.type = 'button';
        couponButton.id = 'couponButton_' + i;
        couponButton.innerText = i + '번 쿠폰 선택';
        couponButton.classList.add('btn', 'btn-secondary');
        couponButton.dataset.bsToggle = 'modal';
        couponButton.dataset.bsTarget = '#couponModal';
        couponButton.dataset.index = i;
        couponButton.addEventListener('click', function() {
            $('#couponModal').data('index', this.dataset.index);
            fetchParticipantRolesAndUpdateModal();
        });
        couponCell.appendChild(couponButton);
        row.appendChild(couponCell);

        tableBody.appendChild(row);
    }
});

document.getElementById('searchButton').addEventListener('click', function() {
    var searchInput = document.getElementById('memberSearchInput').value;

    // 여기서 searchInput을 사용하여 회원을 검색합니다.
    // 이 예제에서는 검색 결과를 담은 배열인 searchResults를 사용하겠습니다.
    fetch('/at/bookstore/members?name=' + searchInput)
        .then(response => response.json())
        .then(searchResults => {
            var tableBody = document.getElementById('memberTable').getElementsByTagName('tbody')[0];
            tableBody.innerHTML = '';

            for (var i = 0; i < searchResults.length; i++) {
                var row = document.createElement('tr');

                var checkBoxCell = document.createElement('td');
                checkBoxCell.innerText = searchResults[i].checkBox;
                row.appendChild(checkBoxCell);

                var nameCell = document.createElement('td');
                nameCell.innerText = searchResults[i].name;
                row.appendChild(nameCell);

                var emailCell = document.createElement('td');
                emailCell.innerText = searchResults[i].email;
                row.appendChild(emailCell);

                tableBody.appendChild(row);
            }
        })
        .catch(error => console.error('Error:', error));
});

document.getElementById('searchForm').addEventListener('submit', function(event) {
    // 기본 form 제출 동작을 막습니다.
    event.preventDefault();

    // 입력된 회원 이름을 가져옵니다.
    var memberName = document.getElementById('memberNameInput').value;

    // 회원 이름으로 쿠폰 이력을 검색합니다.
    fetch('/at/bookstore/member/{memberId}' + encodeURIComponent(memberName))
        .then(response => response.json())
        .then(data => {
            var tableBody = document.getElementById('couponHistoryTable').getElementsByTagName('tbody')[0];
            tableBody.innerHTML = '';

            data.forEach(item => {
                var row = document.createElement('tr');

                var couponNumberCell = document.createElement('td');
                couponNumberCell.textContent = item.couponNumber;
                row.appendChild(couponNumberCell);

                var memberNameCell = document.createElement('td');
                memberNameCell.textContent = item.memberName;
                row.appendChild(memberNameCell);

                var memberEmailCell = document.createElement('td');
                memberEmailCell.textContent = item.memberEmail;
                row.appendChild(memberEmailCell);

                var orderNumberCell = document.createElement('td');
                orderNumberCell.textContent = item.orderNumber;
                row.appendChild(orderNumberCell);

                var productNameCell = document.createElement('td');
                productNameCell.textContent = item.productName;
                row.appendChild(productNameCell);

                tableBody.appendChild(row);
            });
        })
        .catch(error => console.error('Error:', error));
});