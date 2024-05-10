package com.t3t.frontserver.pointdetail.service;

import com.t3t.frontserver.pointdetail.client.UserPointDetailApiClient;
import com.t3t.frontserver.pointdetail.model.response.PointDetailResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserPointDetailService {

    private final UserPointDetailApiClient userPointDetailApiClient;

/**
 * 특정 회원의 모든 포인트 상세 내역 조회 API 호출
 //     * @param memberId 회원 ID
 * @return 등록된 포인트 내역이 없으면 204(NO_CONTENT) 상태와 메시지 반환,
 *         내역이 있으면 해당 내역을 포함한 200(OK) 상태 반환
 *
 * @author hydrationn(박수화)
 */
/*    public List<PointDetailResponse> getPointDetailList() {
return userPointDetailApiClient.getPointDetailList().getBody();
}*/

    /**
     * 특정 회원의 포인트 타입에 따른 포인트 상세 내역 조회 API 호출
     * //     * @param memberId 회원 ID
     *
     //     * @param memberId
     * @param pointDetailType 조회할 포인트 타입(사용/적립)
     * @return 해당 포인트 상세 내역을 포함한 200(OK) 상태 반환
     * @author hydrationn(박수화)
     */
    public List<PointDetailResponse> getPointDetailByPointDetailType(String pointDetailType) {
        List<PointDetailResponse> pointDetailResponseList;

/*if(pointDetailType == null)
userPointDetailApiClient.getPointDetailList().getBody();*/

        return userPointDetailApiClient.getPointDetailByPointDetailType(pointDetailType).getBody();
    }

}