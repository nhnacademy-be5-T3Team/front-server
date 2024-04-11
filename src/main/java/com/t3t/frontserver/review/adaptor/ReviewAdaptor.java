package com.t3t.frontserver.review.adaptor;

import com.t3t.frontserver.model.response.BaseResponse;
import com.t3t.frontserver.review.response.ReviewResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//TODO : url 변경
@FeignClient(name = "reviewAdaptor", url = "http://localhost:8081")
public interface ReviewAdaptor {
    @GetMapping(value = "/reviews/book/{bookId}")
    ResponseEntity<BaseResponse<Page<ReviewResponse>>> getBook(@PathVariable Long bookId, Pageable pageable);
}