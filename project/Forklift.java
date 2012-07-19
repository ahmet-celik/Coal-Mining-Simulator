/**
 * Defines Forklift Vehicle.
 * 
 * @author Ahmet Çelik 2009400111
 * @version 1.0
 * 
 */
public class Forklift extends Vehicle {
	/**
	 * Capacity of any Forklift vehicle
	 */
	private final static int capacity=2;
	/**
	 * Total round trip time of all vehicles that are same type
	 */
	private static double roundTripTimeTotal;
	/**
	 * Total tour of all vehicles that are same type
	 */
	private static int totalTour;
	
	/**
	 * Number of tours done with loaded
	 */
	private static int loadTour;
	
	/**
	 * Constructs a Forklift vehicle with given ID.
	 * @param ID Identity of Vehicle
	 */
	public Forklift(int ID) {
		super();
		this.ID = ID;
	}
	
	public double eventLoadTime(double r){
		return Vehicle.uniform(1, 8, r );
	}
	
	public double eventTravelToMineTime(double r){
		return Vehicle.triangular(1, 2, 2.5, r );	
	}
	public double eventTravelToDisposeTime(double r){
		return Vehicle.triangular(1, 3, 4, r );
		 
	}
	public double eventUnloadTime(double r){
		return Vehicle.uniform(2, 5, r );
	}

	/**
	 * Gets capacity of this type of Vehicle
	 * @return Capacity of this type of Vehicle
	 */
	public static int getCapacity() {
		return capacity;
	}

	/**
	 * Increments total tour of a Vehicle type by 1.
	 */
	public  void incTotalTour() {
		Forklift.totalTour++;
	}
	/**
	 * Increments total Round Trip Time of a Vehicle type by <code>roundTripTimeTotal</code>.
	 * @param roundTripTimeAdd Amount of increase in total Round Trip Time
	 */
	public  void incRoundTripTimeTotal(double roundTripTimeAdd) {
		Forklift.roundTripTimeTotal += roundTripTimeAdd;
	}
	/**
	 * Gets total tour of a Vehicle type.
	 * @return Total tour of a Vehicle type
	 */
	public  static int getTotalTour() {
		return totalTour;
	}
	/**
	 * Gets total Round Trip Time of all vehicles in the same type.
	 * @return Total Round Trip Time of same type Vehicles
	 */
	public static double getRoundTripTimeTotal() {
		return roundTripTimeTotal;
	}
	
	/**
	 * Increments loaded tour of a Vehicle type by 1.
	 */
	public  void incLoadTour() {
		Forklift.loadTour++;
	}

	/**
	 * Gets total Loaded Tour of all vehicles in the same type.
	 * @return Total Loaded Tour of same type Vehicles
	 */
	public static int getLoadTour() {
		return loadTour;
	}
	
	


}
