package com.t3t.frontserver.book.adaptor;

import com.t3t.frontserver.book.model.response.BookDetailResponse;
import com.t3t.frontserver.model.response.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//TODO : url 변경
@FeignClient(name = "bookAdaptor", url = "http://localhost:8082/bookstore")
public interface BookAdaptor {
    @GetMapping(value = "/books/{bookId}")
    ResponseEntity<BaseResponse<BookDetailResponse>> getBook(@PathVariable Long bookId);
}