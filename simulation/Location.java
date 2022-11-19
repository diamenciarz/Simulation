package simulation;

public class Location {
    private double x;
    private double y;
    private int region;

    public static double hexagonSize;

    public Location(){
        createLocation();
    }
    public void createLocation(){
        double x;
        double y;
        do{
            x = Math.random() * hexagonSize;
            y = Math.random() * hexagonSize;
        }while(!inside(x, y));
        this.x = x;
        this.y = y;
    }

    //TODO: Write method to check whether the values x and y make a valid position
    public boolean inside(double x, double y){
        return true;
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
