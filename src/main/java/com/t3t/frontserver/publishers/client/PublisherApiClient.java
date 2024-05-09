package com.t3t.frontserver.publishers.client;

import com.t3t.frontserver.book.model.dto.PublisherDto;
import com.t3t.frontserver.model.response.BaseResponse;
import com.t3t.frontserver.model.response.PageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "publisherApiClient", url = "${t3t.feignClient.url}")
public interface PublisherApiClient {
    @GetMapping("t3t/bookstore/publishers")
    ResponseEntity<BaseResponse<PageResponse<PublisherDto>>> getPublisherList(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "publisherId", required = false) String sortBy);
}
