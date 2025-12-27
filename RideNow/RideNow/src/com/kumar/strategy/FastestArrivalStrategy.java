package com.kumar.strategy;

import java.util.List;

import com.kumar.model.Driver;
import com.kumar.model.Location;

public class FastestArrivalStrategy implements DriverMatchingStrategy{
	private static final double AVERAGE_SPEED_KMH = 30.0;

	@Override
	public Driver selectDriver(List<Driver> availableDrivers, Location pickupLocation) {
		if(availableDrivers.isEmpty()) {
			return null;
		}
		
		Driver fastestDriver = null;
		double minTime = Double.MAX_VALUE;
		
		for(Driver x : availableDrivers) {
			double distance = pickupLocation.distanceTo(x.getCurrentLocation());
			double time = distance / AVERAGE_SPEED_KMH*60;
			
			if(time < minTime) {
				minTime = time;
				fastestDriver = x;
			}
		}
		return fastestDriver;
	}
	
	
}