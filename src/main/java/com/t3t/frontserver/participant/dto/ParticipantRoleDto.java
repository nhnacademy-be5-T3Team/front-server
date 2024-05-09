package com.t3t.frontserver.participant.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 도서 참여자 역할에 대한 데이터 전송 객체(DTO) <br>
 * 각 객체는 도서 참여자 역할의 식별자(ID)와 영어 이름, 한국어 이름을 가지고 있음
 * @author Yujin-nKim(김유진)
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParticipantRoleDto {
    private Integer id;
    private String roleNameEn;
    private String roleNameKr;
}
