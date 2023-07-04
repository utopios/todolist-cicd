package com.example.correctiontodolist.controller;

import com.example.correctiontodolist.exception.TodoNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandlerController {
    @ExceptionHandler({TodoNotFoundException.class})
    public String handleTodoNotFoundException(TodoNotFoundException ex) {
        return ex.getMessage();
    }
}
