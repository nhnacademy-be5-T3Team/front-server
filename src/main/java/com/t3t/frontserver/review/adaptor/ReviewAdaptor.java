package com.t3t.frontserver.review.adaptor;

import com.t3t.frontserver.common.exception.ApiDataFetchException;
import com.t3t.frontserver.model.response.BaseResponse;
import com.t3t.frontserver.model.response.PageResponse;
import com.t3t.frontserver.review.client.ReviewApiClient;
import com.t3t.frontserver.review.client.ReviewFormApiClient;
import com.t3t.frontserver.review.model.request.ReviewCommentUpdateRequest;
import com.t3t.frontserver.review.model.request.ReviewRegisterRequest;
import com.t3t.frontserver.review.model.response.ReviewResponse;
import com.t3t.frontserver.util.FeignClientUtils;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Slf4j
@Component
@RequiredArgsConstructor
public class ReviewAdaptor {
    private final ReviewApiClient reviewApiClient;
    private final ReviewFormApiClient reviewFormApiClient;

    /**
     * 특정 회원과 특정 도서에 대한 리뷰가 이미 등록되어 있는지 확인
     * @param memberId 회원 식별자
     * @param bookId   도서 식별자
     * @return 해당 회원이 해당 도서에 대해 리뷰를 작성했으면 true를 반환하고, 그렇지 않으면 false를 반환
     * @author Yujin-nKim(김유진)
     */
    public boolean existsReview(Long memberId, Long bookId) {
        try {
            return Optional.ofNullable(reviewApiClient.existsReview(memberId, bookId).getBody())
                    .map(BaseResponse::getData)
                    .orElseThrow(ApiDataFetchException::new);
        } catch (FeignException e) {
            throw new ApiDataFetchException(FeignClientUtils.getMessageFromFeignException(e));
        }
    }

    /**
     * 리뷰 생성 요청
     * @param request 리뷰 생성 요청 객체
     * @return 200 OK, 성공 메세지
     * @author Yujin-nKim(김유진)
     */
    public void createReview(ReviewRegisterRequest request) {
        try {
            reviewFormApiClient.createReview(request);
        } catch (FeignException e) {
            log.error(e.getMessage());
            throw new ApiDataFetchException(FeignClientUtils.getMessageFromFeignException(e));
        }
    }

    /**
     * 사용자 식별자에 해당하는 리뷰 페이지 조회
     * @param memberId 회원 식별자
     * @param pageNo   페이지 번호
     * @param pageSize 페이지 크기
     * @param sortBy   정렬 기준
     * @return 주어진 회원 식별자에 대한 리뷰 페이지 응답
     * @author Yujin-nKim(김유진)
     */
    public PageResponse<ReviewResponse> findReviewsByMemberId(Long memberId, int pageNo, int pageSize, String sortBy) {
        try {
            ResponseEntity<BaseResponse<PageResponse<ReviewResponse>>> response = reviewApiClient.findReviewsByMemberId(memberId, pageNo, pageSize, sortBy);
            if (response.getStatusCode() == HttpStatus.OK) {
                return Objects.requireNonNull(response.getBody()).getData();
            }
            return null;
        } catch (FeignException e) {
            log.error(e.getMessage());
            throw new ApiDataFetchException(FeignClientUtils.getMessageFromFeignException(e));
        }
    }

    /**
     * 리뷰 상세 조회
     * @param reviewId 리뷰 ID
     * @return 리뷰 상세
     * @author Yujin-nKim(김유진)
     */
    public ReviewResponse findReviewByReviewId(Long reviewId) {
        try {
            return Optional.ofNullable(reviewApiClient.findReviewByReviewId(reviewId).getBody())
                    .map(BaseResponse::getData)
                    .orElseThrow(ApiDataFetchException::new);
        } catch (FeignException e) {
            log.error(e.getMessage());
            throw new ApiDataFetchException(FeignClientUtils.getMessageFromFeignException(e));
        }
    }

    /**
     * 리뷰 comment 수정 요청
     * @param reviewId 수정할 review ID
     * @param request 리뷰 수정 요청 객체
     * @author Yujin-nKim(김유진)
     */
    public void updateReviewDetail(Long reviewId, ReviewCommentUpdateRequest request) {
        try {
            reviewApiClient.updateReviewDetail(reviewId, request);
        } catch (FeignException e) {
            log.error(e.getMessage());
            throw new ApiDataFetchException(FeignClientUtils.getMessageFromFeignException(e));
        }
    }

    /**
     * 리뷰 score 수정 요청
     * @param reviewId 수정할 review ID
     * @param score 수정할 점수
     * @author Yujin-nKim(김유진)
     */
    public void updateReviewScore(Long reviewId, Integer score) {
        try {
            reviewApiClient.updateReviewScore(reviewId, score);
        } catch (FeignException e) {
            log.error(e.getMessage());
            throw new ApiDataFetchException(FeignClientUtils.getMessageFromFeignException(e));
        }
    }

    /**
     * 리뷰 이미지 삭제 요청
     * @param reviewImageName 삭제할 이미지 name
     * @author Yujin-nKim(김유진)
     */
    public void deleteReviewImage(String reviewImageName) {
        try {
            reviewApiClient.deleteReviewImage(reviewImageName);
        } catch (FeignException e) {
            log.error(e.getMessage());
            throw new ApiDataFetchException(FeignClientUtils.getMessageFromFeignException(e));
        }
    }

    /**
     * 리뷰 이미지 추가 요청
     * @param reviewId 수정할 review ID
     * @param imageList 추가할 이미지
     * @author Yujin-nKim(김유진)
     */
    public void addReviewImage(Long reviewId, List<MultipartFile> imageList) {
        try {
            reviewFormApiClient.addReviewImage(reviewId, imageList);
        } catch (FeignException e) {
            log.error(e.getMessage());
            throw new ApiDataFetchException(FeignClientUtils.getMessageFromFeignException(e));
        }
    }
}
