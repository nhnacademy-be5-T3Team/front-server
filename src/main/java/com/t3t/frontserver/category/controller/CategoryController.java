package com.t3t.frontserver.category.controller;

import com.t3t.frontserver.category.client.CategoryApiClient;
import com.t3t.frontserver.category.response.CategoryTreeResponse;
import com.t3t.frontserver.category.service.CategoryService;
import com.t3t.frontserver.model.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class CategoryController {
    private final CategoryApiClient categoryApiClient;
    private final CategoryService categoryService;

    @GetMapping("/categories")
    ResponseEntity<BaseResponse<List<CategoryTreeResponse>>> getCategoryTreeByDepth(@RequestParam Integer startDepth, @RequestParam Integer maxDepth) {
        return categoryApiClient.getCategoryTreeByDepth(startDepth, maxDepth);
    }

    @GetMapping("/admin/categories")
    public String getCategoryListAdmin(Model model) {
        List<CategoryTreeResponse> categoryList = categoryService.getCategoryTreeByDepth(1, 2);
        model.addAttribute("categoryList", categoryList);
        return "admin/page/categoryList";
    }
}
