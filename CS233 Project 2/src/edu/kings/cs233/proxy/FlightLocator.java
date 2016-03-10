package edu.kings.cs233.proxy;

import java.awt.List;

import edu.kings.cs.util.Map;

/**
 * 
 * @author Kathryn Lavelle
 * @version 2016-03-09
 */
public class FlightLocator {
	/** Map of flight numbers to their corresponding FlightRecord. */
	private ListMap<Integer, FlightRecord> flightData;
	
	/** Map of departure times to a list of FlightRecords. */
	private ListMap<Long, List> departData;
	
	/** Map of destinations to a list of FlightRecords. */
	private ListMap<String, List> destData;

	/**
	 * 
	 */
	public FlightLocator() {
		
	}
	
	/**
	 * 
	 * @param flight_number
	 * @param destination
	 * @param departure_time
	 * @param arrival_time
	 */
	private void ADD(int flight_number, String destination, int departure_time, int arrival_time) {
		
	}
	
	/**
	 * 
	 * @param flight_number
	 */
	private void CANCEL(int flight_number) {
		
	}
	
	/**
	 * 
	 * @param flight_number
	 */
	private void LIST(int flight_number) {
		
	}
	
	/**
	 * 
	 * @param departure_time
	 */
	private void WHOIS(int departure_time) {
		
	}
	
	/**
	 * 
	 * @param destination
	 */
	private void GETAWAY(String destination) {
		
	}
	
	/**
	 * 
	 * @param flight_number
	 * @param time_delay
	 */
	private void DELAY(int flight_number, int time_delay) {
		
	}
	
	/**
	 * 
	 */
	private void QUIT() {
		
	}
	
	/**
	 * Read and collect flight data from data file.
	 */
	private void startUp() {
		
	}
	
	/**
	 * Write updated flight data to data file.
	 */
	private void shutDown() {
		
	}
}
