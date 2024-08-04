package com.example.journeyednt.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomErrorController implements ErrorController {

    @GetMapping("/error")
    public String error(Model model, HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        String message;
        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            HttpStatus code = HttpStatus.valueOf(statusCode);
            assert code.isError();

            switch (code) {
                case NOT_FOUND -> message = "Not found";
                case UNAUTHORIZED -> message = "Unauthorized";
                case FORBIDDEN -> message = "Forbidden";
                default -> message = "Status code: " + statusCode;
            }
        } else {
            message = "Unknown error";
        }

        model.addAttribute("message", message);
        return "error";
    }
}
