package helpers.meth;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.text.Position;

import helpers.Printer;

public class Hexagon {
    ArrayList<Vector2> positions;
    Vector2 middlePoint;
    double radius;

    public Hexagon(Vector2 middlePoint, double radius) {
        this.middlePoint = middlePoint;
        this.radius = radius;
        positions = calculatePositions();
    }

    public final double PUNTOS = 6;

    public Polygon algoritmoHexagonal(double x, double y){


        for (int i = 0; i < PUNTOS; i++) {

        }

        return null;
    }

    public boolean isPointInside(Vector2 position){
        InfLine2D lineP1P2 = new InfLine2D(positions.get(0), positions.get(1));
        InfLine2D lineP2P3 = new InfLine2D(positions.get(1), positions.get(2));
        InfLine2D lineP3P4 = new InfLine2D(positions.get(2), positions.get(3));
        InfLine2D lineP4P5 = new InfLine2D(positions.get(3), positions.get(4));
        InfLine2D lineP5P6 = new InfLine2D(positions.get(4), positions.get(5));
        InfLine2D lineP6P1 = new InfLine2D(positions.get(5), positions.get(0));

        //          _  top
        //point -> /.\ half
        //         \_/ bottom half
        boolean isPointAboveBottomHalf = lineP2P3.isPointAboveLine(position) && lineP3P4.isPointAboveLine(position) && lineP4P5.isPointAboveLine(position);
        boolean isPointBelowTopHalf = !lineP5P6.isPointAboveLine(position) && !lineP6P1.isPointAboveLine(position) && !lineP1P2.isPointAboveLine(position);
        return isPointAboveBottomHalf && isPointBelowTopHalf;
    }

    protected ArrayList<Vector2> calculatePositions() {
        ArrayList<Vector2> calculatedPositions = new ArrayList<>(6);
        for (int degrees = 30; degrees < 360; degrees += 60) {
            //Eliminate errors like -0.5000000000000001, where accuracy is too high and looks bad. Yes, I want it to look good
            double xOffset = (float) Math.sin(degrees * UtilityClass.DEGREES_TO_RADIANS);
            double yOffset = (float) Math.cos(degrees * UtilityClass.DEGREES_TO_RADIANS);
            Vector2 newPosition = new Vector2(middlePoint.x + xOffset, middlePoint.y + yOffset);
            calculatedPositions.add(newPosition);
        }
        return calculatedPositions;
    }

    public static void main(String[] args) {
        Hexagon h = new Hexagon(Vector2.zeroVector(), 1);
        Printer.print(h.positions);
    }
}
