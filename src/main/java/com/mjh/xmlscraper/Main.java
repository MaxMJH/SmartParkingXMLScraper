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
		// Get args for city name.
		String cityName = args[0];
		String mainTag = args[1];
		URL url = new URL(args[2]);
		String[] tags = IntStream.range(3, args.length).mapToObj(i -> args[i]).toArray(String[]::new);
		
		DatabaseWrapper databaseWrapper = new DatabaseWrapper("jdbc:mysql://192.168.0.69:3306/test_smartpark", "test", "test");
		
		// Add City to Database.
		LinkedHashMap<Integer, Object> values = new LinkedHashMap<>();
		values.put(0, cityName);
		Map<Boolean, ArrayList<ArrayList<Object>>> results = databaseWrapper.executeQuery(Queries.addCity(), values);
		
		// Add carparks.
		XMLScraper xmlScraper = new XMLScraper(url, tags);
		
		List<List<String>> xml = xmlScraper.parseXML(mainTag);
		
		ArrayList<Carpark> carparks = new ArrayList<>();
		
		for(int i = 0; i < xml.size(); i++) {
			carparks.add(new Carpark(
					Integer.valueOf(xml.get(i).get(0)), 
					xml.get(i).get(1), 
					xml.get(i).get(2), 
					Integer.valueOf(xml.get(i).get(3)), 
					Double.valueOf(xml.get(i).get(4)), 
					Double.valueOf(xml.get(i).get(5)), 
					Integer.valueOf(xml.get(i).get(6)), 
					xml.get(i).get(7)
				)
			);
		}
		
		values.put(0, cityName);
		results = databaseWrapper.executeQuery(Queries.cityExists(), values);
		
		if(results.get(true) != null) {
			int cityID = (int) results.get(true).get(0).get(0);
			
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
			return;
		}
		
		SchedulerService schedulerService = new SchedulerService();
		
		schedulerService.scheduleForFiveMinutes(new Runnable() {
			@Override
			public void run() {
				try {
					XMLScraper xmlScraper = new XMLScraper(url, tags);
					
					List<List<String>> xml = xmlScraper.parseXML(mainTag);
					
					ArrayList<Carpark> carparks = new ArrayList<>();
					
					for(int i = 0; i < xml.size(); i++) {
						carparks.add(new Carpark(
								Integer.valueOf(xml.get(i).get(0)), 
								xml.get(i).get(1), 
								xml.get(i).get(2), 
								Integer.valueOf(xml.get(i).get(3)), 
								Double.valueOf(xml.get(i).get(4)), 
								Double.valueOf(xml.get(i).get(5)), 
								Integer.valueOf(xml.get(i).get(6)), 
								xml.get(i).get(7)
							)
						);
					}
					
					// URGENT - MUST REMOVE THIS USER WHEN PROJECT IS COMPLETE.
					DatabaseWrapper databaseWrapper = new DatabaseWrapper("jdbc:mysql://192.168.0.69:3306/test_smartpark", "test", "test");
					List<Integer> carparkIDs = new ArrayList<>();
					
					LinkedHashMap<Integer, Object> values = new LinkedHashMap<>();
					values.put(0, cityName);
					Map<Boolean, ArrayList<ArrayList<Object>>> results = databaseWrapper.executeQuery(Queries.getCarparkIDs(), values);
					
					if(results.get(true) != null) {
						for(int i = 0; i < results.get(true).size(); i++) {
							carparkIDs.add((int) results.get(true).get(i).get(0));
						}
					}
					
					for(int i = 0; i < carparks.size(); i++) {
						values.put(0, carparkIDs.get(i));
						values.put(1, Calendar.getInstance().getTime().toString()); // carparks.get(i).getParkingRecordVersionTime()
						values.put(2, carparks.get(i).getParkingNumberOfOccupiedSpaces());
						values.put(3, carparks.get(i).getParkingSiteOpeningStatus().equals("open") ? true : false);
						databaseWrapper.executeQuery(Queries.addFiveMinutes(), values);
					}
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		schedulerService.scheduleForHour(new Runnable() {
			@Override
			public void run() {
				try {
					// URGENT - MUST REMOVE THIS USER WHEN PROJECT IS COMPLETE.
					DatabaseWrapper databaseWrapper = new DatabaseWrapper("jdbc:mysql://192.168.0.69:3306/test_smartpark", "test", "test");
					List<Integer> carparkIDs = new ArrayList<>();
					
					LinkedHashMap<Integer, Object> values = new LinkedHashMap<>();
					values.put(0, cityName);
					Map<Boolean, ArrayList<ArrayList<Object>>> results = databaseWrapper.executeQuery(Queries.getCarparkIDs(), values);
					
					if(results.get(true) != null) {
						for(int i = 0; i < results.get(true).size(); i++) {
							carparkIDs.add((int) results.get(true).get(i).get(0));
						}
					}
					
					for(int i = 0; i < carparkIDs.size(); i++) {
						values = new LinkedHashMap<>();
						values.put(0, cityName);
						values.put(1, carparkIDs.get(i));
						values.put(2, 12);
						
						results = databaseWrapper.executeQuery(Queries.getFiveMinutesEntries(), values);
						
						int sum = 0;
						if(results.get(true) != null) {
							for(int j = 0; j < results.get(true).size(); j++) {
								sum += (int) results.get(true).get(j).get(1);
							}
						}
						
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
		
		schedulerService.scheduleForDaily(new Runnable() {
			@Override
			public void run() {
				try {
					// URGENT - MUST REMOVE THIS USER WHEN PROJECT IS COMPLETE.
					DatabaseWrapper databaseWrapper = new DatabaseWrapper("jdbc:mysql://192.168.0.69:3306/test_smartpark", "test", "test");
					List<Integer> carparkIDs = new ArrayList<>();
					
					LinkedHashMap<Integer, Object> values = new LinkedHashMap<>();
					values.put(0, cityName);
					Map<Boolean, ArrayList<ArrayList<Object>>> results = databaseWrapper.executeQuery(Queries.getCarparkIDs(), values);
					
					if(results.get(true) != null) {
						for(int i = 0; i < results.get(true).size(); i++) {
							carparkIDs.add((int) results.get(true).get(i).get(0));
						}
					}
					
					for(int i = 0; i < carparkIDs.size(); i++) {
						values = new LinkedHashMap<>();
						values.put(0, cityName);
						values.put(1, carparkIDs.get(i));
						values.put(2, 24);
						
						results = databaseWrapper.executeQuery(Queries.getHourlyEntries(), values);
						
						float sum = 0.0F;
						if(results.get(true) != null) {
							for(int j = 0; j < results.get(true).size(); j++) {
								sum += (float) results.get(true).get(j).get(1);
							}
						}
						
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
