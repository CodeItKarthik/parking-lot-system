package com.parkinglot.strategy;

import com.parkinglot.model.VehicleType;

public class FlatHourlyCostStrategy implements CostComputationStrategy {
    @Override
    public double calculateFee(long durationMillis, VehicleType type) {
        // Minimum charge is for 1 hour
        long hours = Math.max(1, (long) Math.ceil(durationMillis / 1000.0 / 3600.0));

        switch (type) {
            case MOTORCYCLE: return hours * 10.0;
            case CAR: return hours * 20.0;
            case BUS: return hours * 40.0;
            default: return hours * 15.0;
        }
    }
}
