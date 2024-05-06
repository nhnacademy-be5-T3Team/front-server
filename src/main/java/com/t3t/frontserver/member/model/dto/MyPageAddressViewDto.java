package com.t3t.frontserver.member.model.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

/**
 * 마이페이지 주소 관리에서 사용될 정보를 담는 DTO
 *
 * @author woody35545(구건모)
 */
@Getter
@Builder
public class MyPageAddressViewDto {

    private List<MemberAddressInfo> memberAddressInfoList;
    private MemberAddressInfo defaultMemberAddressInfo;

    /**
     * MemberAddress 관련 정보를 담는 DTO
     *
     * @author woody35545(구건모)
     */
    @Getter
    @Builder
    public static class MemberAddressInfo {
        private Long id; // MemberAddress 식별자
        private String roadNameAddress; // 도로명 주소
        private String nickName; // 주소 별칭
        private Boolean isDefault; // 기본 주소 여부
    }
}
