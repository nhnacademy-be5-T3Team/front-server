package com.t3t.frontserver.category.client;

import com.t3t.frontserver.category.response.CategoryTreeResponse;
import com.t3t.frontserver.model.response.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "categoryAdaptor", url = "${t3t.feignClient.url}")
public interface CategoryApiClient {
    @GetMapping(value = "/t3t/bookstore/categories")
    ResponseEntity<BaseResponse<List<CategoryTreeResponse>>> getCategoryTreeByDepth(@RequestParam  Integer startDepth, @RequestParam Integer maxDepth);
}
