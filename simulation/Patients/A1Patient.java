package simulation.Patients;

public class A1Patient implements Patient {
    public static int priority = 1;

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public String getType() {
        return "A1";
    }
    
}
