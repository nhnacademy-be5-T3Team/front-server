package com.t3t.frontserver.participant.service;

import com.t3t.frontserver.model.response.PageResponse;
import com.t3t.frontserver.participant.adaptor.ParticipantRoleAdaptor;
import com.t3t.frontserver.participant.dto.ParticipantRoleDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ParticipantRoleService {
    private final ParticipantRoleAdaptor participantRoleAdaptor;

    public PageResponse<ParticipantRoleDto> getParticipantRoleList(int pageNo, int pageSize, String sortBy) {
        return participantRoleAdaptor.getParticipantRoleList(pageNo, pageSize, sortBy);
    }
}
