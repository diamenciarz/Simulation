package processes;

import simulation.Location;
import simulation.Patients.Patient;

public class Occurrence {
    
    private Patient patient;
    private Location location;
    private double time;

    public Occurrence(double time){
        this.location = new Location();
        this.patient = Patient.createPatient();
        this.time = time;
    }
    public Location getLocation() {
        return location;
    }
    public Patient getPatient() {
        return patient;
    }
    public double getTime() {
        return time;
    }
    
}
