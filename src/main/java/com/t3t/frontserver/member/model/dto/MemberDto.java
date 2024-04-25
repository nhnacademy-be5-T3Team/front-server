package com.t3t.frontserver.member.model.dto;

import com.t3t.frontserver.member.model.constant.MemberRole;
import com.t3t.frontserver.member.model.constant.MemberStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Member Entity 에 대한 DTO 클래스
 * @author woody35545(구건모)
 */
@Getter
@Builder
public class MemberDto {
    private Long id;
    private MemberGradeDto grade;
    private String name;
    private String phone;
    private String email;
    private LocalDate birthDate;
    private LocalDateTime latestLogin;
    private Long point;
    private MemberStatus status;
    private MemberRole role;
}
