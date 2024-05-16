package com.t3t.frontserver.pointdetail.service;

import com.t3t.frontserver.pointdetail.adaptor.UserPointDetailAdaptor;
import com.t3t.frontserver.pointdetail.model.response.PointDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 사용자의 포인트 상세 정보를 관리하는 서비스
 * 외부 시스템으로부터 사용자의 포인트 상세 정보를 조회하는 기능 제공
 */
@Service
@RequiredArgsConstructor
public class UserPointDetailService {
    private final UserPointDetailAdaptor userPointDetailAdaptor;

    /**
     * 회원 ID와 포인트 상세 유형을 기반으로 포인트 상세 내역 조회
     * @param memberId 회원 ID
     * @param pointDetailType 조회할 포인트 타입(사용/적립)
     * @return 해당 조건에 맞는 포인트 상세 정보 리스트를 반환.
     *         외부 시스템으로부터 정보를 성공적으로 조회하면 그에 대한 결과를, 그렇지 않으면 null을 반환.
     * @author hydrationn(박수화)
     */
    public List<PointDetailResponse> getPointDetailByPointDetailType(Long memberId, String pointDetailType) {
        return userPointDetailAdaptor.getPointDetailByPointDetailType(memberId, pointDetailType);
    }
}
