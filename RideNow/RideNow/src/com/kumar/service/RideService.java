package com.kumar.service;

import java.util.List;

import com.kumar.factory.RideFactory;
import com.kumar.model.Driver;
import com.kumar.model.Location;
import com.kumar.model.PaymentDetails;
import com.kumar.model.RideType;
import com.kumar.model.Rider;
import com.kumar.model.Trip;
import com.kumar.model.User;
import com.kumar.repository.DriverRepository;
import com.kumar.repository.TripRepository;
import com.kumar.repository.UserRepository;
import com.kumar.strategy.DriverMatchingStrategy;
import com.kumar.strategy.NearestDriverStrategy;

public class RideService {
	
	private final TripRepository tripRepository;
	private final DriverRepository driverRepository;
	private final UserRepository userRepository;
	private DriverMatchingStrategy matchingStrategy;
	
	public RideService(){
		this.tripRepository = TripRepository.getInstance();
		this.driverRepository = DriverRepository.getInstance();
		this.userRepository = UserRepository.getInstance();
		this.matchingStrategy = new NearestDriverStrategy(); // Default strategy
	}
	
	public void setMatchingStrategy(DriverMatchingStrategy strategy) {
		this.matchingStrategy = strategy;
	}
	
	public Trip requestRide(String riderId,Location pickup,
			Location dropoff,RideType rideType) {
		// Validate rider exists
		User user = userRepository.getUserById(riderId);
		if(!(user instanceof Rider)) {
			throw new IllegalArgumentException("Invalid rider ID");
		}
		
		Rider rider = (Rider) user;
		
		// find available drivers near pickup location
		List<Driver> availableDrivers = driverRepository
				.findAvailableDriversNearLocation(pickup, 5.0); // 5km radius
		
		if(availableDrivers.isEmpty()) {
			Trip trip = new Trip(rider,null,pickup,dropoff,rideType);
			trip.setStatus(Trip.TripStatus.NO_DRIVERS_AVAILABLE);
			tripRepository.addTrip(trip);
			return trip;
		}
		
		//Select driver using strategy
		Driver selectedDriver = matchingStrategy.selectDriver(availableDrivers, pickup);
		
		if(selectedDriver == null) {
			throw new IllegalStateException("No suitable driver found");
		}
		
		//create trip
		Trip trip = RideFactory.createTrip(rider, selectedDriver, pickup, dropoff, rideType);
		// update driver status
		selectedDriver.setStatus(Driver.DriverStatus.BUSY);
		driverRepository.updateDriver(selectedDriver);
		
		
		//save trip
		tripRepository.addTrip(trip);
		
		
		// Notify driver (in real app, This would be a push notification)
		System.out.println("Driver "+selectedDriver.getName()+" notified about me ride request");
		
		return trip;
	}
	
	public void acceptRide(String tripId,String driverId) {
		Trip trip = tripRepository.getTripById(tripId);
		Driver driver = driverRepository.getDriverById(driverId);
		
		if(trip == null || driver == null) {
			throw new IllegalArgumentException("Invalid trip or drivern ID");
		}
		
		if(trip.getStatus() != Trip.TripStatus.REQUESTED) {
			throw new IllegalArgumentException("Ride cannot be accepted in current state");
		}
		
		trip.setStatus(Trip.TripStatus.DRIVER_ASSIGNED);
		driver.setStatus(Driver.DriverStatus.ON_TRIP);
		
		tripRepository.updateTrip(trip);
		driverRepository.updateDriver(driver);
	}
	
	public void startRide(String tripId) {
		Trip trip = tripRepository.getTripById(tripId);
		
		if(trip == null) {
			throw new IllegalArgumentException("Invalid trip ID");
		}
		
		if(trip.getStatus() != Trip.TripStatus.DRIVER_ASSIGNED) {
			throw new IllegalArgumentException("Ride cannot be started in current state");
		}
		
		trip.setStatus(Trip.TripStatus.IN_PROGRESS);
		tripRepository.updateTrip(trip);
	}
	
	public void completedRide(String tripId) throws IllegalAccessException {
		Trip trip = tripRepository.getTripById(tripId);
		
		if(trip == null) {
			throw new IllegalArgumentException("Invalid trip ID");
		}
		
		if(trip.getStatus() != Trip.TripStatus.IN_PROGRESS) {
			throw new IllegalAccessException("Ride cannot be completed in current state");
		}
		
		trip.completeTrip();
		
		//Update driver status and earnings
		Driver driver = trip.getDriver();
		driver.setStatus(Driver.DriverStatus.AVAILABLE);
		driver.addCompletedTrip(trip);
		driver.addRating(4.5+ Math.random() *0.5); // Random rating 4.5-5.0
		
		// Update rider
		Rider rider = trip.getRider();
		rider.addTripToHistory(trip);
		rider.addRating(4.5+ Math.random() * 0.5);
		
		// Process Payment
		processPayment(trip);
		
		// Update repositories
		tripRepository.updateTrip(trip);
		driverRepository.updateDriver(driver);
		userRepository.updateUser(rider);
		
		System.out.println("Ride completed successfully. Total fare: $"+trip.getTotalFare());
	}
	
	
	public void cancelRide(String tripId,String cancelledBy) {
		Trip trip = tripRepository.getTripById(tripId);
		
		if(trip == null) {
			throw new IllegalArgumentException("Invalid trip ID");
		}
		
		trip.cancelTrip();
		
		//if driver was assigned, make them available again
		if(trip.getDriver() != null) {
			Driver driver = trip.getDriver();
			driver.setStatus(Driver.DriverStatus.AVAILABLE);
			driverRepository.updateDriver(driver);
		}
		
		tripRepository.updateTrip(trip);
		System.out.println("Ride cancelled by: "+cancelledBy);
	}
	
	
	private void processPayment(Trip trip) {
		PaymentDetails payment = new PaymentDetails(trip.getTotalFare(), 
				PaymentDetails.PaymentMethod.CREDIT_CARD);
		payment.setStatus(PaymentDetails.PaymentStatus.COMPLETED);
		payment.setTransactionId("TXN"+ System.currentTimeMillis());
		trip.setPaymentDetails(payment);
	}
	
	public List<Trip> getRiderTripHistory(String riderId){
		return tripRepository.getTripsByRider(riderId);
	}
	
	public List<Trip> getDriverTripHistory(String driverId){
		return tripRepository.getTripsByRider(driverId);
	}
}	
