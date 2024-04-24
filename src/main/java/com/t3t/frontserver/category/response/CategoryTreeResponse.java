package com.t3t.frontserver.category.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 카테고리 계층별 목록 응답시 사용하는 Dto 클래스
 *
 * @author Yujin-nKim(김유진)
 */
@Data
@Builder
public class CategoryTreeResponse {
    private Integer categoryId;

    private String categoryName;

    private Integer depth;

    private List<CategoryTreeResponse> children;
}
