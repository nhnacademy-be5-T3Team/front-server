package com.t3t.frontserver.category.response;

import lombok.Getter;

import java.util.List;

@Getter
public class CategoryListResponse {
    private CategoryInfo parent;
    private List<CategoryInfo> childCategoryList;
}