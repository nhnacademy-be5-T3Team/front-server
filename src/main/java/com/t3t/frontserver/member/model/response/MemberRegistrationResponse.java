package com.t3t.frontserver.member.model.response;


import com.t3t.frontserver.member.model.constant.MemberRole;
import com.t3t.frontserver.member.model.constant.MemberStatus;
import com.t3t.frontserver.member.model.dto.MemberGradeDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * 회원 생성(회원 가입) 요청을 성공적으로 처리한 경우 응답 정보를 담기 위한 클래스
 * @author woody35545(구건모)
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberRegistrationResponse {
    private String accountId;
    private long memberId;
    private MemberGradeDto grade;
    private String name;
    private String phone;
    private String email;
    private LocalDate birthDate;
    private long point;
    private MemberStatus status;
    private MemberRole role;
}
