package simulation.Patients;

public class BPatient implements Patient {
    
    public static int priority = 3;

    @Override
    public int getPriority() {
        return priority;
    }
    @Override
    public String getType() {
        return "A3";
    }

}
