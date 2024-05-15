package com.t3t.frontserver.pointdetail.adaptor;

import com.t3t.frontserver.auth.util.SecurityContextUtils;
import com.t3t.frontserver.common.exception.ApiDataFetchException;
import com.t3t.frontserver.model.response.BaseResponse;
import com.t3t.frontserver.pointdetail.client.UserPointDetailApiClient;
import com.t3t.frontserver.pointdetail.model.response.PointDetailResponse;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserPointDetailAdaptor {
    private final UserPointDetailApiClient userPointDetailApiClient;

    public List<PointDetailResponse> getPointDetailByPointDetailType(Long memberId, String pointDetailType) {
        try {
            ResponseEntity<BaseResponse<List<PointDetailResponse>>> response = userPointDetailApiClient.getPointDetailByPointDetailType(memberId, pointDetailType);
            if (response.getStatusCode() == HttpStatus.OK) {
                return Objects.requireNonNull(response.getBody()).getData();
            }
            return null;
        } catch (FeignException e) {
            log.error(e.getMessage());
            throw new ApiDataFetchException();
        }
    }
}
