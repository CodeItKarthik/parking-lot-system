package com.parkinglot.model;

public class ParkingSpot {
    private final String spotId;
    private final int floorNumber;
    private final SpotType spotType;
    private boolean isFree;
    private Vehicle parkedVehicle;

    public ParkingSpot(String spotId, int floorNumber, SpotType spotType) {
        this.spotId = spotId;
        this.floorNumber = floorNumber;
        this.spotType = spotType;
        this.isFree = true;
    }

    public synchronized boolean assignVehicle(Vehicle vehicle) {
        if (!isFree) return false;
        this.parkedVehicle = vehicle;
        this.isFree = false;
        return true;
    }

    public synchronized void removeVehicle() {
        this.parkedVehicle = null;
        this.isFree = true;
    }

    public String getSpotId() { return spotId; }
    public int getFloorNumber() { return floorNumber; }
    public SpotType getSpotType() { return spotType; }
    public boolean isFree() { return isFree; }
    public Vehicle getParkedVehicle() { return parkedVehicle; }
}
