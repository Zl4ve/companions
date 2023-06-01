package ru.itis.companionapp.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;
import ru.itis.companionapp.controllers.rest.ExceptionHandlerRestController;
import ru.itis.companionapp.exceptions.NotFoundException;

@ControllerAdvice
@RequestMapping("/error")
public class ExceptionHandlerController {
    Logger logger = LoggerFactory.getLogger(ExceptionHandlerRestController.class);

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFoundException(NotFoundException e) {
        logger.error(e.getClass().getSimpleName() + ": " + e.getMessage());
        return "error/404";
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNoHandlerFoundException(NoHandlerFoundException e) {
        logger.error(e.getClass().getSimpleName() + ": " + e.getMessage());
        return "error/404";
    }

}
