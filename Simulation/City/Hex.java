package simulation.city;

import java.awt.*;
import java.util.ArrayList;

import helpers.math.Vector2;
import simulation.Ambulance;

public class Hex {

    private boolean isHospital;
    public final Vector2 position;
    private final Polygon polygon;
    public static final int POINTS = 6;
    public static final double RADIUS = 5;
    public static double APHOTHEMA = Math.sqrt((Math.pow(RADIUS, 2) + Math.pow(RADIUS, 2) / 2));
    private ArrayList<Ambulance> ambulances;

    public Hex(boolean hospital, Vector2 newPosition) {

        position = newPosition.copy();
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
                    (int) (position.x + RADIUS * Math.cos(i * 2 * Math.PI / POINTS)),
                    (int) (position.y + RADIUS * Math.sin(i * 2 * Math.PI / POINTS)));
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

    public Vector2 getPosition() {
        return position;
    }

    public ArrayList<Ambulance> getAmbulances() {
        return ambulances;
    }

    public void setAmbulances(ArrayList<Ambulance> ambulances) {
        this.ambulances = ambulances;

        for (int i = 0; i < ambulances.size(); i++) {
            // this.ambulances.get(i).setPosition(position);
            this.ambulances.get(i).setHub(this);
        }
    }
}
