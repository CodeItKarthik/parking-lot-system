package com.parkinglot.service;

import com.parkinglot.exception.InvalidTicketException;
import com.parkinglot.exception.ParkingFullException;
import com.parkinglot.model.ParkingSpot;
import com.parkinglot.model.ParkingTicket;
import com.parkinglot.model.Vehicle;
import com.parkinglot.strategy.CostComputationStrategy;
import com.parkinglot.strategy.ParkingStrategy;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ParkingManager {
    private static final DateTimeFormatter TIME_FORMATTER =
            DateTimeFormatter.ofPattern("dd-MMM-yyyy hh:mm:ss a").withZone(ZoneId.systemDefault());

    private final List<ParkingSpot> spots = new ArrayList<>();
    private final Map<String, ParkingTicket> activeTickets = new ConcurrentHashMap<>();

    private ParkingStrategy parkingStrategy;
    private CostComputationStrategy costStrategy;

    public ParkingManager(ParkingStrategy parkingStrategy, CostComputationStrategy costStrategy) {
        this.parkingStrategy = parkingStrategy;
        this.costStrategy = costStrategy;
    }

    public synchronized void addParkingSpot(ParkingSpot spot) {
        spots.add(spot);
    }

    // Command/Action: Issue Ticket & Park
    public synchronized ParkingTicket parkVehicle(Vehicle vehicle) {
        ParkingSpot availableSpot = parkingStrategy.findSpot(spots, vehicle);

        if (availableSpot == null) {
            throw new ParkingFullException(vehicle.getType());
        }

        if (availableSpot.assignVehicle(vehicle)) {
            ParkingTicket ticket = new ParkingTicket(vehicle, availableSpot);
            activeTickets.put(ticket.getTicketId(), ticket);
            System.out.println("Parked " + vehicle.getType() + " [" + vehicle.getLicensePlate() +
                    "] at Spot " + availableSpot.getSpotId() + " (Floor " + availableSpot.getFloorNumber() +
                    "). Ticket ID: " + ticket.getTicketId() + ", Parked time: " +
                    TIME_FORMATTER.format(Instant.ofEpochMilli(ticket.getStartTime())));
            return ticket;
        }
        return null;
    }

    // Command/Action: Unpark & Process Payment
    public synchronized double unparkVehicle(String ticketId) {
        ParkingTicket ticket = activeTickets.remove(ticketId);
        if (ticket == null) {
            throw new InvalidTicketException(ticketId);
        }

        ParkingSpot spot = ticket.getSpot();
        spot.removeVehicle();

        // Simulate some elapsed time for demonstration (e.g., 2 hours elapsed)
        // In real execution, it uses System.currentTimeMillis() - ticket.getStartTime()
        long mockDuration = 2 * 3600 * 1000; // 2 Hours in milliseconds

        double fee = costStrategy.calculateFee(mockDuration, ticket.getVehicle().getType());
        System.out.println("Unparked " + ticket.getVehicle().getLicensePlate() +
                " from Spot " + spot.getSpotId() + ". Total Fee: ₹" + fee);
        return fee;
    }

    // Open/Closed Principle: Allow swapping strategies at runtime
    public void setParkingStrategy(ParkingStrategy strategy) { this.parkingStrategy = strategy; }
    public void setCostStrategy(CostComputationStrategy strategy) { this.costStrategy = strategy; }
}
