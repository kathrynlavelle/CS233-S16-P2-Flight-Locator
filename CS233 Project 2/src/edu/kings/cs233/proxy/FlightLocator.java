package edu.kings.cs233.proxy;

import java.awt.List;
import java.io.IOException;
import java.io.RandomAccessFile;

import edu.kings.cs.util.ArrayPositionList;
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
	private ListMap<Long, ArrayPositionList<FlightRecord>> departData;
	
	/** Map of destinations to a list of FlightRecords. */
	private ListMap<String, ArrayPositionList<FlightRecord>> destData;
	
	/** File where all the records are stored. */
	private RandomAccessFile theFile;
	
	/** Index files. */
	private RandomAccessFile flightNumFile;
	private RandomAccessFile departTimeFile;
	private RandomAccessFile destinationFile;

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
		// Map of all datumMocks to prevent duplicates.
		Map<Long, FlightRecord> allMocks = new ListMap<Long, FlightRecord>();
		try {
			// Data file containing all flight information
			theFile = new RandomAccessFile("flights.dat", "r");
			
			// Read index file and add flight numbers and offsets to datumMock. */
			flightData = new ListMap<Integer, FlightRecord>();
			flightNumFile = new RandomAccessFile("flightNum.idx", "r");
			while (flightNumFile.getFilePointer() < flightNumFile.length()) {
				int num = flightNumFile.readInt();
				long position = flightNumFile.readLong();
				FlightRecord record = new FlightRecord(position, theFile);
				flightData.add(num, record);
				allMocks.add(position, record);
			}
			
			// Read index file and add departure times and offsets to datumMock.
			departData = new ListMap<Long, ArrayPositionList<FlightRecord>>();
			departTimeFile = new RandomAccessFile("departTime.idx", "r");
			
			ArrayPositionList<FlightRecord> list;
			FlightRecord record;
			while (departTimeFile.getFilePointer() < departTimeFile.length()) {
				long time = departTimeFile.readLong();
				long position = departTimeFile.readLong();
				
				// For each existing flightRecord
				for (Long key : allMocks.getKeys()) {
					// If the flight already exists
					if (key.equals(position)) {
						// Get the proxy instance.
						record = allMocks.get(key);
					}
					// Else create a new proxy for this flight and add to map of all proxys.
					else {
						record = new FlightRecord(position, theFile);
						allMocks.add(position, record);
					}
				}
				
				if (departData.contains(time)) {
					list = departData.get(time);
					list.add(record);
				}
				else {
					list = new ArrayPositionList<>();
					list.add(record);
					departData.add(time, list);
				}
			}	
			
			// Read index file and add destinations and offsets to datumMock.
			destData = new ListMap<String, ArrayPositionList<FlightRecord>>();
			destinationFile = new RandomAccessFile("destination.idx", "r");
			
			ArrayPositionList<FlightRecord> flightlist;
			FlightRecord flightrecord;
			while (destinationFile.getFilePointer() < destinationFile.length()) {
				String destination = destinationFile.readUTF();
				long position = destinationFile.readLong();
				
				// For each existing flightRecord
				for (Long key : allMocks.getKeys()) {
					// If the flight already exists
					if (key.equals(position)) {
						// Get the proxy instance.
						flightrecord = allMocks.get(key);
					}
					// Else create a new proxy for this flight and add to map of all proxys.
					else {
						flightrecord = new FlightRecord(position, theFile);
						allMocks.add(position, flightrecord);
					}
				}
				
				if (destData.contains(destination)) {
					flightlist = destData.get(destination);
					flightlist.add(flightrecord);
				}
				else {
					flightlist = new ArrayPositionList<>();
					flightlist.add(flightrecord);
					destData.add(destination, flightlist);
				}
			}	
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		flightNumFile.close();
		departTimeFile.close();
		destinationFile.close();
	}
	
	/**
	 * Write updated flight data to index files.
	 */
	private void shutDown() {
		
	}
}
