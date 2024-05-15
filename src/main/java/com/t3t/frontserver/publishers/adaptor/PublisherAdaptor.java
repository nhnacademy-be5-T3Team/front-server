package com.t3t.frontserver.publishers.adaptor;

import com.t3t.frontserver.book.model.dto.PublisherDto;
import com.t3t.frontserver.common.exception.ApiDataFetchException;
import com.t3t.frontserver.model.response.BaseResponse;
import com.t3t.frontserver.model.response.PageResponse;
import com.t3t.frontserver.publishers.client.PublisherApiClient;
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
public class PublisherAdaptor {
    private final PublisherApiClient publisherApiClient;

    public PageResponse<PublisherDto> getPublisherList(int pageNo, int pageSize, String sortBy) {
        try {
            ResponseEntity<BaseResponse<PageResponse<PublisherDto>>> response = publisherApiClient.getPublisherList(pageNo, pageSize, sortBy);
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
