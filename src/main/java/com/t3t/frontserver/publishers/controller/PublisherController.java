package com.t3t.frontserver.publishers.controller;

import com.t3t.frontserver.book.model.dto.PublisherDto;
import com.t3t.frontserver.model.response.BaseResponse;
import com.t3t.frontserver.model.response.PageResponse;
import com.t3t.frontserver.publishers.client.PublisherApiClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@RequiredArgsConstructor
@Controller
public class PublisherController {

    private final PublisherApiClient publisherApiClient;

    @GetMapping("/publishers")
    public ResponseEntity<BaseResponse<PageResponse<PublisherDto>>> getPublisherList(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "publisherId", required = false) String sortBy) {

        return publisherApiClient.getPublisherList(pageNo, pageSize, sortBy);
    }
}
