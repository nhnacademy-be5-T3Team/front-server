package com.t3t.frontserver.publishers.service;

import com.t3t.frontserver.book.model.dto.PublisherDto;
import com.t3t.frontserver.model.response.PageResponse;
import com.t3t.frontserver.publishers.adaptor.PublisherAdaptor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PublisherService {
    private final PublisherAdaptor publisherAdaptor;

    public PageResponse<PublisherDto> getPublisherList(int pageNo, int pageSize, String sortBy) {
        return publisherAdaptor.getPublisherList(pageNo, pageSize, sortBy);
    }
}
