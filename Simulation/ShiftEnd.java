package simulation;

import helpers.DebugLogger;

public class ShiftEnd implements CProcess {

    public Ambulance ambulance;
    public double timestamp;

    public ShiftEnd(Ambulance newAmbulance, double newTimestamp){
        this.ambulance = newAmbulance;
        this.timestamp = newTimestamp;
    }

    @Override
    public void execute(int type, double endTime) {
        ambulance.isShiftEnded = true;
        //TODO: Update ambulance time series data
        DebugLogger.printShiftEnded(endTime);
    }
    
}
