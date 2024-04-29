package com.t3t.frontserver.elastic.model.response;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ElasticResponse {
    private BigDecimal bookId;//책번호
    private String name;//책이름
    private BigDecimal price;//가격
    private BigDecimal discountRate;//할인률
    private BigDecimal discountedPrice;//할인 가격
    private LocalDate published;//출판일
    private Float averageScore;//평점
    private Integer likeCount;//좋아요 수
    private String publisher;//출판사
    private String coverImageUrl;//썸네일
    private String authorName;//참여자
    private String authorRole;//참여자 역할
    private float score; //유사도 점수
    private long count; //검색한 책의 수
}
