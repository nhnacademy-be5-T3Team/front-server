package com.t3t.frontserver.book.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 도서 전체 목록 조회시 사용되는 DTO
 * @author Yujin-nKim(김유진)
 */
@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class BookListResponse {
    private Long id; // 도서 id
    private String isbn; // isbn
    private String bookName; // 도서 제목
    private BigDecimal price; // 정가
    private BigDecimal discountRate; // 할인율
    private LocalDate published; // 출판일시
    private Float averageScore; // 평균 평점
    private Integer likeCount; // 좋아요 수
    private Integer stock; // 재고 수
    private BigDecimal discountedPrice; // 할인가
    private boolean packagingAvailableStatus; // 포장 가능 여부
}
