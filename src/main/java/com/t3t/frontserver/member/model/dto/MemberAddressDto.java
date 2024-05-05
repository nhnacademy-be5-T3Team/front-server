package com.t3t.frontserver.member.model.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 회원 주소 정보에 대한 DTO
 *
 * @author woody35545(구건모)
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberAddressDto {
    private Long id;
    private Long memberId;
    private Integer addressNumber;
    private String roadNameAddress;
    private String addressNickname;
    private String addressDetail;
}