package com.t3t.frontserver.book.model.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class ParticipantRoleRegistrationDtoByBookId {
    private Long bookId;
    private List<ParticipantRoleRegistrationDto> participantList;
}
