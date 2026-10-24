package com.parkinglot.model;

import com.parkinglot.util.IdGenerator;

public class ParkingTicket {
    private final String ticketId;
    private final Vehicle vehicle;
    private final ParkingSpot spot;
    private final long startTime;

    public ParkingTicket(Vehicle vehicle, ParkingSpot spot) {
        this.ticketId = IdGenerator.generateTicketId();
        this.vehicle = vehicle;
        this.spot = spot;
        this.startTime = System.currentTimeMillis();
    }

    public String getTicketId() { return ticketId; }
    public Vehicle getVehicle() { return vehicle; }
    public ParkingSpot getSpot() { return spot; }
    public long getStartTime() { return startTime; }
}
