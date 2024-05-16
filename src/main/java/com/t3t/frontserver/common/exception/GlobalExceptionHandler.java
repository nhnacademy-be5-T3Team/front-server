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
import java.util.Optional;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 예외 발생 시 예외에 대한 정보를 제공하는 핸들러
     *
     * @author 구건모(woody35545)
     */
    @ExceptionHandler(Exception.class)
    public String handleException(RedirectAttributes redirectAttributes, Exception e) {

        redirectAttributes.addAttribute("message",
                Optional.ofNullable(e.getMessage()).orElse("오류가 발생하였습니다. 잠시 후 다시 시도해주세요."));

        return "redirect:/message";
    }

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
