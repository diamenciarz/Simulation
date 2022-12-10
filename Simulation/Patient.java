package simulation;

import java.util.ArrayList;

import helpers.RandGenerator;
import helpers.math.Vector2;

/**
 *	Product that is send trough the system
 *	@author Joel Karel
 *	@version %I%, %G%
 */
public class Patient
{
	/** Stamps for the products */
	public ArrayList<Double> times;
	public ArrayList<Double> kjb;
	public ArrayList<String> events;
	public ArrayList<String> stations;
	public Vector2 position;
	
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
		position = RandGenerator.generateRandomPosition();
	}
	
	
	public void stamp(double time,String event,String station)
	{
		times.add(time);
		events.add(event);
		stations.add(station);
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
