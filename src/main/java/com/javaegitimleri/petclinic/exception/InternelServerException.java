package com.javaegitimleri.petclinic.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class InternelServerException extends RuntimeException{
    public InternelServerException(Throwable cause) {
        super(cause);
    }
}
