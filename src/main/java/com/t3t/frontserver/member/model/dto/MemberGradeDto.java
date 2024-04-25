package com.t3t.frontserver.member.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 회원 등급에 대한 DTO 클래스
 * @author woody35545(구건모)
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MemberGradeDto {
    private int gradeId;
    private String name;
    private MemberGradePolicyDto policy;
}
