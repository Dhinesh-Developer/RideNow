package com.kumar.observer;

import com.kumar.model.Trip;

public class RiderNotificationObserver implements TripObserver {
    private final String riderId;
    
    public RiderNotificationObserver(String riderId) {
        this.riderId = riderId;
    }
    
    @Override
    public void update(Trip trip, String eventType) {
        if (trip.getRider().getUserId().equals(riderId)) {
            switch (eventType) {
                case "DRIVER_ASSIGNED":
                    System.out.println("Notification to Rider: Driver assigned - " + 
                                     trip.getDriver().getName());
                    break;
                case "RIDE_STARTED":
                    System.out.println("Notification to Rider: Ride started");
                    break;
                case "RIDE_COMPLETED":
                    System.out.println("Notification to Rider: Ride completed. Fare: $" + 
                                     trip.getTotalFare());
                    break;
                case "RIDE_CANCELLED":
                    System.out.println("Notification to Rider: Ride cancelled");
                    break;
            }
        }
    }
}