package simulation.Patients;

public class BPatient implements Patient {

    private int priority;
    @Override
    public int getPriority() {
        return priority;
    }
    @Override
    public String getType() {
        return "A3";
    }

}
