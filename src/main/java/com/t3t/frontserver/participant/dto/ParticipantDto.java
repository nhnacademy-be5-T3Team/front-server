package com.t3t.frontserver.participant.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 도서 참여자에 대한 데이터 전송 객체(DTO) <br>
 * 각 객체는 도서 참여자의 식별자(ID)와 이름, 이메일을 가지고 있음
 * @author Yujin-nKim(김유진)
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParticipantDto {
    private Long id;
    private String name;
    private String email;
}
