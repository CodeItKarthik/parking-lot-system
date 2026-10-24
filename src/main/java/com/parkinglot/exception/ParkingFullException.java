package com.parkinglot.exception;

import com.parkinglot.model.VehicleType;

public class ParkingFullException extends RuntimeException {
    public ParkingFullException(VehicleType vehicleType) {
        super("Parking Full for Vehicle Type: " + vehicleType);
    }
}
