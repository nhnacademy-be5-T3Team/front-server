package com.t3t.frontserver.book.model.response;

import com.t3t.frontserver.book.model.dto.CategoryDto;
import com.t3t.frontserver.book.model.dto.PackagingDto;
import com.t3t.frontserver.book.model.dto.ParticipantRoleRegistrationDto;
import com.t3t.frontserver.book.model.dto.TagDto;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class BookDetailResponse {
    private Long id; // 도서 id
    private String isbn; // isbn
    private String bookName; // 도서 제목
    private String index; // 도서 목차
    private String desc; // 도서 설명
    private BigDecimal price; // 정가
    private BigDecimal discountRate; // 할인율
    private LocalDate published; // 출판일시
    private Float averageScore; // 평균 평점
    private Integer likeCount; // 좋아요 수
    private Integer stock; // 재고 수

    private BigDecimal discountedPrice; // 할인가
    private boolean orderAvailableStatus; // 재고 여부 (주문 가능 여부)
    private boolean packagingAvailableStatus; // 포장 가능 여부

    private String publisherName; // 출판사 이름
    private String thumbnailImageUrl; // 도서 썸네일 이미지 url

    private List<PackagingDto> packagingInfoList; // 포장 종류 리스트

    private List<String> bookImageUrlList; // 도서 미리보기 이미지 url
    private List<CategoryDto> categoryList; //연결된 카테고리 리스트
    private List<TagDto> tagList; // 연결된 태그 리스트
    private List<ParticipantRoleRegistrationDto> participantList; // 도서 참여자 리스트
}