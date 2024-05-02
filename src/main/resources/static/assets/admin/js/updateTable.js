
/*
 * Table Update 함수
 */

/**
 * 응답받은 데이터에 따라 출판사 테이블의 내용을 업데이트하는 함수
 *
 * @param {Array} data 출판사 데이터 배열
 * 각 출판사 객체는 다음 속성을 포함
 * - publisherId: 출판사 ID
 * - publisherName: 출판사 이름
 * - publisherEmail: 출판사 이메일
 * @author Yujin-nKim(김유진)
 */
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

/**
 * 응답받은 데이터에 따라 참여자 선택 테이블의 내용을 업데이트하는 함수
 *
 * @param {Array} data 참여자 데이터 배열
 * 각 참여자 객체는 다음 속성을 포함
 * - id: 참여자 ID
 * - name: 참여자 이름
 * - email: 참여자 이메일
 * @author Yujin-nKim(김유진)
 */
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

/**
 * 응답받은 데이터에 따라 참여자 역할 테이블의 내용을 업데이트하는 함수
 *
 * @param {Array} data 참여자 역할 데이터 배열
 * 각 참여자 역할 객체는 다음 속성을 포함
 * - id: 참여자 역할 ID
 * - roleNameKr: 참여자 역할 한국어 이름
 * - roleNameEn: 참여자 역할 영어 이름
 * @author Yujin-nKim(김유진)
 */
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

/**
 * 응답받은 데이터에 따라 카테고리 테이블의 내용을 업데이트하는 함수
 *
 * @param {number} startDepth 테이블에 표시할 카테고리의 시작 깊이
 * @param {number} maxDepth 테이블에 표시할 최대 카테고리 깊이
 * @param {Array} data 카테고리 데이터 배열
 * 각 카테고리 객체는 다음 속성을 포함
 * - categoryId: 카테고리 ID
 * - categoryName: 카테고리 이름
 * - children: 하위 카테고리 배열 (선택 사항)
 * @author Yujin-nKim(김유진)
 */
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

/**
 * 응답받은 데이터에 따라 태그 테이블의 내용을 업데이트하는 함수
 *
 * @param {Array} data 태그 데이터 배열
 * 각 태그 객체는 다음 속성을 포함
 * - id: 태그 ID
 * - name: 태그 이름
 * @author Yujin-nKim(김유진)
 */
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
var currentPage = 0; // 현재 페이지 번호
var pageSize = 10; // 페이지당 항목 수

/**
 * 응답받은 데이터에 따라 Pagination 부분을 업데이트하는 함수
 *
 * @param {string} name Pagination을 업데이트할 대상의 이름
 * @param {number} totalPages 전체 페이지 수
 * @param {Function} action Pagination 버튼을 클릭할 때 실행될 액션
 * @author Yujin-nKim(김유진)
 */
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