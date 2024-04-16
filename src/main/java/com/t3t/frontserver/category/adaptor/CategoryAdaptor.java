package com.t3t.frontserver.category.adaptor;

import com.t3t.frontserver.category.response.CategoryListResponse;
import com.t3t.frontserver.model.response.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "categoryAdaptor", url = "${t3t.feignClient.url}")
public interface CategoryAdaptor {
    @GetMapping(value = "/t3t/bookstore/categories")
    ResponseEntity<BaseResponse<List<CategoryListResponse>>> getCategories();
}
