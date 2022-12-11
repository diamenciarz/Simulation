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

    public static void setupNightShift(double simulationDuration, int crewCount) {
        createNightShiftAtTime(-1);

        for (int i = 0; i < simulationDuration % 24; i++) {
            double nightShiftStartTime = 24 * i + 23;
            createNightShiftAtTime(nightShiftStartTime);
        }
    }
    
    private static void createNightShiftAtTime(double nightShiftStartTime){
        ArrayList<Hex> hexMap = City.getHexMap();

        for (int i = 0; i < City.getHexMap().size(); i++) {
        
            Hex hex = hexMap.get(i);
            StartShift startShiftEvent = new StartShift(hex, i, 8);
            eventList.add(startShiftEvent, MAXIMUM_AMBULANCE_COUNT_PER_HEX, nightShiftStartTime);
        }
    }

    public static Ambulance askForCrew(Vector2 targetPosition) {
        HubInfo hubInfo = findClosestHub(targetPosition);
        if (hubInfo.hex == null) {
            System.out.println("All hexes are full - no new ambulance can be added");
            return null;
        }
        if (canScheduleCrew(eventList.getHour()) || isReady) {
            double shiftDuration = calculateShiftDuration(eventList.getHour());
            Ambulance newAmbulance = addCrew(hubInfo.hex, hubInfo.hexIndex, shiftDuration);
            return newAmbulance;
        } else {
            // Shifts that start as soon as possible will all have a duration of 8.
            StartShift startShiftEvent = new StartShift(hubInfo.hex, hubInfo.hexIndex, 8);

            double shiftStartTime = findShiftStart(eventList.getTime());
            eventList.add(startShiftEvent, MAXIMUM_AMBULANCE_COUNT_PER_HEX, shiftStartTime);
            return null;
        }
    }

    private static double findShiftStart(double currentTime) {
        double currentHour = currentTime % 24;
        double deltaTime = 0;
        if (currentHour > 22) {
            deltaTime = 7 + (24 - currentHour);
        } else {
            deltaTime = 7 - currentHour;
        }

        return currentTime + deltaTime;
    }

    private static boolean canScheduleCrew(double currentHour) {
        return currentHour > 7 && currentHour < 22;
    }

    private static double calculateShiftDuration(double currentHour) {
        if (currentHour > 18) {
            return 4;
        } else {
            return 8;
        }
    }

    public static Ambulance addCrew(Hex hex, int hexIndex, double shiftDuration) {
        Ambulance newAmbulance = instantiateAmbulance(hex, hexIndex);
        addAmbulanceToHex(hex, newAmbulance, shiftDuration);
        DebugLogger.printAddedShift(eventList.getTime());
        return newAmbulance;
    }

    private static boolean isReady = true;

    private static HubInfo findClosestHub(Vector2 targetPosition) {
        ArrayList<Hex> sortedHexes = City.getClosestHexesTo(targetPosition);
        HubInfo hubInfo = new HubInfo();
        for (int i = 0; i < sortedHexes.size(); i++) {
            int numberOfAmbulancesInHex = sortedHexes.get(i).getAmbulances().size();
            if (numberOfAmbulancesInHex < MAXIMUM_AMBULANCE_COUNT_PER_HEX) {
                hubInfo.hex = sortedHexes.get(i);
                hubInfo.hexIndex = i;
                break;
            }
        }
        return hubInfo;
    }

    private static void addAmbulanceToHex(Hex hex, Ambulance newAmbulance, double shiftDuration) {
        hex.getAmbulances().add(newAmbulance);
        double timestamp = eventList.getTime() + shiftDuration;

        eventList.add(new EndShift(newAmbulance, timestamp), MAXIMUM_AMBULANCE_COUNT_PER_HEX, timestamp);
    }

    private static Ambulance instantiateAmbulance(Hex hex, int hexIndex) {
        int ambulanceID = hex.getAmbulances().size() + 1;
        String ambulanceName = "Machine " + ambulanceID + " H " + hexIndex;
        Ambulance newAmbulance = new Ambulance(queue, sink, eventList, ambulanceName, hex);
        return newAmbulance;
    }

    private static class HubInfo {
        public Hex hex = null;
        public int hexIndex = 0;
    }
}