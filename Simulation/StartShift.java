package simulation;

import helpers.CrewScheduler;
import simulation.city.Hex;

public class StartShift implements CProcess {

    public Hex hex;
    public int hexIndex;
    public double shiftDuration;

    public StartShift(Hex newHex, int newIndex, double shiftDuration) {
        this.shiftDuration = shiftDuration;
        this.hex = newHex;
        this.hexIndex = newIndex;
    }

    @Override
    public void execute(int type, double endTime) {
        CrewScheduler.addCrew(hex, hexIndex, shiftDuration);
    }

}