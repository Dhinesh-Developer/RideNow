package com.kumar.model;

import java.time.LocalDateTime;

public class PaymentDetails {
	private String paymentId;
	private PaymentMethod paymentMethod;
	private double amount;
	private PaymentStatus status;
	private LocalDateTime paymentTime;
	private String transactionId;

	public PaymentDetails(double amount, PaymentMethod paymentMethod) {
		this.paymentId = java.util.UUID.randomUUID().toString();
		this.amount = amount;
		this.paymentMethod = paymentMethod;
		this.status = PaymentStatus.PENDING;
		this.paymentTime = LocalDateTime.now();
	}

	// Getters and setters
	public String getPaymentId() {
		return paymentId;
	}

	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}

	public double getAmount() {
		return amount;
	}

	public PaymentStatus getStatus() {
		return status;
	}

	public void setStatus(PaymentStatus status) {
		this.status = status;
	}

	public LocalDateTime getPaymentTime() {
		return paymentTime;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public enum PaymentMethod {
		CASH, CREDIT_CARD, DEBIT_CARD, UBER_WALLET, PAYPAL, UPI
	}

	public enum PaymentStatus {
		PENDING, COMPLETED, FAILED, REFUNDED
	}
}