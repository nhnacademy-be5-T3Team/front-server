package com.t3t.frontserver.review.model.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import java.util.List;

/**
 * 리뷰 등록 요청을 나타내는 객체
 * @author Yujin-nKim(김유진)
 */
@Data
public class ReviewRegisterRequest {
    private Long memberId; // 리뷰 작성자 id

    private Long bookId; // 리뷰를 작성할 도서 id

    @NotBlank(message = "리뷰 내용을 입력해주세요.")
    private String comment; // 리뷰 내용

    @NotNull(message = "평점을 입력해주세요.")
    @Min(value = 1, message = "리뷰는 1~5점 사이에서 선택할 수 있습니다.")
    @Max(value = 5, message = "리뷰는 1~5점 사이에서 선택할 수 있습니다.")
    private Integer score; // 리뷰 평점

    @Size(max = 5, message = "최대 5개의 리뷰 이미지를 등록할 수 있습니다.")
    private List<MultipartFile> reviewImageList; // 도서 리뷰 이미지
}