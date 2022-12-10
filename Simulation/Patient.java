package simulation;

import java.util.ArrayList;

import helpers.math.Vector2;
import simulation.city.Location;

/**
 *	Product that is send trough the system
 *	@author Joel Karel
 *	@version %I%, %G%
 */
class Patient
{
	/** Stamps for the products */
	private ArrayList<Double> times;
	private ArrayList<Double> kjb;
	private ArrayList<String> events;
	private ArrayList<String> stations;
	private Location location;
	
	/** 
	*	Constructor for the product
	*	Mark the time at which it is created
	*	@param create The current time
	*/
	public Patient()
	{
		times = new ArrayList<>();
		events = new ArrayList<>();
		stations = new ArrayList<>();
		location = new Location();
	}
	
	
	public void stamp(double time,String event,String station)
	{
		times.add(time);
		events.add(event);
		stations.add(station);
	}
	
	public ArrayList<Double> getTimes()
	{
		return times;
	}

	public ArrayList<String> getEvents()
	{
		return events;
	}

	public Location getLocation(){
		return location;
	}

	public ArrayList<String> getStations()
	{
		return stations;
	}
	
	public double[] getTimesAsArray()
	{
		times.trimToSize();
		double[] tmp = new double[times.size()];
		for (int i=0; i < times.size(); i++)
		{
			tmp[i] = (times.get(i)).doubleValue();
		}
		return tmp;
	}

	public String[] getEventsAsArray()
	{
		String[] tmp = new String[events.size()];
		tmp = events.toArray(tmp);
		return tmp;
	}

	public String[] getStationsAsArray()
	{
		String[] tmp = new String[stations.size()];
		tmp = stations.toArray(tmp);
		return tmp;
	}
}
