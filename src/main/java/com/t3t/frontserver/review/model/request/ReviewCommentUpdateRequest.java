package com.t3t.frontserver.review.model.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 리뷰 comment 업데이트 요청을 나타내는 객체
 * @author Yujin-nKim(김유진)
 */
@Data
public class ReviewCommentUpdateRequest {
    @NotBlank(message = "리뷰 내용을 입력해주세요.")
    String comment;
}