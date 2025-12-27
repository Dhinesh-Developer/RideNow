package com.kumar.strategy;


import com.kumar.model.Trip;


public interface PricingStrategy {
    double calculateFare(Trip trip);
}


