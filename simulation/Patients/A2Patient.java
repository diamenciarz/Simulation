package simulation.Patients;

public class A2Patient implements Patient {
    public static int priority = 2;

    @Override
    public int getPriority() {
        return priority;
    }
    @Override
    public String getType() {
        return "A2";
    }

    
}
