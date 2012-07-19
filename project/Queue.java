import java.util.ArrayList;
/**
 * 
 * Defines Vehicle Queues in the simulation
 * @author Ahmet Çelik 2009400111
 * @version 1.0
 *
 */
public class Queue {
	/**
	 * ArrayList of Queue
	 */
	private ArrayList<Vehicle> qu = new  ArrayList<Vehicle>();
	/**
	 * Gets vehicle in the front of this Queue.
	 * @return Vehicle in the front of this Queue
	 */
	public Vehicle get(){
		return qu.get(0);
	}
	/**
	 * Adds vehicle to the rear of this Queue.
	 * @param v Vehicle to be added to this Queue
	 */
	public void add(Vehicle v){
		qu.add(v);
	}
	/**
	 * Check if Queue is empty.
	 * @return true if Queue is empty
	 */
	public boolean isEmpty(){
		return qu.isEmpty();
	}
	/**
	 * Removes vehicle from in front of this queue.
	 */
	public void remove(){
		qu.remove(0);
	}
	/**
	 * Reads size of queue
	 * @return Size of this Queue
	 */
	public int size(){
		return qu.size();
	}
}