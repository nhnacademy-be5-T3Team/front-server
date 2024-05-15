package com.t3t.frontserver.pointdetail.service;

import com.t3t.frontserver.auth.util.SecurityContextUtils;
import com.t3t.frontserver.pointdetail.adaptor.UserPointDetailAdaptor;
import com.t3t.frontserver.pointdetail.model.response.PointDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserPointDetailService {
    private final UserPointDetailAdaptor userPointDetailAdaptor;

    public List<PointDetailResponse> getPointDetailByPointDetailType(Long memberId, String pointDetailType) {
        return userPointDetailAdaptor.getPointDetailByPointDetailType(memberId, pointDetailType);
    }
}
