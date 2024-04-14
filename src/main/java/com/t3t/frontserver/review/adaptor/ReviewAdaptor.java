package com.t3t.frontserver.review.adaptor;

import com.t3t.frontserver.model.response.BaseResponse;
import com.t3t.frontserver.model.response.PageResponse;
import com.t3t.frontserver.review.response.ReviewResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

//TODO : url 변경
@FeignClient(name = "reviewAdaptor", url = "http://localhost:8082/bookstore")
public interface ReviewAdaptor {
    @GetMapping(value = "/reviews/book/{bookId}")
    ResponseEntity<BaseResponse<PageResponse<ReviewResponse>>> getBook(@PathVariable Long bookId, @RequestParam int pageNo);
}