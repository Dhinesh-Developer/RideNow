package com.kumar.strategy;

import com.kumar.model.Trip;

public class PoolPricingStrategy implements PricingStrategy {
    @Override
    public double calculateFare(Trip trip) {
        double baseFare = trip.getRideType().getBaseFare();
        double distanceFare = trip.getDistance() * trip.getRideType().getPerKmRate();
        return (baseFare + distanceFare) * 0.7; // 30% discount for pool
    }
}

