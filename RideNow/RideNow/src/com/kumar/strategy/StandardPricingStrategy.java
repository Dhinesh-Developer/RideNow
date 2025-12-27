package com.kumar.strategy;

import com.kumar.model.Trip;

public class StandardPricingStrategy implements PricingStrategy {
    @Override
    public double calculateFare(Trip trip) {
        double baseFare = trip.getRideType().getBaseFare();
        double distanceFare = trip.getDistance() * trip.getRideType().getPerKmRate();
        // Add surge pricing if needed
        return baseFare + distanceFare;
    }
}

