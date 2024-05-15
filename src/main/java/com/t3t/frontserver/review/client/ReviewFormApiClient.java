package com.t3t.frontserver.review.client;

import com.t3t.frontserver.config.FormConfiguration;
import com.t3t.frontserver.model.response.BaseResponse;
import com.t3t.frontserver.review.model.request.ReviewRegisterRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@FeignClient(name = "reviewFormApiClient", url = "${t3t.feignClient.url}", configuration = FormConfiguration.class)
public interface ReviewFormApiClient {

    /**
     * 리뷰 생성 요청
     * @param request 리뷰 생성 요청 객체
     * @return 200 OK, 성공 메세지
     * @author Yujin-nKim(김유진)
     */
    @PostMapping(value = "/t3t/bookstore/reviews", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<BaseResponse<Void>> createReview(@ModelAttribute ReviewRegisterRequest request);

    /**
     * 리뷰 이미지 추가 요청
     * @param reviewId 수정할 review ID
     * @param imageList 추가할 이미지
     * @return 200 OK, 성공 메세지
     * @author Yujin-nKim(김유진)
     */
    @PostMapping(value = "/t3t/bookstore/reviews/{reviewId}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<BaseResponse<Void>> addReviewImage(@PathVariable Long reviewId,
                                                      @ModelAttribute List<MultipartFile> imageList);
}
