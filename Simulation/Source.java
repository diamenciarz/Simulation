package Simulation;

import helpers.Distributions;
import helpers.Printer;

/**
 *	A source of products
 *	This class implements CProcess so that it can execute events.
 *	By continuously creating new events, the source keeps busy.
 *	@author Joel Karel
 *	@version %I%, %G%
 */
public class Source implements CProcess
{
	/** Eventlist that will be requested to construct events */
	private CEventList eventList;
	/** Queue that buffers products for the machine */
	private ProductAcceptor queue;
	/** Name of the source */
	private String name;
	/** Mean interarrival time */
	private double meanArrTime;
	/** Interarrival times (in case pre-specified) */
	private double[] interarrivalTimes;
	/** Interarrival time iterator */
	private int interArrCnt;

	/**
	*	Constructor, creates objects
	*        Interarrival times are exponentially distributed with mean 33
	*	@param q	The receiver of the products
	*	@param l	The eventlist that is requested to construct events
	*	@param n	Name of object
	*/
	public Source(ProductAcceptor q,CEventList l,String n)
	{
		eventList = l;
		queue = q;
		name = n;
		meanArrTime=33;
		// put first event in list for initialization
		eventList.add(this,0,drawRandomExponential(meanArrTime)); //target,type,time
	}

	/**
	*	Constructor, creates objects
	*        Interarrival times are exponentially distributed with specified mean
	*	@param q	The receiver of the products
	*	@param l	The eventlist that is requested to construct events
	*	@param n	Name of object
	*	@param m	Mean arrival time
	*/
	public Source(ProductAcceptor q,CEventList l,String n,double m)
	{
		eventList = l;
		queue = q;
		name = n;
		meanArrTime=m;
		// put first event in list for initialization
		eventList.add(this,0,drawRandomExponential(meanArrTime)); //target,type,time
	}

	/**
	*	Constructor, creates objects
	*        Interarrival times are prespecified
	*	@param q	The receiver of the products
	*	@param l	The eventlist that is requested to construct events
	*	@param n	Name of object
	*	@param ia	interarrival times
	*/
	public Source(ProductAcceptor q,CEventList l,String n,double[] ia)
	{
		eventList = l;
		queue = q;
		name = n;
		meanArrTime=-1;
		interarrivalTimes=ia;
		interArrCnt=0;
		// put first event in list for initialization
		eventList.add(this,0,interarrivalTimes[0]); //target,type,time
	}
	
	@Override
	public void execute(int type, double tme)
	{
		// show arrival
		Printer.printArrived(eventList.getTime(), name);
		// give arrived product to queue
		Product p = new Product();
		p.stamp(tme,"Creation",name);

		queue.giveProduct(p);
		// generate duration
		if(meanArrTime>0)
		{
			double duration = drawRandomExponential(tme);
			// Create a new event in the eventlist
			eventList.add(this,type,tme+duration); //target,type,time
		}
		else
		{
			interArrCnt++;
			if(interarrivalTimes.length>interArrCnt)
			{
				eventList.add(this,0,tme+interarrivalTimes[interArrCnt]); //target,type,time
			}
			else
			{
				eventList.stop();
			}
		}
	}
	
	public static double drawRandomExponential(double time)
	{
		// draw a [0,1] uniform distributed number
		double u = Math.random();

		double lambda = Distributions.lambda(time);
		// Convert it into a exponentially distributed random variate with lambda
		return  -lambda*Math.log(u);
	}
}