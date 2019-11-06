package com.freenow.controller;

import com.freenow.exception.CarAlreadyInUseException;
import com.freenow.exception.ConstraintsViolationException;
import com.freenow.exception.EntityNotFoundException;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler
{
    private static org.slf4j.Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Could not find entity with id.")
    public void handleEntityNotFoundException(EntityNotFoundException e)
    {
        LOG.debug(e.getMessage(), e);
        LOG.error(e.getMessage());
    }


    @ExceptionHandler(ConstraintsViolationException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Some constraints are violated ...")
    public void handleConstraintsViolationException(ConstraintsViolationException e)
    {
        LOG.debug(e.getMessage(), e);
        LOG.error(e.getMessage());
    }


    @ExceptionHandler(CarAlreadyInUseException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT, reason = "Car already in use")
    public void handleConstraintsViolationException(CarAlreadyInUseException e)
    {
        LOG.debug(e.getMessage(), e);
        LOG.error(e.getMessage());
    }


    @InitBinder(value = {"driverCriteriaDTO"})
    public void initBinder(WebDataBinder binder)
    {
        binder.initDirectFieldAccess();
    }
}
