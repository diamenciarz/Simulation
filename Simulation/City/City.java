package Simulation.City;

import java.util.ArrayList;



public class City {
    /**
     * city.get(0) is for the hex with the hospical
     */
    public static ArrayList<Hex> city;
    public static int CENTER_X = 0;
    public static int CENTER_Y = 0;
    protected static final int N_HEX = 6;
    private static final double APHOTEM = (Math.sqrt(3) * Hex.RADIUS) / 2;

    public City(){
        city = generateCity();
    }

    private ArrayList<Hex> generateCity(){

        ArrayList<Hex> hex = new ArrayList<>();
        
        hex.add(new Hex(true, CENTER_X, CENTER_Y));

        for (int i = 0; i < N_HEX + 1; i++) {
                double desfase = Math.PI / Hex.POINTS;
                int x = (int) (CENTER_X + 2 * APHOTEM * Math.cos(i * 2 * Math.PI / Hex.POINTS + desfase));
                int y = (int) (CENTER_Y + 2 * APHOTEM * Math.sin(i * 2 * Math.PI / Hex.POINTS + desfase));
                hex.add(new Hex(false, x, y));
        }
        return hex;
    }

}
