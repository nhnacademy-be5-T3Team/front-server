package com.t3t.frontserver.member.model.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 회원 정보 페이지 뷰에 필요한 정보를 정의한 DTO
 *
 * @author woody35545(구건모)
 */
@ToString
@Getter
@Builder
public class MyPageInfoViewDto {
    private String accountId;
    private Long memberId;
    private String name;
    private String phone;
    private String email;
    private LocalDate birthDate;
    private LocalDateTime latestLogin;
    private Long point;
    private Integer gradeId;
    private String gradeName;
    private String status;
    private String role;
    private MemberAddressInfo defaultMemberAddressInfo;
    private List<MemberAddressInfo> memberAddressList;

    @Getter
    @Builder
    public static class MemberAddressInfo {
        private Long id; // MemberAddress 식별자
        private String roadNameAddress; // 도로명 주소
        private String addressDetail; // 상세 주소
        private String nickName; // 주소 별칭
        private Boolean isDefault; // 기본 주소 여부
    }
}
