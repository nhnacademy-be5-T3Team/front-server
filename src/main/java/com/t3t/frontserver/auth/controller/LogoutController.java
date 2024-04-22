package com.t3t.frontserver.auth.controller;

import com.t3t.frontserver.auth.service.LogoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequiredArgsConstructor
public class LogoutController {
    private final LogoutService logoutService;
    @PostMapping("/logout")
    public String doLogout(HttpServletResponse response){
        if(logoutService.doLogout().is2xxSuccessful()){
            SecurityContextHolder.clearContext();
            Cookie cookie = new Cookie("t3t", null);
            cookie.setMaxAge(0);
            cookie.setHttpOnly(true);
            response.addCookie(cookie);
            return "/main/page/home";
        }else{
            // 팀원과 고려 필요
            return "/main/page/error";
        }
    }
}
