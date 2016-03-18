package edu.kings.cs233.proxy;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import edu.kings.cs.util.ArrayPositionList;
import edu.kings.cs.util.Map;

/**
 * GUI application that records and locates all flights leaving from one airport over the course of a day.
 * 
 * @author Kathryn Lavelle
 * @version 2016-03-18
 */
public class FlightLocator implements ActionListener {
	/** Map of flight numbers to their corresponding FlightRecord. */
	private ListMap<Integer, FlightRecord> flightData;
	
	/** Map of departure times to a list of FlightRecords. */
	private ListMap<Long, ArrayPositionList<FlightRecord>> departData;
	
	/** Map of destinations to a list of FlightRecords. */
	private ListMap<String, ArrayPositionList<FlightRecord>> destData;
	
	/** Data file where all the records are stored. */
	private RandomAccessFile theFile;
	
	/** FlightNum index file. */
	private RandomAccessFile flightNumFile;
	
	/** DepartTime index file. */
	private RandomAccessFile departTimeFile;
	
	/** Destination index file. */
	private RandomAccessFile destinationFile;
	
	/** Main window component. */
	private JFrame mainFrame;
	
	/** Button that adds a new flight record. */
	private JButton addButton;
	
	/** Button that cancels a flight with given flight number. */
	private JButton cancelButton;
	
	/** Button that finds and displays a flight with given flight number. */
	private JButton listButton;
	
	/** Button that finds and displays a flight with given departure time. */
	private JButton whoisButton;
	
	/** Button that finds and displays the next flight to a given destination. */
	private JButton getawayButton;
	
	/** Button that delays a flight with given flight number. */
	private JButton delayButton;
	
	/** Button that shutdowns program. */
	private JButton quitButton;
	
	/** Panel containing the command buttons. */
	private JPanel buttonPanel;
	
	/** Panel for inputting search criteria. */
	private JPanel inputPanel; 

	/** Text Area for displaying flight information. */
	private JTextArea textDisplay;
	
	/**
	 * Creates a new FlightLocator instance.
	 */
	public FlightLocator() {
		startUp();
		
		addButton = new JButton("ADD");
		cancelButton = new JButton("CANCEL");
		listButton = new JButton("LIST");
		whoisButton = new JButton("WHOIS");
		getawayButton = new JButton("GETAWAY");
		delayButton = new JButton("DELAY");
		quitButton = new JButton("QUIT");
		
		addButton.addActionListener(this);
		cancelButton.addActionListener(this);
		listButton.addActionListener(this);
		whoisButton.addActionListener(this);
		getawayButton.addActionListener(this);
		delayButton.addActionListener(this);
		quitButton.addActionListener(this);
		
		buttonPanel = new JPanel();
		buttonPanel.add(addButton);
		buttonPanel.add(cancelButton);
		buttonPanel.add(listButton);
		buttonPanel.add(whoisButton);
		buttonPanel.add(getawayButton);
		buttonPanel.add(delayButton);
		buttonPanel.add(quitButton);
		
		textDisplay = new JTextArea();
		
		inputPanel = new JPanel();
		
		mainFrame = new JFrame("Flight Locator Tool");
		mainFrame.add(buttonPanel, BorderLayout.NORTH);
		mainFrame.add(textDisplay, BorderLayout.CENTER);
		mainFrame.add(inputPanel, BorderLayout.SOUTH);
		mainFrame.setSize(550, 500);
		mainFrame.setVisible(true);
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
			theFile = new RandomAccessFile("flights.dat", "rw");
			
			// Read index file and add flight numbers and offsets to datumMock. */
			flightData = new ListMap<Integer, FlightRecord>();
			flightNumFile = new RandomAccessFile("flightNum.idx", "rw");
			while (flightNumFile.getFilePointer() < flightNumFile.length()) {
				int num = flightNumFile.readInt();
				long position = flightNumFile.readLong();
				FlightRecord record = new FlightRecord(position, theFile);
				flightData.add(num, record);
				allMocks.add(position, record);
			}
			
			// Read index file and add departure times and offsets to datumMock.
			departData = new ListMap<Long, ArrayPositionList<FlightRecord>>();
			departTimeFile = new RandomAccessFile("departTime.idx", "rw");
			
			ArrayPositionList<FlightRecord> list;
			FlightRecord record = null;
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
			destinationFile = new RandomAccessFile("destination.idx", "rw");
			
			ArrayPositionList<FlightRecord> flightlist;
			FlightRecord flightrecord = null;
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
			flightNumFile.close();
			departTimeFile.close();
			destinationFile.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Write updated flight data to index files.
	 */
	private void shutDown() {
		
	}
	
	/**
	 * 
	 * @param args Array of string args from command line.
	 */
	public static void main(String[] args) {
		new FlightLocator();
	}

	/**
	 * Called whenever an object triggers an event.
	 * 
	 * @param arg0 Action event that was created.
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == quitButton) {
			shutDown();
		}
		else if (arg0.getSource() == delayButton) {
			
		}
		else if (arg0.getSource() == getawayButton) {
			
		}
		else if (arg0.getSource() == whoisButton) {
			
		}
		else if (arg0.getSource() == listButton) {
			
		}
		else if (arg0.getSource() == cancelButton) {
			
		}
		else if (arg0.getSource() == addButton) {
			
		}
	}
}
