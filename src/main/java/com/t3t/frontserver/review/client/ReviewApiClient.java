package com.t3t.frontserver.review.client;

import com.t3t.frontserver.model.response.BaseResponse;
import com.t3t.frontserver.model.response.PageResponse;
import com.t3t.frontserver.review.model.request.ReviewCommentUpdateRequest;
import com.t3t.frontserver.review.model.response.ReviewResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@FeignClient(name = "ReviewApiClient", url = "${t3t.feignClient.url}")
public interface ReviewApiClient {

    /**
     * 책 ID에 해당하는 리뷰 페이지 조회
     * @param bookId   리뷰를 검색할 책의 식별자
     * @param pageNo   페이지 번호
     * @return 주어진 책 식별자에 대한 리뷰 페이지 응답
     * @author Yujin-nKim(김유진)
     */
    @GetMapping(value = "/t3t/bookstore/reviews/book/{bookId}")
    ResponseEntity<BaseResponse<PageResponse<ReviewResponse>>> findReviewsByBookId(
            @PathVariable Long bookId,
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo);

    /**
     * 특정 회원과 특정 도서에 대한 리뷰가 이미 등록되어 있는지 확인
     * @param memberId 회원 식별자
     * @param bookId   도서 식별자
     * @return 특정 회원이 특정 도서에 대한 리뷰가 이미 등록되어 있는지 여부
     * @author Yujin-nKim(김유진)
     */
    @GetMapping("/t3t/bookstore/reviews/members/{memberId}/exists")
    ResponseEntity<BaseResponse<Boolean>> existsReview(@PathVariable Long memberId, @RequestParam Long bookId);

    /**
     * 사용자 식별자에 해당하는 리뷰 페이지 조회
     * @param memberId 회원 식별자
     * @param pageNo   페이지 번호
     * @param pageSize 페이지 크기
     * @param sortBy   정렬 기준
     * @return 주어진 회원 식별자에 대한 리뷰 페이지 응답
     * @author Yujin-nKim(김유진)
     */
    @GetMapping("/t3t/bookstore/reviews/members/{memberId}")
    ResponseEntity<BaseResponse<PageResponse<ReviewResponse>>> findReviewsByMemberId(
            @PathVariable Long memberId,
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "reviewCreatedAt", required = false) String sortBy);

    /**
     * 리뷰 상세 조회
     * @param reviewId 리뷰 ID
     * @return 리뷰 상세
     * @author Yujin-nKim(김유진)
     */
    @GetMapping("/t3t/bookstore/reviews/{reviewId}")
    ResponseEntity<BaseResponse<ReviewResponse>> findReviewByReviewId(@PathVariable Long reviewId);

    /**
     * 리뷰 comment 수정 요청
     * @param reviewId 수정할 review ID
     * @param request 리뷰 수정 요청 객체
     * @return 200 OK, 성공 메세지
     * @author Yujin-nKim(김유진)
     */
    @PutMapping("/t3t/bookstore/reviews/{reviewId}/comment")
    ResponseEntity<BaseResponse<Void>> updateReviewDetail(@PathVariable Long reviewId,
                                                          @RequestBody ReviewCommentUpdateRequest request);

    /**
     * 리뷰 score 수정 요청
     * @param reviewId 수정할 review ID
     * @param score 수정할 점수
     * @return 200 OK, 성공 메세지
     * @author Yujin-nKim(김유진)
     */
    @PutMapping("/t3t/bookstore/reviews/{reviewId}/score")
    ResponseEntity<BaseResponse<Void>> updateReviewScore(@PathVariable Long reviewId,
                                                         @RequestParam Integer score);

    /**
     * 리뷰 이미지 삭제 요청
     * @param reviewImageName 삭제할 이미지 name
     * @return 200 OK, 성공 메세지
     * @author Yujin-nKim(김유진)
     */
    @DeleteMapping("/t3t/bookstore/reviews/image")
    ResponseEntity<BaseResponse<Void>> deleteReviewImage(@RequestParam String reviewImageName);
}
