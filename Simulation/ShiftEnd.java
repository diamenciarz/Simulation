package simulation;

public class ShiftEnd implements CProcess {

    public Ambulance ambulance;
    public double timestamp;

    public ShiftEnd(Ambulance newAmbulance, double newTimestamp){
        this.ambulance = newAmbulance;
        this.timestamp = newTimestamp;
    }

    @Override
    public void execute(int type, double tme) {
        ambulance.endShift();
        //TODO: Update ambulance time series data
        System.out.println("Shift ended");
    }
    
}
