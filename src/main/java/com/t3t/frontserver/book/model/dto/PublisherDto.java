package com.t3t.frontserver.book.model.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Data
@Getter
@Builder
public class PublisherDto {
    @NotNull
    private Long publisherId;
    private String publisherName;
    private String publisherEmail;
}
