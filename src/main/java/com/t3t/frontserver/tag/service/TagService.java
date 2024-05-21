package com.t3t.frontserver.tag.service;

import com.t3t.frontserver.book.model.dto.TagDto;
import com.t3t.frontserver.model.response.PageResponse;
import com.t3t.frontserver.tag.adaptor.TagAdaptor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagAdaptor tagAdaptor;

    public PageResponse<TagDto> getTagList(int pageNo, int pageSize, String sortBy) {
        return tagAdaptor.getTagList(pageNo, pageSize, sortBy);
    }

    public TagDto getTag(Long tagId) {
        return tagAdaptor.getTag(tagId);
    }
}
