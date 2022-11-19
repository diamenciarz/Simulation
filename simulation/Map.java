package simulation;

import java.util.ArrayList;
import java.util.HashMap;
import processes.Occurrence;

public class Map {

    /**Stores the occurrences in a region given the region*/
    public static HashMap<Integer, ArrayList<Occurrence>> occurrences = new HashMap<>();

    
    public static ArrayList<Occurrence> getOccurrences(int region) {
        return occurrences.get(region);
    }

    /**Adds a new occurrence to its region */
    public static void addOccurrence(Occurrence o){
        ArrayList<Occurrence> newOccurrences = occurrences.get(o.getRegion());
        if(newOccurrences == null){
            newOccurrences = new ArrayList<>();
        }
        newOccurrences.add(o);
        occurrences.put(o.getRegion(), newOccurrences);
    }

    public static void main(String[] args) {

        int regions = 7;
        //Create 3 random patients
        for(int i = 0; i < 3; i++){
            new Occurrence(i);
        }

        //Printing the patients in each region
        for(int i = 0; i < regions; i++){
            System.out.println(occurrences.get(i));
        }
    }




}
