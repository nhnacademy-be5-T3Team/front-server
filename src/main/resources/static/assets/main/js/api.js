/*
 * Bookstore API 호출 함수
 */

/**
 * 리뷰 comment 업데이트 요청
 * @author Yujin-nKim(김유진)
 */
function updateReviewComment(reviewId) {
    var comment = document.getElementById('updateReviewComment').value;

    $.ajax({
        url: '/reviews/' + reviewId + '/comment',
        type: 'PUT',
        contentType: 'application/json',
        data: JSON.stringify({ "comment": comment }),
        success: function(response, textStatus, xhr) {
        },
        error: function(xhr, status, error) {
            console.error('Error:', error);
        }
    });
}

/**
 * 리뷰 score 업데이트 요청
 * @author Yujin-nKim(김유진)
 */
function updateReviewScore(reviewId) {
    var selectedRadioButton = document.querySelector('input[class="form-check-input"]:checked');

    if (selectedRadioButton == null) {
        alert("평점을 선택해주세요.");
        return;
    }

    $.ajax({
        url: '/reviews/' + reviewId + '/score',
        type: 'PUT',
        data : {
            score : selectedRadioButton.value
        },
        success: function(response, textStatus, xhr) {
        },
        error: function(xhr, status, error) {
            console.error('Error:', error);
        }
    });
}

/**
 * 리뷰 이미지 삭제 요청
 * @author Yujin-nKim(김유진)
 */
function deleteImage(reviewId, imageUrl) {

    alert("선택한 이미지를 삭제합니다.");
    var imageName = imageUrl.substring(imageUrl.lastIndexOf('/') + 1);

    $.ajax({
        url: '/reviews/' + reviewId + '/image',
        type: 'DELETE',
        data : {
            reviewImageName : imageName
        },
        success: function(response, textStatus, xhr) {
        },
        error: function(xhr, status, error) {
            console.error('Error:', error);
        }
    });
}

/**
 * 리뷰 이미지 추가 요청
 * @author Yujin-nKim(김유진)
 */
function addImage(reviewId) {

    // 선택한 이미지 파일
    var imageList = document.querySelector('input[name="reviewImageList"]').files;
    // 기존 이미지 개수
    var existingImageCount = document.querySelectorAll('.image-container').length;
    // 선택한 이미지 개수
    var addedImageCount = imageList.length;

    var totalImageCount = existingImageCount + addedImageCount;

    if (addedImageCount == 0) {
        alert("이미지를 선택해주세요.");
        return;
    }

    if (totalImageCount > 5) {
        alert("이미지는 최대 5개까지만 등록할 수 있습니다.");
        return;
    }

    var formData = new FormData();

    for (var i = 0; i < imageList.length; i++) {
        formData.append('imageList', imageList[i]);
    }

    $.ajax({
        url: '/reviews/' + reviewId + '/image',
        type: 'POST',
        processData: false,
        contentType: false,
        data: formData,
        success: function(response) {
        },
        error: function(xhr, status, error) {
        }
    });
}