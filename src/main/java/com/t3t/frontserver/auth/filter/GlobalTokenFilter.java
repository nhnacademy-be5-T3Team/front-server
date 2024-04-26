package com.t3t.frontserver.auth.filter;

import com.t3t.frontserver.auth.util.RequestWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

/**
 * 브라우저에 쿠키가 저장되어 있으면, 해당 쿠키의 value를 request.header에 넣어주는 필터
 * @author joohyun1996(이주현)
 */
@Slf4j
public class GlobalTokenFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {log.debug("@@ GlobalTokenFilter servlet request uri {}", ((HttpServletRequest)servletRequest).getRequestURI());
        if("/error".equals(((HttpServletRequest)servletRequest).getRequestURI())){
            chain.doFilter(servletRequest,servletResponse);
        }
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        if(Objects.isNull(request.getCookies())){
            chain.doFilter(servletRequest,servletResponse);
        }
        Cookie[] cookies = request.getCookies();
        Optional<Cookie> userCookie = Arrays.stream(cookies)
                .filter(cookie -> "t3t".equals(cookie.getName()))
                .findFirst();

        if (userCookie.isEmpty()){
            chain.doFilter(request,servletResponse);
        }


        String token = Arrays.stream(cookies)
                .filter(cookie -> Objects.equals(cookie.getName(), "t3t"))
                .map(Cookie::getValue)
                .findFirst()
                .orElse(null);

        if (Objects.nonNull(token)) {
            RequestWrapper customRequest = new RequestWrapper(request);
            customRequest.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
            chain.doFilter(customRequest, servletResponse);
        } else {
            chain.doFilter(request, servletResponse);
        }
    }
}
