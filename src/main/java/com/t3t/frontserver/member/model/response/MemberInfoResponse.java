package com.t3t.frontserver.member.model.response;

import com.t3t.frontserver.member.model.constant.MemberRole;
import com.t3t.frontserver.member.model.constant.MemberStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 회원과 관련된 정보를 종합적으로 담기 위한 응답 클래스
 *
 * @author woody35545(구건모)
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberInfoResponse {
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
    private MemberStatus status;
    private MemberRole role;
}
