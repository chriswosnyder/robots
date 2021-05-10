package com.eventsystem.exception;

public class EventSystemException extends RuntimeException {

    public EventSystemException(Long code) {
        super(" User with id  " + code + " not found !");
    }
}

