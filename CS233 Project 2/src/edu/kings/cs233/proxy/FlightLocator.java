package edu.kings.cs233.proxy;

import java.awt.List;
import java.io.RandomAccessFile;

import edu.kings.cs.util.Map;
import edu.kings.cs233.hw.indexfile.DatumMock;
import edu.kings.cs233.hw.indexfile.ListMap;

/**
 * 
 * @author Kathryn Lavelle
 * @version 2016-03-09
 */
public class FlightLocator {
	/** Map of flight numbers to their corresponding FlightRecord. */
	private ListMap<Integer, FlightRecord> flightData;
	
	/** Map of departure times to a list of FlightRecords. */
	private ListMap<Long, List<FlightRecord>> departData;
	
	/** Map of destinations to a list of FlightRecords. */
	private ListMap<String, List<FlightRecord>> destData;
	
	/** File where all the records are stored. */
	private RandomAccessFile flights;
	
	/** Index files. */
	private RandomAccessFile flightNum;
	private RandomAccessFile departTime;
	private RandomAccessFile destination;

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
	 * Read and collect flight data from index files.
	 */
	private void startUp() {
		try {
			theFile = new RandomAccessFile("flights.dat", "r");
			
			/* Read index file and add flight numbers and offsets to datumMock. */
			flightData = new ListMap<Integer, FlightRecord>();
			flightNum = new RandomAccessFile("flightNum.idx", "r");
			while (flightNum.getFilePointer() < flightNum.length()) {
				flightData.add(flightNum.readInt(), new FlightRecord(flightNum.readLong(), theFile));
			}
			
			/* Read index file and add departure times and offsets to datumMock. */
			departData = new ListMap<Long, FlightRecord>();
			departTime = new RandomAccessFile("departTime.idx", "r");
			while (departTime.getFilePointer() < departTime.length()) {
				departData.add(departTime.readLong(), new FlightRecord(departTime.readLong(), theFile));
			}
			
			
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		flightNum.close();
		departTime.close();
		destination.close();
	}
	
	/**
	 * Write updated flight data to index files.
	 */
	private void shutDown() {
		
	}
}
