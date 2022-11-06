package processes;

public class EndingEvent implements IProcess {

    @Override
    public void execute(int type, double tme) {
        EventList.stopSimulation();
    }
    
}
