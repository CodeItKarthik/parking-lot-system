package com.parkinglot.strategy;

import com.parkinglot.model.VehicleType;

public interface CostComputationStrategy {
    double calculateFee(long durationMillis, VehicleType type);
}
