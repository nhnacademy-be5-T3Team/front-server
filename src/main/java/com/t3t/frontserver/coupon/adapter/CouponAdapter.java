package com.t3t.frontserver.coupon.adapter;

import com.t3t.frontserver.coupon.client.CouponApiClient;
import com.t3t.frontserver.member.exception.CouponApiClientException;
import com.t3t.frontserver.util.FeignClientUtils;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CouponAdapter {
    private final CouponApiClient couponApiClient;

    /**
     * 회원이 도서 쿠폰 발급받기 위해 사용하는 api
     * @author joohyun1996(이주현)
     */
    public String registerBookCouponByMember(){
        try{
            couponApiClient.registerBookCoupon();
            return "쿠폰이 등록되었습니다";
        }catch(FeignException e){
            throw new CouponApiClientException("도서쿠폰 등록에 실패하였습니다 " + FeignClientUtils.getMessageFromFeignException(e));
        }
    }

    /**
     * 회원이 카테고리 쿠폰 발급받기 위해 사용하는 api
     * @author joohyun1996(이주현)
     */
    public String registerCategoryCouponByMember(){
        try{
            couponApiClient.registerCategoryCoupon();
            return "쿠폰이 등록되었습니다";
        }catch(FeignException e){
            throw new CouponApiClientException("카테고리쿠폰 등록에 실패하였습니다 " + FeignClientUtils.getMessageFromFeignException(e));
        }
    }
}
