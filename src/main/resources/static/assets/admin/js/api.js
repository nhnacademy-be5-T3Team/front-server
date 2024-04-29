
/*
 * Bookstore API 호출 함수
 */

// 출판사 목록 요청을 보내는 함수
function fetchPublishersAndUpdateModal() {
    $.ajax({
        url: 'http://localhost:8081/publishers',
        type: 'GET',
        data: {
            pageNo: currentPage,
            pageSize: pageSize
        },
        success: function(response, textStatus, xhr) {
            if (xhr.status === 200) {
                // 성공적인 응답인 경우
                if (response.data.content.length > 0) {
                    // 데이터가 있는 경우 테이블 업데이트
                    updatePublisherTable(response.data.content);
                    // 페이징 컨트롤 업데이트
                    updatePagination('publisher', response.data.totalPages, fetchPublishersAndUpdateModal);
                } else {
                    console.warn('No content found.');
                }
            } else if (xhr.status === 204) {
                console.warn('No content found.');
            }
        },
        error: function(xhr, status, error) {
            console.error('Error:', error);
        }
    });
}

// 참여자 목록 요청을 보내는 함수
function fetchParticipantsAndUpdateModal() {
    $.ajax({
        url: 'http://localhost:8081/participants',
        type: 'GET',
        data: {
            pageNo: currentPage,
            pageSize: pageSize
        },
        success: function(response, textStatus, xhr) {
            if (xhr.status === 200) {
                // 성공적인 응답인 경우
                if (response.data.content.length > 0) {
                    // 데이터가 있는 경우 테이블 업데이트
                    updateParticipantTable(response.data.content);
                    // 페이징 컨트롤 업데이트
                    updatePagination('participant', response.data.totalPages, fetchParticipantsAndUpdateModal);
                } else {
                    console.warn('No content found.');
                }
            } else if (xhr.status === 204) {
                console.warn('No content found.');
            }
        },
        error: function(xhr, status, error) {
            console.error('Error:', error);
        }
    });
}

// 참여자 역할 목록 요청을 보내는 함수
function fetchParticipantRolesAndUpdateModal() {
    $.ajax({
        url: 'http://localhost:8081/participantRoles',
        type: 'GET',
        data: {
            pageNo: currentPage,
            pageSize: pageSize
        },
        success: function(response, textStatus, xhr) {
            if (xhr.status === 200) {
                // 성공적인 응답인 경우
                if (response.data.content.length > 0) {
                    // 데이터가 있는 경우 테이블 업데이트
                    updateParticipantRoleTable(response.data.content);
                    // 페이징 컨트롤 업데이트
                    updatePagination('participantRole', response.data.totalPages, fetchParticipantRolesAndUpdateModal);
                } else {
                    console.warn('No content found.');
                }
            } else if (xhr.status === 204) {
                console.warn('No content found.');
            }
        },
        error: function(xhr, status, error) {
            console.error('Error:', error);
        }
    });
}

// 카테고리 목록 요청을 보내는 함수
function fetchCategoriesAndUpdateModal(startDepth, maxDepth) {
    $.ajax({
        url: 'http://localhost:8081/categories',
        type: 'GET',
        data: {
            startDepth: startDepth,
            maxDepth: maxDepth
        },
        success: function(response, textStatus, xhr) {
            if (xhr.status === 200) {
                // 성공적인 응답인 경우
                if (response.data.length > 0) {
                    updateCategoryTable(startDepth, maxDepth, response.data);
                } else {
                    console.warn('No content found.');
                }
            } else if (xhr.status === 204) {
                console.warn('No content found.');
            }
        },
        error: function(xhr, status, error) {
            console.error('Error:', error);
        }
    });
}

// 태그 목록 요청을 보내는 함수
function fetchTagsAndUpdateModal() {
    $.ajax({
        url: 'http://localhost:8081/tags',
        type: 'GET',
        data: {
            pageNo: currentPage,
            pageSize: pageSize
        },
        success: function(response, textStatus, xhr) {
            if (xhr.status === 200) {
                // 성공적인 응답인 경우
                if (response.data.content.length > 0) {
                    // 데이터가 있는 경우 테이블 업데이트
                    updateTagTable(response.data.content);
                    // 페이징 컨트롤 업데이트
                    updatePagination('tag', response.data.totalPages, fetchTagsAndUpdateModal);
                } else {
                    console.warn('No content found.');
                }
            } else if (xhr.status === 204) {
                console.warn('No content found.');
            }
        },
        error: function(xhr, status, error) {
            // 그 외의 에러 처리
            console.error('Error:', error);
        }
    });
}
