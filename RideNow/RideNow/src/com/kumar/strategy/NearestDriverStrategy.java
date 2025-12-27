package com.kumar.strategy;

import java.util.List;

import com.kumar.model.Driver;
import com.kumar.model.Location;

public class NearestDriverStrategy implements DriverMatchingStrategy{

	@Override
	public Driver selectDriver(List<Driver> availableDrivers, Location pickupLocation) {
		if(availableDrivers.isEmpty()) {
			return null;
		}
		
		Driver nearestDriver = null;
		double minDistance = Double.MAX_VALUE;
		
		for(Driver driver : availableDrivers) {
			double distance = pickupLocation.distanceTo(driver.getCurrentLocation());
			if(distance < minDistance) {
				minDistance = distance;
				nearestDriver = driver;
			}
					
		}
		return nearestDriver;
	}
	
}
