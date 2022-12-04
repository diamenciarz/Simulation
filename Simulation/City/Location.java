package Simulation.City;

import java.util.Vector;

import helpers.math.Vector2;

public class Location {
    private double x;
    private double y;

    public static double hexagonSize;

    public Location() {
        createLocation();
    }

    public Location(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    public Location(Vector2 position) {
        this.x = position.x;
        this.y = position.y;
    }

    public Location(Location copyLocation) {
        this.x = copyLocation.x;
        this.y = copyLocation.y;
    }

    public void createLocation() {
        double x;
        double y;
        do {
            x = Math.random() * 6 * Hex.RADIUS;
            y = Math.random() * 6 * Hex.APHOTHEMA;
        } while (!inside(x, y));
        this.x = x;
        this.y = y;
    }

    public boolean inside(double x, double y) {

        for (int i = 0; i < City.getHexMap().size(); i++) {
            if (City.getHexMap().get(i).getPolygon().contains(x, y)) {
                return true;
            }
        }
        return false;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    /**
     * returns the manhattan distance between two locations.
     * 
     * @param other
     * @return
     */
    public double manhattan(Location other) {
        return Math.abs(this.x - other.getX()) + Math.abs(this.y - other.getY());
    }

    public double manhattan2Points(Location b, Location a) {
        return Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY());
    }
}
