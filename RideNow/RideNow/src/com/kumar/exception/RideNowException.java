package com.kumar.exception;



public class RideNowException extends RuntimeException {
    public RideNowException(String message) {
        super(message);
    }
    
    public RideNowException(String message, Throwable cause) {
        super(message, cause);
    }
}

class RideNotAvailableException extends RideNowException {
    public RideNotAvailableException(String message) {
        super(message);
    }
}

class PaymentFailedException extends RideNowException {
    public PaymentFailedException(String message) {
        super(message);
    }
}

class InvalidUserException extends RideNowException {
    public InvalidUserException(String message) {
        super(message);
    }
}
