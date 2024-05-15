package com.t3t.frontserver.pointdetail.adaptor;

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

/**
 * 포인트 상세 정보를 가져오는 adaptor class
 * 외부 시스템으로부터 사용자의 포인트 상세 내역을 가져오는 역할
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class UserPointDetailAdaptor {
    private final UserPointDetailApiClient userPointDetailApiClient;

    /**
     * 사용자 ID와 포인트 상세 유형을 기반으로 포인트 상세 정보 조회
     * @param memberId 회원 ID
     * @param pointDetailType 조회할 포인트 타입(사용/적립), null이면 전체 내역 조회
     * @return 조건에 맞는 포인트 상세 내역 리스트를 반환. 조회된 정보가 없거나, 요청 처리 중 오류가 발생한 경우 null 반환.
     * @throws ApiDataFetchException 외부 시스템으로부터 데이터를 가져오는 과정에서 오류가 발생한 경우 예외 발생
     * @author hydrationn(박수화)
     */
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
