package simulation.Patients;

public class A1Patient implements Patient {
    private int priority;

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public String getType() {
        return "A1";
    }
    
}
