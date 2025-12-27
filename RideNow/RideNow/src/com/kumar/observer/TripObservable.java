package com.kumar.observer;

import java.util.ArrayList;
import java.util.List;

import com.kumar.model.Trip;

public class TripObservable {
	
	private final List<TripObserver> observers;
	
	public TripObservable() {
		observers = new ArrayList<>();
	}
	
	public void addObserver(TripObserver observer) {
		observers.add(observer);
	}
	
	public void removeObserver(TripObserver observer) {
		observers.remove(observer);
	}
	
	public void notifyObservers(Trip trip,String eventType) {
		for(TripObserver x : observers) {
			x.update(trip, eventType);
		}
	}
}
