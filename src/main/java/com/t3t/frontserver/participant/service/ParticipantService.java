package com.t3t.frontserver.participant.service;

import com.t3t.frontserver.model.response.PageResponse;
import com.t3t.frontserver.participant.adaptor.ParticipantAdaptor;
import com.t3t.frontserver.participant.dto.ParticipantDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ParticipantService {
    private final ParticipantAdaptor participantAdaptor;

    public PageResponse<ParticipantDto> getParticipantList(int pageNo, int pageSize, String sortBy) {
        return participantAdaptor.getParticipantList(pageNo, pageSize, sortBy);
    }
}
