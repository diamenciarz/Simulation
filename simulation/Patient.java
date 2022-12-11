package simulation;

import java.util.ArrayList;
import java.util.UUID;

import helpers.RandGenerator;
import helpers.math.Vector2;
import helpers.stats.StatsCollector;

/**
 *	Product that is send trough the system
 *	@author Joel Karel
 *	@version %I%, %G%
 */
public class Patient
{
	/** Stamps for the products */
	public ArrayList<Double> times;
	public ArrayList<String> eventName;
	public ArrayList<String> ambulanceName;
	public Vector2 position;
	public double creationTime;
	public double waitingTime;
	private UUID patientID;
	
	/** 
	*	Constructor for the product
	*	Mark the time at which it is created
	*	@param create The current time
	*/
	public Patient()
	{
		times = new ArrayList<>();
		eventName = new ArrayList<>();
		ambulanceName = new ArrayList<>();
		position = RandGenerator.generateRandomPosition();
		patientID = UUID.randomUUID();
	}
	
	
	public void stampEvent(double time,String event,String station)
	{
		times.add(time);
		eventName.add(event);
		ambulanceName.add(station);
	}

	public void stampWaitTime(double waitingTime){
		System.out.println("Wait time: " + waitingTime);
		this.waitingTime = waitingTime;
		StatsCollector.collectPatient(this,waitingTime,ambulanceName.get(0) );
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
		String[] tmp = new String[eventName.size()];
		tmp = eventName.toArray(tmp);
		return tmp;
	}

	public String[] getStationsAsArray()
	{
		String[] tmp = new String[ambulanceName.size()];
		tmp = ambulanceName.toArray(tmp);
		return tmp;
	}
	public UUID getPatientID()
	{
		return patientID;
	}
}
