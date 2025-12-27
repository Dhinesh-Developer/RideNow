package com.kumar.service;

import com.kumar.model.Driver;
import com.kumar.model.Location;
import com.kumar.model.Rider;
import com.kumar.model.User;
import com.kumar.model.Vehicle;
import com.kumar.repository.DriverRepository;
import com.kumar.repository.UserRepository;

public class UserService {
	
	private final UserRepository userRepository;
	private final DriverRepository driverRepository;
	
	public UserService() {
		this.userRepository = UserRepository.getInstance();
		this.driverRepository = DriverRepository.getInstance();
	}
	
	public Rider registerRider(String name,String email,String phone) {
		Rider rider = new Rider(name,email,phone);
		userRepository.addUser(rider);
		System.out.println("Rider registered successfully: "+rider.getUserId());
		return rider;
	}
	
	public Driver registerDriver(String name,String email,String phone,Vehicle vehicle) {
		Driver driver = new Driver(name,email,phone,vehicle);
		userRepository.addUser(driver);
		driverRepository.addDriver(driver);
		System.out.println("Driver registered successfully: "+driver.getUserId());
		return driver;
	}
	
	public void updatedUserLocation(String userId,Location location) {
		User user = userRepository.getUserById(userId);
		if(user == null) {
			throw new IllegalArgumentException("User not found");
		}
		
		user.setCurrentLocation(location);
		userRepository.updateUser(user);
		
		// if user is a driver, update driver repo too
		if(user instanceof Driver) {
			driverRepository.updateDriverLocation(userId, location);
		}
	}
	
	
	public void verifyDriver(String driverId) {
		Driver driver = driverRepository.getDriverById(driverId);
		if(driver == null) {
			throw new IllegalArgumentException("Driver not found");
		}
		
		driver.setVerified(true);
		driverRepository.updateDriver(driver);
		System.out.println("Driver verified: "+driverId);
		
	}
	
	
	public User getUser(String userId) {
		return userRepository.getUserById(userId);
	}
	
	public void rateUser(String ratedUserId,String raterUserId,double rating) {
		User ratedUser = userRepository.getUserById(raterUserId);
		User rater = userRepository.getUserById(raterUserId);
		
		if(ratedUser == null || rater == null) {
			throw new IllegalArgumentException("User not found");
		}
		
		ratedUser.addRating(rating);
		userRepository.updateUser(ratedUser);
		
		System.out.println(rater.getName() + " rated " + ratedUser.getName() + 
                " with " + rating + " stars");
	}
	
	
}
