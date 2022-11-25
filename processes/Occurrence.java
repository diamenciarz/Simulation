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

    public Occurrence(double time){
        this.location = new Location();
        this.patient = Patient.createPatient();
        this.time = time;
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
        return location.getRegion();
    }
    public String toString() {
        return "Patient of type "+patient.getType()+ " arrived at "+ time+ " in region "+ getRegion();
    }

    
}
