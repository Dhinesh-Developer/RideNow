package com.kumar.strategy;

import com.kumar.model.Trip;

public class SurgePricingStrategy implements PricingStrategy {
    private final double surgeMultiplier;

    public SurgePricingStrategy(double surgeMultiplier) {
        this.surgeMultiplier = surgeMultiplier;
    }

    @Override
    public double calculateFare(Trip trip) {
        double baseFare = trip.getRideType().getBaseFare();
        double distanceFare = trip.getDistance() * trip.getRideType().getPerKmRate();
        return (baseFare + distanceFare) * surgeMultiplier;
    }
}

