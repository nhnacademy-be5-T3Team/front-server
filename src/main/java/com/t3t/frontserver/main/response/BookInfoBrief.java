package com.t3t.frontserver.main.response;

import lombok.Getter;

@Getter
public class BookInfoBrief {
    private Long id; // 도서 id
    private String name; // 도서 제목
    private String thumbnailImageUrl; // 도서 커버 이미지 url
}
