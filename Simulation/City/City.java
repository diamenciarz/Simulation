package simulation.city;

import java.util.ArrayList;

import helpers.math.Vector2;

public class City {
    /**
     * city.get(0) is for the hex with the hospical
     */
    private static ArrayList<Hex> hexMap;
    public final static Vector2 centerPosition = Vector2.zeroVector();
    protected static final int N_HEX = 6;
    private static final double APHOTEM = (Math.sqrt(3) * Hex.RADIUS) / 2;
    private static boolean alreadyGeneratedCity = false;

    public City() {
        hexMap = generateCity();
        alreadyGeneratedCity = true;
    }

    public static ArrayList<Hex> getHexMap() {
        if (!alreadyGeneratedCity) {
            hexMap = generateCity();
            alreadyGeneratedCity = true;
        }
        return hexMap;
    }

    public static ArrayList<Hex> copyHexMap() {
        ArrayList<Hex> hexes = new ArrayList<>();
        for (Hex hex : hexMap) {
            hexes.add(new Hex(hex.isHospital(), hex.getPosition()));
        }
        return hexes;
    }

    public static ArrayList<Hex> getClosestHexesTo(Vector2 position) {
        ArrayList<Hex> unsortedHexes = City.getHexMap();
        ArrayList<Hex> sortedHexes = new ArrayList<>();

        for (int i = 0; i < unsortedHexes.size(); i++) {
            Hex closestHex = findClosestHex(unsortedHexes, position);
            sortedHexes.add(closestHex);
            unsortedHexes.remove(closestHex);
        }
        return sortedHexes;
    }

    private static Hex findClosestHex(ArrayList<Hex> hexes, Vector2 position) {
        Hex currentClosestHex = hexes.get(0);
        double currencShortestDistance = currentClosestHex.position.distanceTo(position);
        for (Hex hex : hexes) {
            double newDistance = hex.position.distanceTo(position);
            if (newDistance < currencShortestDistance) {
                currentClosestHex = hex;
                currencShortestDistance = newDistance;
            }
        }
        return currentClosestHex;
    }

    private static ArrayList<Hex> generateCity() {

        ArrayList<Hex> hex = new ArrayList<>();

        hex.add(new Hex(true, centerPosition));

        for (int i = 0; i < N_HEX + 1; i++) {
            double desfase = Math.PI / Hex.POINTS;
            Vector2 hexPosition = Vector2.zeroVector();
            hexPosition.x = (int) (centerPosition.x + 2 * APHOTEM * Math.cos(i * 2 * Math.PI / Hex.POINTS + desfase));
            hexPosition.y = (int) (centerPosition.y + 2 * APHOTEM * Math.sin(i * 2 * Math.PI / Hex.POINTS + desfase));
            hex.add(new Hex(false, hexPosition));
        }
        return hex;
    }

    public static boolean isPositionOnMap(Vector2 position) {

        for (int i = 0; i < getHexMap().size(); i++) {
            if (City.getHexMap().get(i).getPolygon().contains(position.x, position.y)) {
                return true;
            }
        }
        return false;
    }
}
