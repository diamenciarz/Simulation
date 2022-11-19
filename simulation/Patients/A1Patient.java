package simulation.Patients;

public class A1Patient implements Patient {
    private int priority;

    public A1Patient (int priority){
        this.priority = priority;
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public String getType() {
        return "A1";
    }
    
}
