package Constants;


public class AppConstants {
    // Pricing constants
    public static final double BASE_FARE = 50.0;
    public static final double PER_KM_RATE = 10.0;
    public static final double PER_MINUTE_RATE = 2.0;
    
    // Service constants
    public static final double SEARCH_RADIUS_KM = 5.0;
    public static final double AVERAGE_SPEED_KMH = 30.0;
    public static final double SURGE_MULTIPLIER = 1.5;
    
    // Commission rates
    public static final double UBER_COMMISSION_RATE = 0.25; // 25%
    public static final double DRIVER_EARNING_RATE = 0.75; // 75%
    
    // Time constants (in minutes)
    public static final int MAX_WAIT_TIME = 10;
    public static final int CANCELLATION_WINDOW = 5;
}
