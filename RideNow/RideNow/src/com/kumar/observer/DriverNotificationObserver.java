package com.kumar.observer;

import com.kumar.model.Trip;

public class DriverNotificationObserver implements TripObserver {
    private final String driverId;
    
    public DriverNotificationObserver(String driverId) {
        this.driverId = driverId;
    }
    
    @Override
    public void update(Trip trip, String eventType) {
        if (trip.getDriver() != null && trip.getDriver().getUserId().equals(driverId)) {
            switch (eventType) {
                case "RIDE_REQUESTED":
                    System.out.println("Notification to Driver: New ride request from " + 
                                     trip.getRider().getName());
                    break;
                case "RIDER_CANCELLED":
                    System.out.println("Notification to Driver: Rider cancelled the ride");
                    break;
            }
        }
    }
}

