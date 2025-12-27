package com.kumar.model;

import java.util.ArrayList;
import java.util.List;

import com.kumar.model.PaymentDetails.PaymentMethod;

public class Rider extends User {
	private List<Trip> tripHistory;
	private PaymentMethod preferredPaymentMethod;

	public Rider(String name, String email, String phone) {
		super(name, email, phone);
		this.tripHistory = new ArrayList<>();
	}

	public List<Trip> getTripHistory() {
		return tripHistory;
	}

	public PaymentMethod getPreferredPaymentMethod() {
		return preferredPaymentMethod;
	}

	public void setPreferredPaymentMethod(PaymentMethod method) {
		this.preferredPaymentMethod = method;
	}

	public void addTripToHistory(Trip trip) {
		this.tripHistory.add(trip);
	}

	public double getTotalSpent() {
		return tripHistory.stream().mapToDouble(Trip::getTotalFare).sum();
	}
}
