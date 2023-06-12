package com.jpmc.theater.exceptions;

public class TooManyMoviesToScheduleException extends RuntimeException{

    public TooManyMoviesToScheduleException(String message){
        super(message);
    }
}
