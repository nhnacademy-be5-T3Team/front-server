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

@Service
@RequiredArgsConstructor
public class LoginService {
    private final LoginApiClient loginApiClient;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public ResponseEntity<BaseResponse<String>> login(LoginRequestDto loginRequestDto){
//        loginRequestDto.setPassword(bCryptPasswordEncoder.encode(loginRequestDto.getPassword()));
        ResponseEntity<String> resp = loginApiClient.doLogin(loginRequestDto);
        String access = resp.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if(Objects.nonNull(access)){
            return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, access).build();
        }else{
            return ResponseEntity.status(401).body(new BaseResponse<>(resp.getBody(),null));
        }
    }
}
