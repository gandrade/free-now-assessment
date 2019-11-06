package com.freenow.exception;

public class ConstraintsViolationException extends Exception
{

    static final long serialVersionUID = -3387516993224229948L;


    public ConstraintsViolationException(String message)
    {
        super(message);
    }


    public ConstraintsViolationException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
