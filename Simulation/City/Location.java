package Simulation.City;

public class Location {
    private double x;
    private double y;
    private int region;

    public static double hexagonSize;

    public Location(){
        createLocation();
    }

    /*TODO: set the region according to the hexagon */
    public void createLocation(){
        double x;
        double y;
        do{
            x = Math.random() * hexagonSize;
            y = Math.random() * hexagonSize;
        }while(!inside(x, y));
        this.x = x;
        this.y = y;
        this.region = (int)(Math.random()*7);
    }

    //TODO: Write method to check whether the values x and y make a valid position
    public boolean inside(double x, double y){

        for (int i = 0; i < City.city.size(); i++) {
            if(City.city.get(i).getPolygon().contains(x, y)){
                return true;
            }
        }
        return false;
    }
    public int getRegion() {
        return region;
    }

    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
}
