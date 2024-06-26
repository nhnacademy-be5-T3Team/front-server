
/*
 * Bookstore API 호출 함수
 */

const APP_KEY = document.querySelector("#appKey").getAttribute("data-contextPath");

/**
 * 출판사 정보를 가져와서 모달을 업데이트하는 함수
 * 현재 페이지와 페이지당 항목 수를 기준으로 출판사 목록을 가져옴
 * @author Yujin-nKim(김유진)
 */
function fetchPublishersAndUpdateModal() {
    $.ajax({
        url : '/publishers',
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

/**
 * 참여자 정보를 가져와서 모달을 업데이트하는 함수
 * 현재 페이지와 페이지당 항목 수를 기준으로 참여자 목록을 가져옴
 * @author Yujin-nKim(김유진)
 */
function fetchParticipantsAndUpdateModal() {
    $.ajax({
        url: '/participants',
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

/**
 * 참여자 역할 정보를 가져와서 모달을 업데이트하는 함수
 * 현재 페이지와 페이지당 항목 수를 기준으로 참여자 역할 목록을 가져옴
 * @author Yujin-nKim(김유진)
 */
function fetchParticipantRolesAndUpdateModal() {
    $.ajax({
        url: '/participantRoles',
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

/**
 * 카테고리 정보를 가져와서 모달을 업데이트하는 함수
 * @author Yujin-nKim(김유진)
 */
function fetchCategoriesAndUpdateModal(startDepth, maxDepth) {
    $.ajax({
        url: '/categories',
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

/**
 * 태그 정보를 가져와서 모달을 업데이트하는 함수
 * 현재 페이지와 페이지당 항목 수를 기준으로 태그 목록을 가져옴
 * @author Yujin-nKim(김유진)
 */
function fetchTagsAndUpdateModal() {
    $.ajax({
        url: '/tags',
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

/**
 * 출판사 변경 요청을 보내는 함수
 * @param {number} bookId - 도서 ID
 * @param {number} publisherId - 변경할 출판사 ID
 * @author Yujin-nKim(김유진)
 */
function sendPublisherUpdateRequest(bookId, publisherId) {
    $.ajax({
        url: `/admin/books/${bookId}/publisher`,
        type: 'PUT',
        data: {
            publisherId: publisherId
        }
    });
}

/**
 * 도서 참여자 변경 요청을 보내는 함수
 * @param {number} bookId - 도서 ID
 * @param {number} participantList - 변경할 도서 참여자 맵핑 리스트
 * @author Yujin-nKim(김유진)
 */
function sendParticipantUpdateRequest(bookId, participantList) {
    var jsonData = JSON.stringify(participantList);
    $.ajax({
        url: `/admin/books/${bookId}/participant`,
        type: 'PUT',
        contentType: 'application/json',
        data: jsonData
    });
}

/**
 * 도서 썸네일 변경 요청을 보내는 함수
 * @param {number} bookId - 도서의 ID
 * @param {FormData} data - 변경할 도서 썸네일
 * @author Yujin-nKim(김유진)
 */
function sendThumbnailUpdateRequest(bookId, formData) {
    $.ajax({
        url: `/admin/books/${bookId}/book-thumbnail`,
        type: 'PUT',
        data: formData,
        processData: false,
        contentType: false
    });
}

/**
 * 도서 미리보기 이미지 변경 요청을 보내는 함수
 * @param {number} bookId - 도서의 ID
 * @param {FormData} data - 변경할 도서 이미지
 * @author Yujin-nKim(김유진)
 */
function sendBookImageUpdateRequest(bookId, formData) {
    $.ajax({
        url: `/admin/books/${bookId}/book-image`,
        type: 'PUT',
        data: formData,
        processData: false,
        contentType: false
    });
}

function updateBookCategoryRequest(bookId, categoryList) {
    $.ajax({
        url: `/admin/books/${bookId}/category`,
        type: 'PUT',
        contentType: 'application/json',
        data: JSON.stringify(categoryList)
    });
}

function updateBookTagRequest(bookId, tagList) {
    $.ajax({
        url: `/admin/books/${bookId}/tag`,
        type: 'PUT',
        contentType: 'application/json',
        data: JSON.stringify(tagList)
    });
}