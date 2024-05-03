package com.t3t.frontserver.book.client;

import com.t3t.frontserver.book.model.request.BookRegisterRequest;
import com.t3t.frontserver.book.model.response.BookDetailResponse;
import com.t3t.frontserver.model.response.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "bookAdaptor", url = "${t3t.feignClient.url}")
public interface BookApiClient {
    @GetMapping(value = "/t3t/bookstore/books/{bookId}")
    ResponseEntity<BaseResponse<BookDetailResponse>> getBook(@PathVariable Long bookId);

//    @PostMapping(value = "/t3t/bookstore/books")
//    ResponseEntity<BaseResponse<Void>> createBook(@RequestBody BookRegisterRequest request);
}