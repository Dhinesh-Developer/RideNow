package com.kumar.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class User {
	private final String userId;
	private String name;
	private String email;
	private String phone;
	private double rating;
	private List<Double> ratings;
	private Location currentLocation;
	private boolean isActive;

	public User(String name, String email, String phone) {
		this.userId = UUID.randomUUID().toString();
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.rating = 5.0;
		this.ratings = new ArrayList<>();
		this.isActive = true;
	}

	// Getters and setters
	public String getUserId() {
		return userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public double getRating() {
		return rating;
	}

	public Location getCurrentLocation() {
		return currentLocation;
	}

	public void setCurrentLocation(Location location) {
		this.currentLocation = location;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean active) {
		isActive = active;
	}

	public void addRating(double rating) {
		if (rating < 1 || rating > 5) {
			throw new IllegalArgumentException("Rating must be between 1 and 5");
		}
		this.ratings.add(rating);
		this.rating = this.ratings.stream().mapToDouble(Double::doubleValue).average().orElse(5.0);
	}

	@Override
	public String toString() {
		return String.format("User{userId='%s', name='%s', rating=%.2f}", userId, name, rating);
	}
}