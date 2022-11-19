package simulation.Patients;

public class A2Patient implements Patient {
    private int priority;

    @Override
    public int getPriority() {
        return priority;
    }
    @Override
    public String getType() {
        return "A2";
    }

    
}
