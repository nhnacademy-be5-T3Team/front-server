package com.t3t.frontserver.auth.filter;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

/**
 * access token 재발급시 재발급 받은 토큰을 브라우저에 저장하기 위한 필터
 * @author joohyun1996(이주현)
 */
@Component
public class GlobalRefreshFilter extends GenericFilterBean {
    public GlobalRefreshFilter() {
        super();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        Cookie[] cookies = request.getCookies();

        if(Objects.isNull(cookies)){
            chain.doFilter(request,response);
            return;
        }

        String prevToken = Arrays.stream(cookies)
                .filter(cookie -> Objects.equals(cookie.getName(), "t3t"))
                .map(Cookie::getValue)
                .findFirst()
                .orElse(null);

        if (Objects.nonNull(response.getHeader(HttpHeaders.AUTHORIZATION))) {
            String postToken = response.getHeader(HttpHeaders.AUTHORIZATION).trim().split(" ")[1];
            if(!Objects.equals(prevToken, postToken)){
                Cookie cookie = new Cookie("t3t", postToken);
                cookie.setHttpOnly(true);
                cookie.setMaxAge(-1);
                response.addCookie(cookie);
            }
        }
        chain.doFilter(request,response);
    }
}
