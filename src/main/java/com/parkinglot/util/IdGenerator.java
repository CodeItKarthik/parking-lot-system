package com.parkinglot.util;

import java.util.concurrent.atomic.AtomicInteger;

public final class IdGenerator {
    private static final AtomicInteger ticketCounter = new AtomicInteger();

    private IdGenerator() {}

    public static String generateTicketId() {
        return String.format("TCK-%04d", ticketCounter.incrementAndGet());
    }
}
