package com.t3t.frontserver.tag.client;

import com.t3t.frontserver.book.model.dto.TagDto;
import com.t3t.frontserver.model.response.BaseResponse;
import com.t3t.frontserver.model.response.PageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "tagApiClient", url = "${t3t.feignClient.url}")
public interface TagApiClient {

    @GetMapping(value = "/t3t/bookstore/tags")
    ResponseEntity<BaseResponse<PageResponse<TagDto>>> getTagList(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "tagId", required = false) String sortBy);

}
