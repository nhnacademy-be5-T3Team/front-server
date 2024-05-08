package com.t3t.frontserver.pointdetail.model.request;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 포인트 사용/정립 내역 생성 정보를 담기 위한 클래스
 * @author hydrationn(박수화)
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
public class CreatePointDetailRequest {

    // 포인트 상세 내용
    @NotBlank(message = "포인트 상세 내용이 누락되었습니다.")
    private String content;

    // 포인트 사용/적립 구분
    @NotBlank(message = "포인트 사용/적립 구분이 명시되지 않았습니다.")
    @Pattern(regexp = "^(사용|적립)$", message = "포인트 상세 유형은'사용' 또는'적립'만 가능합니다.")
    private String pointDetailType;

    // 사용/적립 내역 일자
    @NotNull(message = "포인트 사용/적립 일자가 누락되었습니다.")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime pointDetailDate;

    // 포인트 양
    @NotNull(message = "포인트 양이 누락되었습니다.")
    private BigDecimal pointAmount;
}
