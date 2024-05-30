package com.t3t.frontserver.category.service;

import com.t3t.frontserver.category.adaptor.CategoryAdaptor;
import com.t3t.frontserver.category.response.CategoryTreeResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@CacheConfig(cacheNames = "categories")
@RequiredArgsConstructor
@Service
public class CategoryService {
    private final CategoryAdaptor categoryAdaptor;

    /**
     * 카테고리 트리를 조회한다.
     * 요청하는 startDepth 와 maxDepth 에 대해서 캐싱이 적용되어 조회된다.
     *
     * @author woody33545(구건모)
     */
    @Cacheable(key = "'startDepth:' + #startDepth + 'maxDepth:' + #maxDepth", unless = "#result == null")
    public List<CategoryTreeResponse> getCategoryTreeByDepth(Integer startDepth, Integer maxDepth) {
        return categoryAdaptor.getCategoryTreeByDepth(startDepth, maxDepth);
    }
}
