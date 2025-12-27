package com.kumar.observer;

import com.kumar.model.Trip;

public interface TripObserver {
    void update(Trip trip, String eventType);
}



