package com.t3t.frontserver.auth.service;

import com.t3t.frontserver.auth.client.LogoutApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * 로그아웃을 담당하는 서비스 클래스
 * @author joohyun1996(이주현)
 */
@Service
@RequiredArgsConstructor
public class LogoutService {
    private final LogoutApiClient logoutApiClient;

    /**
     * logout 요청을 실행 후, response status에 따라서 HttpStatus를 다르게 return해준다
     * @author joohyun1996(이주현)
     */
    public HttpStatus doLogout(){
        ResponseEntity<String> responseEntity = logoutApiClient.doLogout();
        if(responseEntity.getStatusCode().is2xxSuccessful()){
            return HttpStatus.NO_CONTENT;
        }else{
            return HttpStatus.BAD_REQUEST;
        }
    }
}
