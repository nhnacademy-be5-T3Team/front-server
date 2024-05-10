package com.t3t.frontserver.participant.client;

import com.t3t.frontserver.model.response.BaseResponse;
import com.t3t.frontserver.model.response.PageResponse;
import com.t3t.frontserver.participant.dto.ParticipantDto;
import com.t3t.frontserver.participant.dto.ParticipantRoleDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "categoryApiClient", url = "${t3t.feignClient.url}")
public interface ParticipantApiClient {

    @GetMapping("/t3t/bookstore/participants")
    ResponseEntity<BaseResponse<PageResponse<ParticipantDto>>> getParticipantList(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "participantId", required = false) String sortBy);

    @GetMapping("/t3t/bookstore/participantRoles")
    ResponseEntity<BaseResponse<PageResponse<ParticipantRoleDto>>> getParticipantRoleList(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "participantRoleId", required = false) String sortBy);
}
