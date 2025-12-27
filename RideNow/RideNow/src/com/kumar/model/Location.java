package com.kumar.model;

public class Location {
	private double latitude;
	private double longitude;
	private String address;

	public Location(double latitude, double longitude, String address) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.address = address;
	}

	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public String getAddress() {
		return address;
	}

	public double distanceTo(Location other) {
		final int R = 6371; // Earth's radius in kilometers
		double latDistance = Math.toRadians(other.latitude - this.latitude);
		double lonDistance = Math.toRadians(other.longitude - this.longitude);
		double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) + Math.cos(Math.toRadians(this.latitude))
				* Math.cos(Math.toRadians(other.latitude)) * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		return R * c;
	}

	@Override
	public String toString() {
		return String.format("Location{lat=%.6f, lng=%.6f, address='%s'}", latitude, longitude, address);
	}
}
