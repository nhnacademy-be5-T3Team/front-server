package com.t3t.frontserver.common.exception;

import com.t3t.frontserver.auth.exception.RestApiClientException;
import org.json.JSONObject;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public String handleException(Model model, Exception e){
        model.addAttribute("message", e.getMessage());
        return "main/page/message";
    }

    @ExceptionHandler(RestApiClientException.class)
    public String handleRestApiClientException(Model model, RestApiClientException e, HttpServletResponse response){
        JSONObject json = new JSONObject(e.getResponseBody());
        String message = json.getString("message");

        if (e.getStatus() == 401 && "Login again".equals(message)) {
            Cookie cookie = new Cookie("t3t", null);
            cookie.setMaxAge(0);
            cookie.setPath("/");
            SecurityContextHolder.clearContext();
            response.addCookie(cookie);

            return "redirect:/";
        }else {
            model.addAttribute("message", e.getResponseBody());
            return "main/page/message";
        }

    }
}
