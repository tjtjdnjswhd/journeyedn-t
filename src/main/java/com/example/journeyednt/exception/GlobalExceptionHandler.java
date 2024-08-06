package com.example.journeyednt.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public String handleException(HttpServletRequest request, Exception ex, RedirectAttributes redirectAttributes) {
        String requestURI = request.getRequestURI();

        if (ex instanceof MethodArgumentNotValidException me) {
            me.getFieldErrors().forEach(error -> redirectAttributes.addFlashAttribute(error.getField() + "Error", error.getDefaultMessage()));
        } else if (ex instanceof NoResourceFoundException) {
            return null;
        } else {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
        }

        return "redirect:" + requestURI;
    }
}
