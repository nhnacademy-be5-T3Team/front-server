package com.t3t.frontserver.auth.interceptor;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@Component
public class AdminInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(Objects.nonNull(request.getHeader(HttpHeaders.AUTHORIZATION))){
            if(request.getRequestURI().startsWith("/admin")){
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                for (GrantedAuthority authority : authentication.getAuthorities()) {
                    if("ROLE_ADMIN".equals(authority.getAuthority())){
                        // 권한이 있고, 관리자인 경우
                        return true;
                    }
                }
                // 권한이 없는 경우 메인으로 이동
                response.sendRedirect("/");
                return false;
            }
        }
        return true;
    }
}
