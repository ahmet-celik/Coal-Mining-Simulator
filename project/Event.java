/**
 * Defines events that are in this simulation. 
 * 
 * @author Ahmet Çelik 2009400111
 * @version 1.0
 * 
 */
public class Event implements Comparable<Event>{
	/**
	 * Execution time for event
	 */
	private double startTime;
	/**
	 * Types of Event. 
	 * 
	 * <p>Valid Types:
	 * <p><code>"LS" </code>: Load Start
	 * <p><code>"LF" </code>: Load Finish
	 * <p><code>"DA" </code>: Arrival to Disposal
	 * <p><code>"US" </code>: Unload Start
	 * <p><code>"UF" </code>: Unload Finish
	 * <p><code>"MA"</code>: Arrival to Mining
	 */
	private String eventType;
	/**
	 * Vehicle that does this event
	 */
	private Vehicle vehicle;
	/**
	 * Constructs an Event with given StartTime , Type and Vehicle
	 * @param startTime Start Time of Event
	 * @param eventType Type of Event
	 * @param v Vehicle of Event
	 */
	public Event(double startTime, String eventType, Vehicle v) {
		this.startTime = startTime;
		this.eventType = eventType;
		this.vehicle = v;
	}
		
	/**
	 * Gets start time of Event
	 * @return Start time of Event
	 */
	public double getStartTime() {
		return startTime;
	}
	/**
	 * Gets type of Event
	 * @return Type of Event
	 */
	public String getEventType() {
		return eventType;
	}
	
	/**
	 * Gets Vehicle that does this event
	 * @return Vehicle that does this event
	 */
	public Vehicle getVehicle() {
		return vehicle;
	}
	
	/**
	 * Implements required method of Comparable<Event> interface.
	 * Order events according to their start times(i.e. in chronological order).
	 * @param other Other event to be compared
	 */
	public int compareTo(Event other) {
		   return (int)(Math.signum(startTime-other.startTime));
	}	
}
