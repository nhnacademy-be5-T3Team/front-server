package com.t3t.frontserver.main.response;

import lombok.Getter;

@Getter
public class BookInfoBrief {
    private String name; // 도서 제목
    private String coverImageUrl; // 도서 커버 이미지 url
}
