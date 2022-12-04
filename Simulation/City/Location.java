package Simulation.City;

public class Location {
    private double x;
    private double y;
    private int region;

    public static double hexagonSize;

    public Location(){
        createLocation();
    }

    public Location(int x, int y){
        this.x = x;
        this.y = y;
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
        this.region = (int)(Math.random()*7);
    }

    public boolean inside(double x, double y){

        for (int i = 0; i < City.city.size(); i++) {
            if(City.city.get(i).getPolygon().contains(x, y)){
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
     * @param other
     * @return
     */
    public double manhattan(Location other){
        return Math.abs(this.x - other.getX()) + Math.abs(this.y - other.getY());
    }

    public double manhattan2Points(Location b, Location a){
        return Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY());
    }
}
