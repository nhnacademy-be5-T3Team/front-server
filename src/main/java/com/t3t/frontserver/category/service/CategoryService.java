package com.t3t.frontserver.category.service;

import com.t3t.frontserver.category.adaptor.CategoryAdaptor;
import com.t3t.frontserver.category.response.CategoryTreeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryAdaptor categoryAdaptor;

    public List<CategoryTreeResponse> getCategoryTreeByDepth(Integer startDepth, Integer maxDepth) {
        return categoryAdaptor.getCategoryTreeByDepth(startDepth, maxDepth);
    }
}
