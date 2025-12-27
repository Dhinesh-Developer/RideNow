package com.kumar.model;

public enum RideType {
	
	RIDENOW_GO(50.0, 10.0, 2.0, "Economy"),
	RIDENOW_X(75.0, 12.0,3.0,"Comfort"),
	RIDENOW_BLACK(150.0,20.0,5.0,"Premium"),
	RIDENOW_SUV(200.0,25.0,6.0, "Luxury SUV"),
	RIDENOW_POOL(30.0,8.0,1.5,"Shared Ride"),
	RIDENOW_AUTO(40.0,9.0,1.8,"Auto Rickshaw"),
	RIDENOW_BIKE(25.0,5.0,1.0,"Bike Taxi");
	
	private final double baseFare;
	private final double perKmRate;
	private final double perMinuteRate;
	private final String description;
	
	RideType(double baseFare,double perkmRate,double perMinuteRate,String description){
		this.baseFare = baseFare;
		this.perKmRate = perkmRate;
		this.perMinuteRate = perMinuteRate;
		this.description = description;
	}

	public double getBaseFare() {
		return baseFare;
	}

	public double getPerKmRate() {
		return perKmRate;
	}

	public double getPerMinuteRate() {
		return perMinuteRate;
	}

	public String getDescription() {
		return description;
	}
	
	
	
}
