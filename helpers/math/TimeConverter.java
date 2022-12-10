package helpers.math;

public class TimeConverter {

    private final static double AVERAGE_AMBULANCE_VELOCITY = 60;
    public static double getTravelTime(double distance){
        return distance / AVERAGE_AMBULANCE_VELOCITY;
    }
}
