package com.t3t.frontserver.participant.adaptor;

import com.t3t.frontserver.common.exception.ApiDataFetchException;
import com.t3t.frontserver.model.response.BaseResponse;
import com.t3t.frontserver.model.response.PageResponse;
import com.t3t.frontserver.participant.client.ParticipantApiClient;
import com.t3t.frontserver.participant.dto.ParticipantRoleDto;
import com.t3t.frontserver.util.FeignClientUtils;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class ParticipantRoleAdaptor {
    private final ParticipantApiClient participantApiClient;

    public PageResponse<ParticipantRoleDto> getParticipantRoleList(int pageNo, int pageSize, String sortBy) {
        try {
            ResponseEntity<BaseResponse<PageResponse<ParticipantRoleDto>>> response = participantApiClient.getParticipantRoleList(pageNo, pageSize, sortBy);
            if (response.getStatusCode() == HttpStatus.OK) {
                return Objects.requireNonNull(response.getBody()).getData();
            }
            return null;
        } catch (FeignException e) {
            log.error(e.getMessage());
            throw new ApiDataFetchException(FeignClientUtils.getMessageFromFeignException(e));
        }
    }
}
