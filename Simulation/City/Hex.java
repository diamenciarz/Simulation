package simulation.city;

import java.awt.*;
import java.util.ArrayList;

import simulation.Ambulance;
import simulation.Simulation;

public class Hex {

    private boolean isHospital;
    public final Location location;
    private final Polygon polygon;
    public static final int POINTS = 6;
    public static final double RADIUS = 10;
    public static double APHOTHEMA = Math.sqrt((Math.pow(RADIUS, 2) + Math.pow(RADIUS, 2) / 2));
    private ArrayList<Ambulance> ambulances;

    public Hex(boolean hospital, double x, double y) {

        location = new Location(x, y);
        this.isHospital = hospital;
        this.polygon = generatePoints();
    }
    
    public Hex(boolean hospital, Location hexLocation) {

        location = new Location(hexLocation);
        this.isHospital = hospital;
        this.polygon = generatePoints();
    }
    /**
     * This method creates hexagons with a given center
     * 
     * @return a object polygon that is nice for visualization
     */
    private Polygon generatePoints() {

        Polygon p = new Polygon();
        for (int i = 0; i < POINTS + 1; i++) {
            p.addPoint(
                    (int) (location.getX() + RADIUS * Math.cos(i * 2 * Math.PI / POINTS)),
                    (int) (location.getY() + RADIUS * Math.sin(i * 2 * Math.PI / POINTS)));
        }
        return p;
    }

    public Polygon getPolygon() {
        return polygon;
    }

    public void setHospital(boolean hospital) {
        this.isHospital = hospital;
    }

    public boolean isHospital() {
        return isHospital;
    }

    public Location getLocation() {
        return location;
    }

    public ArrayList<Ambulance> getAmbulances() {
        return ambulances;
    }

    public void setAmbulances(ArrayList<Ambulance> ambulances) {
        this.ambulances = ambulances;

        for (int i = 0; i < ambulances.size(); i++) {
            this.ambulances.get(i).setLocation(location);
            this.ambulances.get(i).setHub(location);
        }
    }
}
