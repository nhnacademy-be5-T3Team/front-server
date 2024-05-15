package com.t3t.frontserver.review.service;

import com.t3t.frontserver.auth.util.SecurityContextUtils;
import com.t3t.frontserver.model.response.PageResponse;
import com.t3t.frontserver.review.adaptor.ReviewAdaptor;
import com.t3t.frontserver.review.model.request.ReviewCommentUpdateRequest;
import com.t3t.frontserver.review.model.request.ReviewRegisterRequest;
import com.t3t.frontserver.review.model.response.ReviewResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewAdaptor reviewAdaptor;

    /**
     * 특정 회원과 특정 도서에 대한 리뷰가 이미 등록되어 있는지 확인
     * @param memberId 특정 회원의 식별자
     * @param bookId   특정 도서의 식별자
     * @return 해당 회원이 해당 도서에 대해 리뷰를 작성했으면 true를 반환하고, 그렇지 않으면 false를 반환
     * @author Yujin-nKim(김유진)
     */
    public boolean existsReview(Long memberId, Long bookId) {
        return reviewAdaptor.existsReview(memberId, bookId);
    }

    /**
     * 리뷰 생성 요청
     * @param request 리뷰 생성 요청 객체
     * @author Yujin-nKim(김유진)
     */
    public void createReview(ReviewRegisterRequest request) {
        request.setMemberId(SecurityContextUtils.getMemberId());
        reviewAdaptor.createReview(request);
    }

    /**
     * 사용자 ID에 해당하는 리뷰 페이지 조회
     * @param pageNo   페이지 번호
     * @param pageSize 페이지 크기
     * @param sortBy   정렬 기준
     * @return 주어진 회원 ID에 대한 리뷰 페이지 응답
     * @author Yujin-nKim(김유진)
     */
    public PageResponse<ReviewResponse> findReviewsByMemberId(int pageNo, int pageSize, String sortBy) {
        return reviewAdaptor.findReviewsByMemberId(SecurityContextUtils.getMemberId(), pageNo, pageSize, sortBy);
    }

    /**
     * 리뷰 ID에 해당하는 리뷰 상세 조회
     * @param reviewId 리뷰 ID
     * @return 리뷰 ID에 해당하는 리뷰 상세
     * @author Yujin-nKim(김유진)
     */
    public ReviewResponse findReviewByReviewId(Long reviewId) {
        return reviewAdaptor.findReviewByReviewId(reviewId);
    }

    /**
     * 리뷰 comment 수정 요청
     * @param reviewId 리뷰 ID
     * @param request 리뷰 comment 수정 요청 객체
     * @author Yujin-nKim(김유진)
     */
    public void updateReviewDetail(Long reviewId, ReviewCommentUpdateRequest request) {
        reviewAdaptor.updateReviewDetail(reviewId, request);
    }

    /**
     * 리뷰 score 수정 요청
     * @param reviewId 리뷰 ID
     * @param score 수정할 리뷰 평점
     * @author Yujin-nKim(김유진)
     */
    public void updateReviewScore(Long reviewId, Integer score) {
        reviewAdaptor.updateReviewScore(reviewId, score);
    }

    /**
     * 리뷰 이미지 delete 요청
     * @param reviewImageName 삭제할 리뷰 이미지
     * @author Yujin-nKim(김유진)
     */
    public void deleteReviewImage(String reviewImageName) {
        reviewAdaptor.deleteReviewImage(reviewImageName);
    }

    /**
     * 리뷰 이미지 추가 요청
     * @param reviewId 리뷰 ID
     * @param imageList 추가할 리뷰 이미지 리스트
     * @author Yujin-nKim(김유진)
     */
    public void addReviewImage(Long reviewId, List<MultipartFile> imageList) {
        reviewAdaptor.addReviewImage(reviewId, imageList);
    }
}
