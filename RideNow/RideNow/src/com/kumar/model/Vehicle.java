package com.kumar.model;


public class Vehicle {
    private String licensePlate;
    private String make;
    private String model;
    private int year;
    private VehicleType type;
    private int capacity;
    private Color color;

    public Vehicle(String licensePlate, String make, String model, 
                   int year, VehicleType type, int capacity, Color color) {
        this.licensePlate = licensePlate;
        this.make = make;
        this.model = model;
        this.year = year;
        this.type = type;
        this.capacity = capacity;
        this.color = color;
    }

    // Getters
    public String getLicensePlate() { return licensePlate; }
    public String getMake() { return make; }
    public String getModel() { return model; }
    public int getYear() { return year; }
    public VehicleType getType() { return type; }
    public int getCapacity() { return capacity; }
    public Color getColor() { return color; }

    public enum VehicleType {
        SEDAN, SUV, LUXURY, POOL, BIKE, AUTO
    }

    public enum Color {
        WHITE, BLACK, BLUE, RED, SILVER, GRAY
    }
}

