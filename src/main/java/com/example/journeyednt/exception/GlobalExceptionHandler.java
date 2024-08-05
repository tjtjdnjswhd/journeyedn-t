package com.example.journeyednt.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public String handleException(HttpServletRequest request, Exception ex, RedirectAttributes redirectAttributes) {
        String requestURI = request.getRequestURI();
        String message = ex.getMessage();

        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:" + requestURI;
    }
}
