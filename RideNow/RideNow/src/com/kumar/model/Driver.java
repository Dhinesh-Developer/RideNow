package com.kumar.model;

import java.util.ArrayList;
import java.util.List;

public class Driver extends User {
	private Vehicle vehicle;
	private DriverStatus status;
	private List<Trip> completedTrips;
	private double totalEarnings;
	private boolean isVerified;

	public Driver(String name, String email, String phone, Vehicle vehicle) {
		super(name, email, phone);
		this.vehicle = vehicle;
		this.status = DriverStatus.AVAILABLE;
		this.completedTrips = new ArrayList<>();
		this.totalEarnings = 0.0;
		this.isVerified = false;
	}

	// Getters and setters
	public Vehicle getVehicle() {
		return vehicle;
	}

	public DriverStatus getStatus() {
		return status;
	}

	public void setStatus(DriverStatus status) {
		this.status = status;
	}

	public List<Trip> getCompletedTrips() {
		return completedTrips;
	}

	public double getTotalEarnings() {
		return totalEarnings;
	}

	public boolean isVerified() {
		return isVerified;
	}

	public void setVerified(boolean verified) {
		isVerified = verified;
	}

	public void addCompletedTrip(Trip trip) {
		this.completedTrips.add(trip);
		this.totalEarnings += trip.getDriverEarnings();
	}

	public boolean isAvailable() {
		return status == DriverStatus.AVAILABLE && isVerified;
	}

	public enum DriverStatus {
		AVAILABLE, ON_TRIP, OFFLINE, BUSY
	}
}