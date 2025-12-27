package com.kumar.repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import com.kumar.model.Driver;
import com.kumar.model.Location;

public class DriverRepository {
	private final Map<String, Driver> drivers;
	private final Map<String, Set<String>> locationToDrivers;
	private static DriverRepository instance;

	private DriverRepository() {
		drivers = new ConcurrentHashMap<>();
		locationToDrivers = new ConcurrentHashMap<>();
	}

	public static synchronized DriverRepository getInstance() {
		if (instance == null) {
			instance = new DriverRepository();
		}
		return instance;
	}

	public void addDriver(Driver driver) {
		drivers.put(driver.getUserId(), driver);
		// Only update location if it's not null
		if (driver.getCurrentLocation() != null) {
			updateDriverLocation(driver.getUserId(), driver.getCurrentLocation());
		}
	}

	public Driver getDriverById(String driverId) {
		return drivers.get(driverId);
	}

	public void updateDriver(Driver driver) {
		drivers.put(driver.getUserId(), driver);
	}

	public List<Driver> findAvailableDriversNearLocation(Location location, double radiusInKm) {
		List<Driver> availableDrivers = new ArrayList<>();

		for (Driver driver : drivers.values()) {
			if (driver.isAvailable() && driver.getCurrentLocation() != null) {
				double distance = location.distanceTo(driver.getCurrentLocation());
				if (distance <= radiusInKm) {
					availableDrivers.add(driver);
				}
			}
		}

		// Sort by distance
		availableDrivers.sort(Comparator.comparingDouble(d -> 
			location.distanceTo(d.getCurrentLocation())));

		return availableDrivers;
	}

	public void updateDriverLocation(String driverId, Location location) {
		Driver driver = drivers.get(driverId);
		if (driver != null && location != null) {
			driver.setCurrentLocation(location);

			// Update location index
			String locationKey = getLocationKey(location);
			locationToDrivers.computeIfAbsent(locationKey, k -> new HashSet<>())
					.add(driverId);
		}
	}

	public List<Driver> getAllAvailableDrivers() {
		return drivers.values().stream()
				.filter(Driver::isAvailable)
				.collect(Collectors.toList());
	}

	private String getLocationKey(Location location) {
		if (location == null) {
			return "0,0"; // Default key for null location
		}
		double lat = Math.round(location.getLatitude() * 100.0) / 100.0;
		double lng = Math.round(location.getLongitude() * 100.0) / 100.0;
		return lat + "," + lng;
	}
	
	// Optional: Add a method to check if driver has location
	public boolean hasLocation(String driverId) {
		Driver driver = drivers.get(driverId);
		return driver != null && driver.getCurrentLocation() != null;
	}
}