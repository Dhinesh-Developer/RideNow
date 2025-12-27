package com.kumar.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Trip {
	private final String tripId;
	private Rider rider;
	private Driver driver;
	private Location pickupLocation;
	private Location dropoffLocation;
	private TripStatus status;
	private double totalFare;
	private double distance;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private PaymentDetails paymentDetails;
	private RideType rideType;

	public Trip(Rider rider, Driver driver, Location pickup, Location dropoff, RideType rideType) {
		this.tripId = UUID.randomUUID().toString();
		this.rider = rider;
		this.driver = driver;
		this.pickupLocation = pickup;
		this.dropoffLocation = dropoff;
		this.status = TripStatus.REQUESTED;
		this.rideType = rideType;
		this.startTime = LocalDateTime.now();
		this.distance = calculateDistance(pickup, dropoff);
		this.totalFare = calculateFare();
	}

	private double calculateDistance(Location from, Location to) {
		return from.distanceTo(to);
	}

	private double calculateFare() {
		double baseFare = rideType.getBaseFare();
		double perKmRate = rideType.getPerKmRate();
		double perMinuteRate = rideType.getPerMinuteRate();

		// Simple fare calculation
		return baseFare + (distance * perKmRate);
	}

	// Getters and setters
	public String getTripId() {
		return tripId;
	}

	public Rider getRider() {
		return rider;
	}

	public Driver getDriver() {
		return driver;
	}

	public Location getPickupLocation() {
		return pickupLocation;
	}

	public Location getDropoffLocation() {
		return dropoffLocation;
	}

	public TripStatus getStatus() {
		return status;
	}

	public void setStatus(TripStatus status) {
		this.status = status;
	}

	public double getTotalFare() {
		return totalFare;
	}

	public void setTotalFare(double totalFare) {
		this.totalFare = totalFare;
	}

	public double getDistance() {
		return distance;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}

	public PaymentDetails getPaymentDetails() {
		return paymentDetails;
	}

	public void setPaymentDetails(PaymentDetails paymentDetails) {
		this.paymentDetails = paymentDetails;
	}

	public RideType getRideType() {
		return rideType;
	}

	public double getDriverEarnings() {
		// Driver gets 75% of total fare
		return totalFare * 0.75;
	}

	public void completeTrip() {
		this.status = TripStatus.COMPLETED;
		this.endTime = LocalDateTime.now();
	}

	public void cancelTrip() {
		this.status = TripStatus.CANCELLED;
		this.endTime = LocalDateTime.now();
	}

	public enum TripStatus {
		REQUESTED, DRIVER_ASSIGNED, IN_PROGRESS, COMPLETED, CANCELLED, NO_DRIVERS_AVAILABLE
	}
}