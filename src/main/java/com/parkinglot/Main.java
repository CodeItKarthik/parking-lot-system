package com.parkinglot;

import com.parkinglot.model.*;
import com.parkinglot.service.ParkingManager;
import com.parkinglot.strategy.CostComputationStrategy;
import com.parkinglot.strategy.DefaultParkingStrategy;
import com.parkinglot.strategy.FlatHourlyCostStrategy;
import com.parkinglot.strategy.ParkingStrategy;
import com.parkinglot.exception.InvalidTicketException;
import com.parkinglot.exception.ParkingFullException;

public class Main {

    public static void main(String[] args) {

        // Initialize Strategies
        ParkingStrategy strategy = new DefaultParkingStrategy();
        CostComputationStrategy costStrategy = new FlatHourlyCostStrategy();

        // Initialize Core System
        ParkingManager parkingManager = new ParkingManager(strategy, costStrategy);

        // Seed Data: Create a multi-floor parking structure
        parkingManager.addParkingSpot(new ParkingSpot("M-101", 1, SpotType.MOTORCYCLE));
        parkingManager.addParkingSpot(new ParkingSpot("C-102", 1, SpotType.COMPACT));
        parkingManager.addParkingSpot(new ParkingSpot("L-103", 1, SpotType.LARGE));
        parkingManager.addParkingSpot(new ParkingSpot("C-201", 2, SpotType.COMPACT));

        System.out.println("--- Starting Parking Lot Simulation --- \n");

        // Create Vehicles
        Vehicle car1 = new Car("KA-01-MA-1234");
        Vehicle bike1 = new Motorcycle("KA-03-EX-9999");
        Vehicle bus1 = new Bus("MH-12-TR-5555");
        Vehicle car2 = new Car("DL-01-CA-5678");
        Vehicle car3 = new Car("UP-16-NZ-0001"); // Extra car to test full capacity

        // Simulate Entry Processes
        ParkingTicket ticket1 = tryPark(parkingManager, car1);
        ParkingTicket ticket2 = tryPark(parkingManager, bike1);
        ParkingTicket ticket3 = tryPark(parkingManager, bus1);
        ParkingTicket ticket4 = tryPark(parkingManager, car2);

        // Try to park a 3rd car when only 2 compact spots exist
        ParkingTicket ticket5 = tryPark(parkingManager, car3);

        System.out.println("\n--- Simulating Exit Process (Mocked 2 Hours Duration) --- \n");

        try {
            if (ticket1 != null) parkingManager.unparkVehicle(ticket1.getTicketId());
            if (ticket2 != null) parkingManager.unparkVehicle(ticket2.getTicketId());
        } catch (InvalidTicketException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\n--- Trying to park the blocked vehicle again --- \n");
        // Car 3 should be able to park now that Car 1 left
        ParkingTicket ticket6 = tryPark(parkingManager, car3);

    }

    private static ParkingTicket tryPark(ParkingManager parkingManager, Vehicle vehicle) {
        try {
            return parkingManager.parkVehicle(vehicle);
        } catch (ParkingFullException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
