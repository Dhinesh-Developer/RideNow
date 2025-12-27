package com.kumar.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import com.kumar.model.Trip;

public class TripRepository {
	
	private final Map<String,Trip> trips;
	private final Map<String,List<String>> riderTrips;
	private final Map<String,List<String>> driverTrips;
	private static TripRepository instance;
	
	private TripRepository() {
		trips = new ConcurrentHashMap<>();
		riderTrips = new ConcurrentHashMap<>();
		driverTrips = new ConcurrentHashMap<>();
	}
	
	public static synchronized TripRepository getInstance() {
		if(instance == null) {
			instance = new TripRepository();
		}
		return instance;
	}
	
	
	public void addTrip(Trip trip) {
		trips.put(trip.getTripId(), trip);
		
		// update rider trips
		riderTrips.computeIfAbsent(trip.getRider().getUserId(), 
				k -> new ArrayList<>()).add(trip.getTripId());
		
		// update driver trips
		if(trip.getDriver() != null) {
			driverTrips.computeIfAbsent(trip.getDriver().getUserId(), 
					k -> new ArrayList<>())
			.add(trip.getTripId());
		}
		
	}
	
	
	public Trip getTripById(String tripId) {
		return trips.get(tripId);
	}
	
	public void updateTrip(Trip trip) {
		trips.put(trip.getTripId(), trip);
	}
	
	public List<Trip> getTripsByRider(String riderId){
		List<String> tripIds = riderTrips.getOrDefault(riderId, new ArrayList<>());
		return tripIds.stream()
				.map(trips::get)
				.filter(Objects::nonNull)
				.collect(Collectors.toList());
		
	}
	
	public List<Trip> getTripsByDriver(String driverId){
		List<String> tripIds = driverTrips.getOrDefault(driverId, new ArrayList<>());
		return tripIds.stream()
				.map(trips::get)
				.filter(Objects::nonNull)
				.collect(Collectors.toList());
	}
	
	public List<Trip> getAllActiveTrips(){
		return trips.values().stream()
				.filter(trip -> trip.getStatus() == Trip.TripStatus.IN_PROGRESS)
				.collect(Collectors.toList());
	}
	
	public int getTotalTrips() {
		return trips.size();
	}
}
