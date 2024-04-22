package com.t3t.frontserver.auth.filter;

import com.t3t.frontserver.auth.util.RequestWrapper;
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

@Component
public class GlobalTokenFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        Cookie[] cookies = request.getCookies();

        if(Objects.isNull(cookies)){
            chain.doFilter(request,response);
        }

        String token = Arrays.stream(cookies)
                .filter(cookie -> Objects.equals(cookie.getName(), "t3t"))
                .map(Cookie::getValue)
                .findFirst()
                .orElse(null);

        if(Objects.nonNull(token)){
            RequestWrapper customRequest = new RequestWrapper(request);
            customRequest.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
            chain.doFilter(customRequest, response);
        }else{
            chain.doFilter(request,response);
        }
    }
}
