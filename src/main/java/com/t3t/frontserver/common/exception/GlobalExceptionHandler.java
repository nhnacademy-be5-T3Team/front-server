package com.t3t.frontserver.common.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public String handleException(Model model, Exception e){
        model.addAttribute("message", e.getMessage());
        return "main/page/message";
    }
}
