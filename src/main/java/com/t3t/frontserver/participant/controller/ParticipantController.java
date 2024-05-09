package com.t3t.frontserver.participant.controller;

import com.t3t.frontserver.model.response.BaseResponse;
import com.t3t.frontserver.model.response.PageResponse;
import com.t3t.frontserver.participant.client.ParticipantApiClient;
import com.t3t.frontserver.participant.dto.ParticipantDto;
import com.t3t.frontserver.participant.dto.ParticipantRoleDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ParticipantController {
    private final ParticipantApiClient participantApiClient;

    @GetMapping("/participants")
    ResponseEntity<BaseResponse<PageResponse<ParticipantDto>>> getParticipantList(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "participantId", required = false) String sortBy) {

        return participantApiClient.getParticipantList(pageNo, pageSize, sortBy);
    }

    @GetMapping("/participantRoles")
    ResponseEntity<BaseResponse<PageResponse<ParticipantRoleDto>>> getParticipantRoleList(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "participantRoleId", required = false) String sortBy) {

        return participantApiClient.getParticipantRoleList(pageNo, pageSize, sortBy);
    }
}
