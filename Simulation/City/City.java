package Simulation.City;

import java.util.ArrayList;

public class City {
    /**
     * city.get(0) is for the hex with the hospical
     */
    private static ArrayList<Hex> hexMap;
    public final static Location centerLocation = new Location(0,0);
    protected static final int N_HEX = 6;
    private static final double APHOTEM = (Math.sqrt(3) * Hex.RADIUS) / 2;
    private static boolean alreadyGeneratedCity = false;

    public City() {
        hexMap = generateCity();
        alreadyGeneratedCity = true;
    }
    
    public static ArrayList<Hex> getHexMap(){
        if (!alreadyGeneratedCity) {
            hexMap = generateCity();
            alreadyGeneratedCity = true;
        }
        return hexMap;
    }

    private static ArrayList<Hex> generateCity() {

        ArrayList<Hex> hex = new ArrayList<>();

        hex.add(new Hex(true, centerLocation));

        for (int i = 0; i < N_HEX + 1; i++) {
            double desfase = Math.PI / Hex.POINTS;
            int x = (int) (centerLocation.getX() + 2 * APHOTEM * Math.cos(i * 2 * Math.PI / Hex.POINTS + desfase));
            int y = (int) (centerLocation.getY() + 2 * APHOTEM * Math.sin(i * 2 * Math.PI / Hex.POINTS + desfase));
            hex.add(new Hex(false, x, y));
        }
        return hex;
    }

}
