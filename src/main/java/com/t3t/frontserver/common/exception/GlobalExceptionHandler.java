package com.t3t.frontserver.common.exception;

import com.t3t.frontserver.member.client.MemberApiClient;
import com.t3t.frontserver.member.exception.MemberApiClientException;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public String handleException(RedirectAttributes redirectAttributes, Exception e){
        redirectAttributes.addAttribute("message", e.getMessage());
        return "redirect:/message";
    }
}
