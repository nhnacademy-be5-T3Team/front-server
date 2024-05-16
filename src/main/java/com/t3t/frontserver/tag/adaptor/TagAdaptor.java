package com.t3t.frontserver.tag.adaptor;

import com.t3t.frontserver.book.model.dto.TagDto;
import com.t3t.frontserver.common.exception.ApiDataFetchException;
import com.t3t.frontserver.model.response.BaseResponse;
import com.t3t.frontserver.model.response.PageResponse;
import com.t3t.frontserver.tag.client.TagApiClient;
import com.t3t.frontserver.util.FeignClientUtils;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class TagAdaptor {
    private final TagApiClient tagApiClient;

    public PageResponse<TagDto> getTagList(int pageNo, int pageSize, String sortBy) {
        try {
            ResponseEntity<BaseResponse<PageResponse<TagDto>>> response = tagApiClient.getTagList(pageNo, pageSize, sortBy);
            if (response.getStatusCode() == HttpStatus.OK) {
                return Objects.requireNonNull(response.getBody()).getData();
            }
            return null;
        } catch (FeignException e) {
            log.error(e.getMessage());
            throw new ApiDataFetchException(FeignClientUtils.getMessageFromFeignException(e));
        }
    }

    public TagDto getTag(Long tagId) {
        try {
            return Objects.requireNonNull(tagApiClient.getTag(tagId).getBody()).getData();
        } catch (FeignException e) {
            log.error(e.getMessage());
            throw new ApiDataFetchException(FeignClientUtils.getMessageFromFeignException(e));
        }
    }
}
