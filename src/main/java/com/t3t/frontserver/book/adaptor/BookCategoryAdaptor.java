package com.t3t.frontserver.book.adaptor;

import com.t3t.frontserver.book.model.response.BookSearchResultResponse;
import com.t3t.frontserver.model.response.BaseResponse;
import com.t3t.frontserver.model.response.PageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "bookCategoryAdaptor", url = "${t3t.feignClient.url}")
public interface BookCategoryAdaptor {
    @GetMapping(value = "/t3t/bookstore/category/{categoryId}/books")
    ResponseEntity<BaseResponse<PageResponse<BookSearchResultResponse>>> getBooksByCategoryId(@PathVariable Integer categoryId, @RequestParam int pageNo);
}