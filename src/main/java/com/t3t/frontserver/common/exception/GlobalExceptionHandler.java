package com.t3t.frontserver.common.exception;

import com.t3t.frontserver.auth.exception.RestApiClientException;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
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

    @ExceptionHandler(RestApiClientException.class)
    public String handleRestApiClientException(Model model, RestApiClientException e, HttpServletResponse response) {
        JSONObject json = new JSONObject(e.getResponseBody());
        String message = json.getString("message");

        if (e.getStatus() == 401 && "Login again".equals(message)) {
            Cookie cookie = new Cookie("t3t", null);
            cookie.setMaxAge(0);
            cookie.setPath("/");
            SecurityContextHolder.clearContext();
            response.addCookie(cookie);

            return "redirect:/";
        } else {
            model.addAttribute("message", e.getResponseBody());
            return "main/page/message";
        }

    }
}
