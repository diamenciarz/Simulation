package simulation.Patients;

public interface Patient {
    public int getPriority();

    public static Patient createPatient(){
        double r = Math.random();
        
        if(r < 0.33){
            return new A1Patient();
        }else if(r < 0.66){
            return new A2Patient();
        }else{
            return new BPatient();
        }
    }
}
