package com.parkinglot.exception;

public class InvalidTicketException extends RuntimeException {
    public InvalidTicketException(String ticketId) {
        super("Invalid Ticket ID: " + ticketId);
    }
}
