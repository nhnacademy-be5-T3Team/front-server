package com.t3t.frontserver.book.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 도서 참여자의 id와 각 참여자의 역할의 id를 맵핑하는 클래스
 * @author Yujin-nKim(김유진)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParticipantMapDto {
    private Long participantId; // 도서 참여자 id
    private Integer participantRoleId; // 도서 참여자 역할 id
}
