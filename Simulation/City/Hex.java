package Simulation.City;
import Simulation.Machine;
import Simulation.Simulation;

import java.awt.*;
import java.util.ArrayList;


public class Hex {
    

    private boolean hospital;
    private final double[] coordinates;
    private  final Polygon polygon;
    public static final int POINTS = 6;
    public static final double RADIUS = 10;
    private ArrayList<Machine> ambulances;

    public Hex(boolean hospital, double x, double y){

        coordinates = new double[] {x, y};
        this.hospital = hospital;
        this.polygon = generatePoints();
    }

    /**
     * This method creates hexagons with a given center
     * @return a object polygon that is nice for visualization
     */
    private Polygon generatePoints(){

        Polygon p = new Polygon();
        for (int i = 0; i < POINTS+1; i++) {
            p.addPoint(
                    (int) (coordinates[0] + RADIUS * Math.cos(i * 2 * Math.PI / POINTS)),
                    (int) (coordinates[1] + RADIUS * Math.sin(i * 2 * Math.PI / POINTS))
            );
        }
        return p;
    }


    public Polygon getPolygon() {return polygon;}

    public void setHospital(boolean hospital) {this.hospital = hospital;}

    public boolean isHospital() {return hospital;}

    public double[] getCoordinates() {return coordinates;}

    public ArrayList<Machine> getAmbulances() {return ambulances;}

    public void setAmbulances(ArrayList<Machine> ambulances) {
        this.ambulances = ambulances;

        for (int i = 0; i < ambulances.size(); i++) {
            this.ambulances.get(i);
        }
    }
}
