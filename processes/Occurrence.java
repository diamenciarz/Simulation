package processes;

import simulation.Location;
import simulation.Patients.Patient;

/**
 * An occurrence object stores a new patient appearing at a given time and a given location
 */
public class Occurrence {

    private Patient patient;
    private Location location;
    private double time;
    private int region;

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
    public int getRegion() {
        return region;
    }
    
}
