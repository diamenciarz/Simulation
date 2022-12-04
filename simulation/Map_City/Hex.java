package simulation.Map_City;

import processes.Machine;
import processes.Occurrence;

import java.awt.*;
import java.util.ArrayList;

public class Hex{

    //TO DO TRANSLATE MY COMENTS THAT ARE IN SPANISH

    private boolean hospital;
    private  ArrayList<Occurrence> patients;
    private  ArrayList<Machine> ambulances;
    private final double[] coordinates;
    private  final Polygon polygon;
    public static final int POINTS = 6;
    public static final double RADIUS = 100;
    public static final int CIRCLE_HOSPITAL = 10;
    public static double ambulanceX;
    public static double ambulanceY;
    public double vel = 0.001;

    public Hex(boolean hospital, double x, double y){

        patients = new ArrayList<>();
        coordinates = new double[] {x, y};
        ambulanceX = x;
        ambulanceY = y;
        this.hospital = hospital;
        this.polygon = generatePoints();

    }

    public static void up(){
        ambulanceY -= 10;
    }

    public static void down(){
        ambulanceY += 10;
    }

    public static void left(){
        ambulanceX -= 10;
    }

    public static void right(){
        ambulanceX += 10;
    }



    public void moveX(double x){

        if(ambulanceX < x){
            ambulanceX += vel;
        }else {
            ambulanceX -= vel;
        }

    }

    public void moveY(double y){

        if(ambulanceY < y){
            ambulanceY += vel;
        }else {
            ambulanceY -= vel;
        }
    }

    public void generateOcurrencies(int time){

        patients.add(new Occurrence(time));
    }

    public void removeOcurrence(Occurrence ocurence){
        patients.remove(ocurence);
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

    public  void clear(){
        patients = new ArrayList<>();
    }

    public Polygon getPolygon() {return polygon;}

    public void setHospital(boolean hospital) {this.hospital = hospital;}

    public ArrayList<Occurrence> getPatients() {return patients;}

    public void setPatients(ArrayList<Occurrence> patients) {this.patients = patients;}

    public boolean isHospital() {return hospital;}

    public double[] getCoordinates() {return coordinates;}


}
