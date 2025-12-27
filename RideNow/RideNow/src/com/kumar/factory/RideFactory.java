package com.kumar.factory;

import com.kumar.model.Driver;
import com.kumar.model.Location;
import com.kumar.model.RideType;
import com.kumar.model.Rider;
import com.kumar.model.Trip;
import com.kumar.strategy.*;


public class RideFactory {
    public static Trip createTrip(Rider rider, Driver driver, 
                                  Location pickup, Location dropoff, 
                                  RideType rideType) {
        Trip trip = new Trip(rider, driver, pickup, dropoff, rideType);
        
        // Apply pricing strategy based on ride type
        PricingStrategy pricingStrategy = getPricingStrategy(rideType, pickup);
        double fare = pricingStrategy.calculateFare(trip);
        trip.setTotalFare(fare);
        
        return trip;
    }
    
    private static PricingStrategy getPricingStrategy(RideType rideType, Location location) {
        if (rideType == RideType.RIDENOW_POOL) {
            return new PoolPricingStrategy();
        }
        
        // Check for surge pricing (simplified)
        boolean isSurgeArea = checkSurgeArea(location);
        if (isSurgeArea) {
            return new SurgePricingStrategy(1.5); // 1.5x surge
        }
        
        return new StandardPricingStrategy();
    }
    
    private static boolean checkSurgeArea(Location location) {
        // Simplified surge area check
        // In production, this would check real-time demand data
        return Math.random() > 0.7; // 30% chance of surge
    }
}