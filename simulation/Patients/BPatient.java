package simulation.Patients;

public class BPatient implements Patient {

    private int priority;
    public BPatient (int priority){
        this.priority = priority;
    }
    @Override
    public int getPriority() {
        return priority;
    }
    
}
