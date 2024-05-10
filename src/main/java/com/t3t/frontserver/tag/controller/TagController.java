package com.t3t.frontserver.tag.controller;

import com.t3t.frontserver.book.model.dto.TagDto;
import com.t3t.frontserver.model.response.BaseResponse;
import com.t3t.frontserver.model.response.PageResponse;
import com.t3t.frontserver.tag.client.TagApiClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@RequiredArgsConstructor
@Controller
public class TagController {
    private final TagApiClient tagApiClient;

    @GetMapping("/tags")
    public ResponseEntity<BaseResponse<PageResponse<TagDto>>> getTagList(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "tagId", required = false) String sortBy) {

        return tagApiClient.getTagList(pageNo, pageSize, sortBy);
    }
}
