package com.t3t.frontserver.auth.error;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * 401, 403 HttpStatus 가 response로 돌아오면 /login으로 redirect 하는 클래스
 * @author joohyun1996(이주현)
 */
public class CustomAuthenticationPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(Objects.isNull(authentication) && request.getRequestURI().startsWith("/admin")){
            response.sendRedirect("/");
            return;
        }

        if(response.getStatus()==HttpServletResponse.SC_FORBIDDEN || response.getStatus()==HttpServletResponse.SC_UNAUTHORIZED){
            request.setAttribute("errorMessage", "인증시간이 지났습니다. 다시 로그인 해주세요");
            response.sendRedirect("/login");
        }
    }
}
