package com.t3t.frontserver.category.adaptor;

import com.t3t.frontserver.category.client.CategoryApiClient;
import com.t3t.frontserver.category.response.CategoryTreeResponse;
import com.t3t.frontserver.common.exception.ApiDataFetchException;
import com.t3t.frontserver.model.response.BaseResponse;
import com.t3t.frontserver.util.FeignClientUtils;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class CategoryAdaptor {
    private final CategoryApiClient categoryApiClient;

    public List<CategoryTreeResponse> getCategoryTreeByDepth(Integer startDepth, Integer maxDepth)  {
        try {
            ResponseEntity<BaseResponse<List<CategoryTreeResponse>>> response = categoryApiClient.getCategoryTreeByDepth(startDepth, maxDepth);
            if (response.getStatusCode() == HttpStatus.OK) {
                return Objects.requireNonNull(response.getBody()).getData();
            }
            return null;
        } catch (FeignException e) {
            log.error(e.getMessage());
            throw new ApiDataFetchException(FeignClientUtils.getMessageFromFeignException(e));
        }
    }
}
