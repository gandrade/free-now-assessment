package com.freenow.exception;

public class EntityNotFoundException extends Exception
{
    static final long serialVersionUID = -3387516993334229948L;


    public EntityNotFoundException(String message)
    {
        super(message);
    }


    public EntityNotFoundException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
