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
		/* Map of all datumMocks to prevent duplicates. */
		Map<Integer, FlightRecord> allMocks = new ListMap<Long, FlightRecord>();
		try {
			/* The data file containing all flight information. */
			theFile = new RandomAccessFile("flights.dat", "r");
			
			/* Read flight number index file and add flight numbers and offsets to datumMock. */
			flightData = new ListMap<Integer, FlightRecord>();
			flightNum = new RandomAccessFile("flightNum.idx", "r");
			while (flightNum.getFilePointer() < flightNum.length()) {
				int num = flightNum.readInt();
				long position = flightNum.readLong();
				FlightRecord flightRecord = new FlightRecord(position, theFile);
				flightData.add(num, flightRecord);
				allMocks.add(position, flightRecord);
			}
			
			/* Read departure time index file and add departure times and offsets to datumMock. */
			List<FlightRecord> departFlightList = new List<FlightRecord>();
			departData = new ListMap<Long, departFlightList>();
			departTimeFile = new RandomAccessFile("departTime.idx", "r");
			while (departTimeFile.getFilePointer() < departTimeFile.length()) {
				long time = departTimeFile.readLong();
				long position = departTimeFile.readLong();
				for (Long pos : allMocks.getKeys()) {
					if (pos.equals(position)) {
						departData.add(time, allMocks.get(pos));
					}
					else {
						FlightRecord flightRecord = new FlightRecord(position, theFile);
						departData.add(time, departFlightList.add(flightRecord));
						allMocks.add(position, flightRecord);
					}
				}
			}
			
			/* Read destination index file and add destinations and offsets to datumMock. */
			List<FlightRecord> destFlightList = new List<FlightRecord>();
			destData = new ListMap<String, destFlightList>();
			destinationFile = new RandomAccessFile("destination.idx", "r");
			while (destinationFile.getFilePointer() < destinationFile.length()) {
				String dest = destinationFile.readUTF();
				long position = destinationFile.readLong();
				for (long pos : allMocks.getKeys()) {
					if (pos.equals(position)) {
						destData.add(dest, allMocks.get(pos));
					}
					else {
						FlightRecord flightRecord = new FlightRecord(position, theFile);
						destData.add(dest, destFlightList.add(flightRecord));
						allMocks.add(position, flightRecord);
					}
				}
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
