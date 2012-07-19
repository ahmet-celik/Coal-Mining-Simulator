/**
 * Parent class of Vehicle types. 
 * Also contains static methods for probability distrubition calculations and common abstract methods of vehicles.
 *
 * @author Ahmet Çelik 2009400111
 * @version 1.0
 */
public abstract class Vehicle {
	/**
	 * ID of a vehicle
	 */
	protected int ID;
	
	/**
	 * Numbers of tour a vehicle made
	 */
	protected int numTour;
	
	/**
	 * Total loaded time of a vehicle
	 */
	protected double loadedTime;
	
	/**
	 * Loading finish time of a vehicle
	 */
	protected double loadFinishTime;
	
	/**
	 * Start time of Round Trip of a Vehicle
	 */
	protected double roundTripTimeStart;
	
	/**
	 * Calculates travel times that have a triangular distribution.
	 * @param a Minimum number 
	 * @param c Middle number
	 * @param b Maximum number
	 * @param r Random probability
	 * @return Travel times that have a triangular distribution
	 */
	public static double triangular(double a,double c,double b,double r){
		double Fc = (c-a)/(b-a);
		if(r<Fc)
		return a+Math.sqrt(r*(b-a)*(c-a));
		else 
		return b-Math.sqrt((1-r)*(b-a)*(b-c));
	}
	
	/**
	 * 
	 * Calculates travel times that have a uniform distribution.
	 * @param a Minimum number
	 * @param b Maximum number
	 * @param r Random probability
	 * @return Travel times that have a uniform distribution
	 */
	public static double uniform(double a,double b,double r){
		return a+r*(b-a);	
	}
	
	/**
	 * Calculates loading time of a {@link Vehicle Vehicle} in the Mining Site.
	 * @param r Random probability
	 * @return Loading time of a {@link Vehicle Vehicle}
	 */
	public abstract double eventLoadTime(double r);
	
	/**
	 * Calculates travel time to Mining Site queue from Disposal site.
	 * @param r Random probability
	 * @return Travel time to Mining Site queue
	 */
	public abstract double eventTravelToMineTime(double r);
	
	/**
	 * Calculates travel time to Disposal Site queue from Mining site.
	 * @param r Random probability
	 * @return Travel time to Mining Site queue
	 */
	public abstract double eventTravelToDisposeTime(double r);
	
	/**
	 * Calculates unloading time of a {@link Vehicle Vehicle} in the Disposal Site.
	 * @param r Random probability
	 * @return Unloading time of a {@link Vehicle Vehicle}
	 */
	public abstract double eventUnloadTime(double r);
	
	/**
	 * Increments total tour of a Vehicle type by 1.
	 */
	public abstract void incTotalTour();
	
	/**
	 * Increments loaded tour of a Vehicle type by 1.
	 */
	public abstract  void incLoadTour(); 
	
	/**
	 * Increments total Round Trip Time of a Vehicle type by <code>roundTripTimeTotal</code>.
	 * @param roundTripTimeAdd Amount of increase in total Round Trip Time
	 */
	public abstract void incRoundTripTimeTotal(double roundTripTimeAdd);
	
	
	/**
	 * Overrides default <code>toString</code> method
	 */
	public String toString(){
		return this.getClass().getName()+"#"+this.ID+" made "+this.numTour+" travels with "+Math.round(this.loadedTime*10000)/10000.0+" minutes total loaded time.";
	}
	
}
