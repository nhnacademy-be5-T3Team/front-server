package com.t3t.frontserver.book.client;

import com.t3t.frontserver.book.model.response.BookDetailResponse;
import com.t3t.frontserver.model.response.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "bookAdaptor", url = "${t3t.feignClient.url}")
public interface BookApiClient {
    @GetMapping(value = "/t3t/bookstore/books/{bookId}")
    ResponseEntity<BaseResponse<BookDetailResponse>> getBook(@PathVariable Long bookId);
}