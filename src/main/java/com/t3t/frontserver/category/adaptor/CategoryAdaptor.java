package com.t3t.frontserver.category.adaptor;

import com.t3t.frontserver.category.response.CategoryListResponse;
import com.t3t.frontserver.model.response.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

//TODO : url 변경
@FeignClient(name = "categoryAdaptor", url = "http://localhost:8081")
public interface CategoryAdaptor {
    @GetMapping(value = "/categories")
    ResponseEntity<BaseResponse<List<CategoryListResponse>>> getCategories();
}
