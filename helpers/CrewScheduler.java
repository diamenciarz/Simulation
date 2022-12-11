package helpers;

import java.util.ArrayList;

import helpers.math.Vector2;
import simulation.*;
import simulation.Ambulance;
import simulation.City.City;
import simulation.City.Hex;

public class CrewScheduler {
    public static CEventList eventList;
    public static Queue queue;
    public static Sink sink;

    private static final int MAXIMUM_AMBULANCE_COUNT_PER_HEX = 5;

    public static void addCrew(Vector2 targetPosition) {
        ArrayList<Hex> sortedHexes = City.getClosestHexesTo(targetPosition);
        Hex closestNonfullHex = null;
        int hexIndex = 0;
        for (int i = 0; i < sortedHexes.size(); i++) {
            int numberOfAmbulancesInHex = sortedHexes.get(i).getAmbulances().size();
            if (numberOfAmbulancesInHex < MAXIMUM_AMBULANCE_COUNT_PER_HEX) {
                closestNonfullHex = sortedHexes.get(i);
                hexIndex = i;
                break;
            }
        }
        if (closestNonfullHex == null) {
            System.out.println("All hexes are full - no new ambulance can be added");
        }
        addAmbulanceToHex(closestNonfullHex, hexIndex);
    }

    private static void addAmbulanceToHex(Hex hex, int hexIndex) {
        // TODO: Choose shift length to be 4 or 8. Beware of scheduling limits.
        double shiftDuration = 8;
        int ambulanceID = hex.getAmbulances().size() + 1;
        String ambulanceName = "Machine " + ambulanceID + " H " + hexIndex;
        Ambulance newAmbulance = new Ambulance(queue, sink, eventList, ambulanceName);
        eventList.add(new ShiftEnd(newAmbulance, eventList.getTime()), MAXIMUM_AMBULANCE_COUNT_PER_HEX, shiftDuration);

        hex.getAmbulances().add(newAmbulance);
    }
}