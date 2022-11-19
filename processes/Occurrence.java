package processes;

import simulation.Location;
import simulation.Map;
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
        this.region = location.getRegion();
        Map.addOccurrence(this);
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
    public String toString() {
        return "Patient of type "+patient.getType()+ " arrived at "+ time+ " in region "+ region;
    }

    
}
