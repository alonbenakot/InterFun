package com.alon.InterFun.advice;

import com.alon.InterFun.exceptions.InterFunException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
public class UserControllerAdvice {

    @ExceptionHandler(value = InterFunException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String exceptionHandler(Exception e) {
        return e.getMessage();
    }
}
