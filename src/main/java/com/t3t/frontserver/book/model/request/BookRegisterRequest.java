package com.t3t.frontserver.book.model.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BookRegisterRequest {
    private String bookTitle;
    private String bookIsbn;
    private BigDecimal bookPrice;
    private BigDecimal bookDiscountRate;
    private Integer packagingAvailableStatus;
    private String bookPublished;
    private Integer bookStock;
}
