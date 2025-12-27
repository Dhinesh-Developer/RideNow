package com.kumar.strategy;

import java.util.List;

import com.kumar.model.Driver;
import com.kumar.model.Location;

public interface DriverMatchingStrategy{
	Driver selectDriver(List<Driver> availableDrivers,Location pickupLocation);
}






