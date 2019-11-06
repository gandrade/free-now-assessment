package com.freenow.exception;

import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class CarAlreadyInUseException extends Exception
{
    private static org.slf4j.Logger LOG = LoggerFactory.getLogger(CarAlreadyInUseException.class);


    public CarAlreadyInUseException(String message)
    {
        super(message);
        LOG.warn(message);
    }
}
