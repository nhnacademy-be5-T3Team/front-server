package com.t3t.frontserver.auth.service;

import com.t3t.frontserver.auth.client.LogoutApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutService {
    private final LogoutApiClient logoutApiClient;

    public HttpStatus doLogout(){
        ResponseEntity<String> responseEntity = logoutApiClient.doLogout();
        if(responseEntity.getStatusCode().is2xxSuccessful()){
            return HttpStatus.NO_CONTENT;
        }else{
            return HttpStatus.BAD_REQUEST;
        }
    }
}
