package com.t3t.frontserver.common.exception;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public String handleException(RedirectAttributes redirectAttributes, Exception e) {
        redirectAttributes.addAttribute("message", e.getMessage());
        return "redirect:/message";
    }

/*    @ExceptionHandler(RestApiClientException.class)
    public String handleUnAuthorizedException(Model model, RestApiClientException e, HttpServletResponse response) {
        Cookie cookie = new Cookie("t3t", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        SecurityContextHolder.clearContext();
        response.addCookie(cookie);
        return "redirect:/login";
    }*/

    @ExceptionHandler(FeignException.Unauthorized.class)
    public String handleLogoutException(Model model, FeignException.Unauthorized e, HttpServletResponse response) {
        Cookie cookie = new Cookie("t3t", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        SecurityContextHolder.clearContext();
        response.addCookie(cookie);
        return "redirect:/login";
    }
}
