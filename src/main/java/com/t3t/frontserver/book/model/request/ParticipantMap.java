package com.t3t.frontserver.book.model.request;

import lombok.Data;
import lombok.ToString;

/**
 * 도서 참여자의 id와 각 참여자의 역할의 id를 맵핑하는 클래스
 * @author Yujin-nKim(김유진)
 */
@Data
@ToString
public class ParticipantMap {
    private Integer participantId; // 도서 참여자 id
    private Integer participantRoleId; // 도서 참여자 역할 id
}
