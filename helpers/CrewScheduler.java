package helpers;

import java.util.ArrayList;

import helpers.math.Vector2;
import simulation.*;
import simulation.Ambulance;
import simulation.city.City;
import simulation.city.Hex;

public class CrewScheduler {
    public static CEventList eventList;
    public static Queue queue;
    public static Sink sink;

    private static final int MAXIMUM_AMBULANCE_COUNT_PER_HEX = 5;

    public static Ambulance addCrew(Vector2 targetPosition) {
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
            return null;
        }

        Ambulance newAmbulance = instantiateAmbulance(closestNonfullHex, hexIndex);
        addAmbulanceToHex(closestNonfullHex, newAmbulance);
        return newAmbulance;
    }

    private static void addAmbulanceToHex(Hex hex, Ambulance newAmbulance) {
        // TODO: Choose shift length to be 4 or 8. Beware of scheduling limits.
        final double SHIFT_DURATION = 8;
        hex.getAmbulances().add(newAmbulance);
        double timestamp = eventList.getTime() + SHIFT_DURATION;

        eventList.add(new ShiftEnd(newAmbulance, timestamp), MAXIMUM_AMBULANCE_COUNT_PER_HEX, timestamp);
    }

    private static Ambulance instantiateAmbulance(Hex hex, int hexIndex) {
        int ambulanceID = hex.getAmbulances().size() + 1;
        String ambulanceName = "Machine " + ambulanceID + " H " + hexIndex;
        Ambulance newAmbulance = new Ambulance(queue, sink, eventList, ambulanceName);
        newAmbulance.setHub(hex);
        return newAmbulance;
    }
}