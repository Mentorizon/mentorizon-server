package com.mapthree.mentorizonserver.exception.mentorshipapplication;

public class AlreadyMentoredException extends RuntimeException {
    public AlreadyMentoredException(String message) {
        super(message);
    }
}
