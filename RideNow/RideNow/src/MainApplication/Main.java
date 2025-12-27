package MainApplication;

import com.kumar.model.Driver;
import com.kumar.model.Location;
import com.kumar.model.RideType;
import com.kumar.model.Rider;
import com.kumar.model.Trip;
import com.kumar.model.Vehicle;
import com.kumar.observer.RiderNotificationObserver;
import com.kumar.observer.TripObservable;
import com.kumar.repository.TripRepository;
import com.kumar.repository.UserRepository;
import com.kumar.service.RideService;
import com.kumar.service.UserService;
import com.kumar.strategy.FastestArrivalStrategy;

public class Main {
	public static void main(String[] args) {
		
		System.out.println("🚗 RideNow System Starting...\n");
		
		// Initialize services
        UserService userService = new UserService();
        RideService rideService = new RideService();
        
     // Register users
        Rider rider1 = userService.registerRider("Kumar", "kumar@example.com", "9876543210");
        Rider rider2 = userService.registerRider("Priya", "priya@example.com", "8765432109");
        Rider rider3 = userService.registerRider("Arun", "arun@example.com", "7654321098");
        Rider rider4 = userService.registerRider("Sneha", "sneha@example.com", "6543210987");

        // Register drivers with vehicles 
        Vehicle vehicle1 = new Vehicle("TN-01-AB-1234", "Toyota", "Innova", 2020, 
                Vehicle.VehicleType.SUV, 7, Vehicle.Color.WHITE);
        Vehicle vehicle2 = new Vehicle("TN-02-CD-5678", "Maruti", "Swift Dzire", 2021, 
                Vehicle.VehicleType.SEDAN, 4, Vehicle.Color.SILVER);
        Vehicle vehicle3 = new Vehicle("TN-03-EF-9012", "Kia", "Seltos", 2022, 
                Vehicle.VehicleType.SUV, 5, Vehicle.Color.RED);
        Vehicle vehicle4 = new Vehicle("TN-04-GH-3456", "Bajaj", "Auto", 2019, 
                Vehicle.VehicleType.AUTO, 3, Vehicle.Color.BLACK);
        Vehicle vehicle5 = new Vehicle("TN-05-IJ-7890", "Royal Enfield", "Classic 350", 2021, 
                Vehicle.VehicleType.BIKE, 2, Vehicle.Color.BLACK);
        Vehicle vehicle6 = new Vehicle("TN-06-KL-2468", "Hyundai", "Creta", 2020, 
                Vehicle.VehicleType.LUXURY, 5, Vehicle.Color.BLUE);

        // Register drivers 
        Driver driver1 = userService.registerDriver("Muthu", "muthu@example.com", 
                "9000011111", vehicle1);
        Driver driver2 = userService.registerDriver("Suresh", "suresh@example.com", 
                "9000022222", vehicle2);
        Driver driver3 = userService.registerDriver("Rajesh", "rajesh@example.com", 
                "9000033333", vehicle3);
        Driver driver4 = userService.registerDriver("Karthik", "karthik@example.com", 
                "9000044444", vehicle4);
        Driver driver5 = userService.registerDriver("Vijay", "vijay@example.com", 
                "9000055555", vehicle5);
        Driver driver6 = userService.registerDriver("Anand", "anand@example.com", 
                "9000066666", vehicle6);

        // Verify all drivers
        userService.verifyDriver(driver1.getUserId());
        userService.verifyDriver(driver2.getUserId());
        userService.verifyDriver(driver3.getUserId());
        userService.verifyDriver(driver4.getUserId());
        userService.verifyDriver(driver5.getUserId());
        userService.verifyDriver(driver6.getUserId());

        // Update user locations with Tamil Nadu coordinates
        // Chennai locations
        Location riderLocation1 = new Location(13.0827, 80.2707, "Anna Nagar, Chennai");
        Location riderLocation2 = new Location(13.0604, 80.2496, "T Nagar, Chennai");
        Location riderLocation3 = new Location(13.0359, 80.2313, "Mylapore, Chennai");
        Location riderLocation4 = new Location(13.0909, 80.2788, "Kilpauk, Chennai");

        // Driver locations across Chennai
        Location driverLocation1 = new Location(13.0860, 80.2760, "Aminjikarai, Chennai");
        Location driverLocation2 = new Location(13.0478, 80.2427, "Egmore, Chennai");
        Location driverLocation3 = new Location(13.0150, 80.2276, "Adyar, Chennai");
        Location driverLocation4 = new Location(13.0659, 80.2598, "Purasawalkam, Chennai");
        Location driverLocation5 = new Location(13.1003, 80.2964, "Ambattur, Chennai");
        Location driverLocation6 = new Location(13.0225, 80.2204, "Thiruvanmiyur, Chennai");

        // Update rider locations
        userService.updatedUserLocation(rider1.getUserId(), riderLocation1);
        userService.updatedUserLocation(rider2.getUserId(), riderLocation2);
        userService.updatedUserLocation(rider3.getUserId(), riderLocation3);
        userService.updatedUserLocation(rider4.getUserId(), riderLocation4);

        // Update driver locations
        userService.updatedUserLocation(driver1.getUserId(), driverLocation1);
        userService.updatedUserLocation(driver2.getUserId(), driverLocation2);
        userService.updatedUserLocation(driver3.getUserId(), driverLocation3);
        userService.updatedUserLocation(driver4.getUserId(), driverLocation4);
        userService.updatedUserLocation(driver5.getUserId(), driverLocation5);
        userService.updatedUserLocation(driver6.getUserId(), driverLocation6);
        
        userService.updatedUserLocation(rider1.getUserId(), riderLocation1);
        userService.updatedUserLocation(rider2.getUserId(), riderLocation2);
        userService.updatedUserLocation(driver1.getUserId(), driverLocation1);
        userService.updatedUserLocation(driver2.getUserId(), driverLocation2);
		
		//setup observer PAttern
		TripObservable tripObservable = new TripObservable();
		tripObservable.addObserver(new RiderNotificationObserver(rider1.getUserId()));
		
		// Test ride request
		System.out.println("\n----- Test Ride Request ------");
		Location dropoff1 = new Location(37.7959,-122.4004,"Golden Gate park");
		
		
		//Request ride with fastest arrival strategy
		rideService.setMatchingStrategy(new FastestArrivalStrategy());
		Trip trip1 = rideService.requestRide(rider1.getUserId(), driverLocation2, dropoff1, RideType.RIDENOW_X);
		
		System.out.println("Trip requested: "+trip1.getTripId());
		System.out.println("Trip status: "+trip1.getStatus());
		
		if(trip1.getDriver() != null) {
			// Accept and complete ride
			rideService.acceptRide(trip1.getTripId(), trip1.getDriver().getUserId());
			rideService.startRide(trip1.getTripId());
			try {
				rideService.completedRide(trip1.getTripId());
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			
			//Rate each other
			userService.rateUser(rider1.getUserId(), driver1.getUserId(), 5.0);
			userService.rateUser(driver1.getUserId(), rider1.getUserId(), 4.5);
			
		}
		
		 // Test another ride
        System.out.println("\n=== Test Another Ride ===");
        Location dropoff2 = new Location(37.8029, -122.4054, "Fisherman's Wharf");
        Trip trip2 = rideService.requestRide(rider2.getUserId(), riderLocation2, 
                                           dropoff2, RideType.RIDENOW_BLACK);
        
        if (trip2.getDriver() != null) {
            rideService.acceptRide(trip2.getTripId(), trip2.getDriver().getUserId());
            rideService.startRide(trip2.getTripId());
            
            // Cancel ride
            rideService.cancelRide(trip2.getTripId(), "Rider");
        }
        
        // Display trip history
        System.out.println("\n--- Trip History ---");
        System.out.println("Rider 1 trips:");
        rideService.getRiderTripHistory(rider1.getUserId())
                .forEach(trip -> System.out.println("  - " + trip.getTripId() + 
                        ": " + trip.getStatus() + ", $" + trip.getTotalFare()));
        
        System.out.println("\nDriver 1 trips:");
        rideService.getDriverTripHistory(driver1.getUserId())
                .forEach(trip -> System.out.println("  - " + trip.getTripId() + 
                        ": " + trip.getStatus() + ", Earnings: $" + trip.getDriverEarnings()));
        
        // Display statistics
        System.out.println("\n=== System Statistics ===");
        System.out.println("Total registered users: " + UserRepository.getInstance().getTotalUsers());
        System.out.println("Total trips: " + TripRepository.getInstance().getTotalTrips());
        
        System.out.println("\n🚗 RideNow System Terminated!");
    
		
		
	}
}
