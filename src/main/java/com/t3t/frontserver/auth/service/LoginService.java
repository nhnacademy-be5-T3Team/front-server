package com.t3t.frontserver.auth.service;

import com.t3t.frontserver.auth.client.LoginApiClient;
import com.t3t.frontserver.auth.model.request.LoginRequestDto;
import com.t3t.frontserver.model.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Objects;

/**
 * 로그인을 담당하는 서비스 클래스
 * @author joohyun1996(이주현)
 */
@Service
@RequiredArgsConstructor
public class LoginService {
    private final LoginApiClient loginApiClient;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * 로그인 요청을 수행하는 메소드
     * 비밀번호는 BCryptPasswordEncoder를 써서 암호화한다
     * @param loginRequestDto
     * @author joohyun1996(이주현)
     */
    public ResponseEntity<BaseResponse<String>> login(LoginRequestDto loginRequestDto){
        ResponseEntity<String> resp = loginApiClient.doLogin(loginRequestDto);
        String access = resp.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if(Objects.nonNull(access)){
            return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, access).build();
        }else{
            return ResponseEntity.status(401).body(new BaseResponse<>(resp.getBody(),null));
        }
    }
}
