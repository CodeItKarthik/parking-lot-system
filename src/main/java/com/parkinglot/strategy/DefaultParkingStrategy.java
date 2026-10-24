package com.parkinglot.strategy;

import com.parkinglot.model.ParkingSpot;
import com.parkinglot.model.SpotType;
import com.parkinglot.model.Vehicle;
import com.parkinglot.model.VehicleType;

import java.util.List;

public class DefaultParkingStrategy implements ParkingStrategy {
    @Override
    public ParkingSpot findSpot(List<ParkingSpot> spots, Vehicle vehicle) {
        SpotType requiredType = getRequiredSpotType(vehicle.getType());

        // Find the first available spot that fits the vehicle type rules
        return spots.stream()
                .filter(spot -> spot.isFree() && spot.getSpotType() == requiredType)
                .findFirst()
                .orElse(null);
    }

    private SpotType getRequiredSpotType(VehicleType vehicleType) {
        switch (vehicleType) {
            case MOTORCYCLE: return SpotType.MOTORCYCLE;
            case CAR: return SpotType.COMPACT;
            case BUS: return SpotType.LARGE;
            default: throw new IllegalArgumentException("Unknown vehicle type");
        }
    }
}
