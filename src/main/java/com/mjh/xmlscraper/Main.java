package com.mjh.xmlscraper;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class Main {
	public static void main(String[] args) throws SQLException, SAXException, IOException, ParserConfigurationException, ClassNotFoundException {
		// Get CLI arguments.
		String cityName = args[0];
		String mainTag = args[1];
		URL url = new URL(args[2]);
		String[] tags = IntStream.range(3, args.length).mapToObj(i -> args[i]).toArray(String[]::new);
		 
		// Load carparks from XML.
		XMLScraper xmlScraper = new XMLScraper(url, tags);
		List<List<String>> xml = xmlScraper.parseXML(mainTag);
		
		// Go through each carpark returned by the XML Scraper.
		ArrayList<Carpark> carparks = new ArrayList<>();
		for(int i = 0; i < xml.size(); i++) {
			// Instantiate a Carpark and populate it with data.
			carparks.add(new Carpark(
					Integer.valueOf(xml.get(i).get(0)), 
					xml.get(i).get(1), 
					xml.get(i).get(2), 
					Integer.valueOf(xml.get(i).get(3)), 
					Float.valueOf(xml.get(i).get(4)), 
					Float.valueOf(xml.get(i).get(5)), 
					Integer.valueOf(xml.get(i).get(6)), 
					xml.get(i).get(7)
				)
			);
		}
		
		// Initialise a connection to the database.
		DatabaseWrapper databaseWrapper = new DatabaseWrapper();
				
		// Create a LinkedHashMap to store values for prepared statements.
		LinkedHashMap<Integer, Object> values = new LinkedHashMap<>();
				
		// Need to check first if the city being added already exists.
		values.put(0, cityName); 
		Map<Boolean, ArrayList<ArrayList<Object>>> results = databaseWrapper.executeQuery(Queries.cityExists(), values);
				
		int cityID = -1;
				
		// If the city does not exist, add it to the database. If it does, get the city's city ID.
		if(results.get(true) == null) {
			databaseWrapper.executeQuery(Queries.addCity(), values);
		} else {
			cityID = (int) results.get(true).get(0).get(0);
		}
		
		// If the city is new, add the carparks. If not, check to see if there are new carparks available.
		if(cityID == -1) {
			values = new LinkedHashMap<>();
			values.put(0, cityName);
		
			// Get the newly created city's city ID.
			results = databaseWrapper.executeQuery(Queries.cityExists(), values);
			cityID = (int) results.get(true).get(0).get(0);
		
			// Add each carpark to the database.
			for(int i = 0; i < carparks.size(); i++) {
				values = new LinkedHashMap<>();
				values.put(0, carparks.get(i).getText());
				values.put(1, carparks.get(i).getLatitude());
				values.put(2, carparks.get(i).getLongitude());
				values.put(3, carparks.get(i).getParkingNumberOfSpaces());
				values.put(4, cityID);
				results = databaseWrapper.executeQuery(Queries.addCarpark(), values);
			}
		} else {
			// The city is not new, therefore some carparks may be newly added. Check for that.
			values = new LinkedHashMap<>();
			values.put(0, cityID);
			
			results = databaseWrapper.executeQuery(Queries.getCarparks(), values);
			
			// Go through existing carparks.
			for(int i = 0; i < results.get(true).size(); i++) {
				String carparkName = (String) results.get(true).get(i).get(0);
				float latitude = (Float) results.get(true).get(i).get(1);
				float longitude = (Float) results.get(true).get(i).get(2);
				int totalSpaces = (Integer) results.get(true).get(i).get(3);
	
				// Create a carpark instance of existing carparks within the database.
				Carpark carpark = new Carpark(-1, carparkName, null, totalSpaces, latitude, longitude, -1, null);
				
				// If the carparks pulled from the XML are not in the database, add them.
				if(!carparks.contains(carpark)) {
					values = new LinkedHashMap<>();
					values.put(0, carparks.get(i).getText());
					values.put(1, carparks.get(i).getLatitude());
					values.put(2, carparks.get(i).getLongitude());
					values.put(3, carparks.get(i).getParkingNumberOfSpaces());
					values.put(4, cityID);
					results = databaseWrapper.executeQuery(Queries.addCarpark(), values);
				}
			}
		}
		
		SchedulerService schedulerService = new SchedulerService();
	
		// Start the schedule that collects data from the XML URL every five minutes.
		schedulerService.scheduleForFiveMinutes(new Runnable() {
			@Override
			public void run() {
				try {
					// Parse the same XML URL with the same tags to get real-time data.
					XMLScraper xmlScraper = new XMLScraper(url, tags);
					List<List<String>> xml = xmlScraper.parseXML(mainTag);
					
					// Add each of the carparks parking data to an array.
					ArrayList<Carpark> carparks = new ArrayList<>();
					for(int i = 0; i < xml.size(); i++) {
						carparks.add(new Carpark(
								Integer.valueOf(xml.get(i).get(0)), 
								xml.get(i).get(1), 
								xml.get(i).get(2), 
								Integer.valueOf(xml.get(i).get(3)), 
								Float.valueOf(xml.get(i).get(4)), 
								Float.valueOf(xml.get(i).get(5)), 
								Integer.valueOf(xml.get(i).get(6)), 
								xml.get(i).get(7)
							)
						);
					}
					
					// Create a connection to the database.
					DatabaseWrapper databaseWrapper = new DatabaseWrapper();
					
					// Create a LinkedHashMap to store query values.
					LinkedHashMap<Integer, Object> values = new LinkedHashMap<>();
					values.put(0, cityName);
					
					// Execute the getCarparkIDs query and store the result.
					Map<Boolean, ArrayList<ArrayList<Object>>> results = databaseWrapper.executeQuery(Queries.getCarparkIDs(), values);
					
					List<Integer> carparkIDs = new ArrayList<>();
					
					// If anything was returned, true is returned, therefore if the query failed we would expect results.get(true) to return null.
					if(results.get(true) != null) {
						for(int i = 0; i < results.get(true).size(); i++) {
							// Get the ID of each carpark.
							carparkIDs.add((int) results.get(true).get(i).get(0));
						}
					}
					
					// Iterate through each carpark and add their data to the database.
					for(int i = 0; i < carparks.size(); i++) {
						values.put(0, carparkIDs.get(i));
						values.put(1, Calendar.getInstance().getTime().toString());
						values.put(2, carparks.get(i).getParkingNumberOfOccupiedSpaces());
						values.put(3, carparks.get(i).getParkingSiteOpeningStatus().equals("open") ? true : false);
						databaseWrapper.executeQuery(Queries.addFiveMinutes(), values);
					}
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		// Start the schedule that collects data from database every hour.
		schedulerService.scheduleForHour(new Runnable() {
			@Override
			public void run() {
				try {
					// Create a connection to the database.
					DatabaseWrapper databaseWrapper = new DatabaseWrapper();
					
					// Create a LinkedHashMap to store query values.
					LinkedHashMap<Integer, Object> values = new LinkedHashMap<>();
					values.put(0, cityName);
					
					// Execute the getCarparkIDs query and store the result.
					Map<Boolean, ArrayList<ArrayList<Object>>> results = databaseWrapper.executeQuery(Queries.getCarparkIDs(), values);
					
					List<Integer> carparkIDs = new ArrayList<>();
					
					// If anything was returned, true is returned, therefore if the query failed we would expect results.get(true) to return null.
					if(results.get(true) != null) {
						for(int i = 0; i < results.get(true).size(); i++) {
							carparkIDs.add((int) results.get(true).get(i).get(0));
						}
					}
					
					// Iterate through each carpark and get the latest 12 entries of FiveMinutes data.
					for(int i = 0; i < carparkIDs.size(); i++) {
						values = new LinkedHashMap<>();
						values.put(0, cityName);
						values.put(1, carparkIDs.get(i));
						values.put(2, 12);
						
						results = databaseWrapper.executeQuery(Queries.getFiveMinutesEntries(), values);
						
						// Calculate the hourly average of occupied spaces.
						int sum = 0;
						if(results.get(true) != null) {
							for(int j = 0; j < results.get(true).size(); j++) {
								sum += (int) results.get(true).get(j).get(1);
							}
						}
						
						// Add the newly required data to the Hourly table in the database.
						values.put(0, carparkIDs.get(i));
						values.put(1, Calendar.getInstance().getTime().toString());
						values.put(2, sum / 12);
						databaseWrapper.executeQuery(Queries.addHourly(), values);
					}
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		// Start the schedule that collects data from the database every 24 hours.
		schedulerService.scheduleForDaily(new Runnable() {
			@Override
			public void run() {
				try {
					// Create a connection to the database.
					DatabaseWrapper databaseWrapper = new DatabaseWrapper();
					
					// Create a LinkedHashMap to store query values.
					LinkedHashMap<Integer, Object> values = new LinkedHashMap<>();
					values.put(0, cityName);
					
					// Execute the getCarparkIDs query and store the result.
					Map<Boolean, ArrayList<ArrayList<Object>>> results = databaseWrapper.executeQuery(Queries.getCarparkIDs(), values);
					
					List<Integer> carparkIDs = new ArrayList<>();
					
					// If anything was returned, true is returned, therefore if the query failed we would expect results.get(true) to return null.
					if(results.get(true) != null) {
						for(int i = 0; i < results.get(true).size(); i++) {
							carparkIDs.add((int) results.get(true).get(i).get(0));
						}
					}
					
					// Iterate through each carpark and get the latest 24 entries of Hourly data.
					for(int i = 0; i < carparkIDs.size(); i++) {
						values = new LinkedHashMap<>();
						values.put(0, cityName);
						values.put(1, carparkIDs.get(i));
						values.put(2, 24);
						
						results = databaseWrapper.executeQuery(Queries.getHourlyEntries(), values);
						
						// Calculate the hourly average of occupied spaces.
						float sum = 0.0F;
						if(results.get(true) != null) {
							for(int j = 0; j < results.get(true).size(); j++) {
								sum += (float) results.get(true).get(j).get(1);
							}
						}
						
						// Add the newly required data to the Daily table in the database.
						values.put(0, carparkIDs.get(i));
						values.put(1, Calendar.getInstance().getTime().toString());
						values.put(2, sum / 24);
						databaseWrapper.executeQuery(Queries.addDaily(), values);
					}
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
