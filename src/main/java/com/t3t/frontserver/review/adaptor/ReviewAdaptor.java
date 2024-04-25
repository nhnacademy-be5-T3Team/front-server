package com.t3t.frontserver.review.adaptor;

import com.t3t.frontserver.model.response.BaseResponse;
import com.t3t.frontserver.model.response.PageResponse;
import com.t3t.frontserver.review.response.ReviewResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "reviewAdaptor", url = "${t3t.feignClient.url}")
public interface ReviewAdaptor {
    @GetMapping(value = "/t3t/bookstore/reviews/book/{bookId}")
    ResponseEntity<BaseResponse<PageResponse<ReviewResponse>>> getBook(@PathVariable Long bookId, @RequestParam int pageNo);
}