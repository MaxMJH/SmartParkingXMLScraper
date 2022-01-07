package com.mjh.xmlscraper;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;

public class TestDatabaseWrapper {
	@Test
	public void testDatabaseWrapperInstance() {
		DatabaseWrapper database = null;
		
		database = new DatabaseWrapper("jdbc:mysql://192.168.0.69:3306/test_smartpark", "test", "test");
		assertNotNull(database);
	}

	@Test
	public void testCheckConnection() {
		DatabaseWrapper database = new DatabaseWrapper("jdbc:mysql://192.168.0.69:3306/test_smartpark", "test", "test");
		
		assertTrue(database.canConnect());
	}
	
	@Test
	public void testCheckConnectionOnFalseURL() {
		DatabaseWrapper database = new DatabaseWrapper("jdbc:mysql://192.168.0.54:3306/test_smartpark", "test", "test");
		
		assertFalse(database.canConnect());
	}
	
	@Test
	public void testInsertQueryWithNoValues() {
		DatabaseWrapper database = new DatabaseWrapper("jdbc:mysql://192.168.0.69:3306/test_smartpark", "test", "test");
		
		LinkedHashMap<Integer, Object> values = new LinkedHashMap<>();
		Map<Boolean, ArrayList<ArrayList<Object>>> results = database.executeQuery(Queries.addCity(), values);
		
		assertTrue(results.containsKey(false));
	}
	
	@Test
	public void testInsertQueryWithValues() {
		DatabaseWrapper database = new DatabaseWrapper("jdbc:mysql://192.168.0.69:3306/test_smartpark", "test", "test");
		
		LinkedHashMap<Integer, Object> values = new LinkedHashMap<>();
		values.put(0, "Leicester");
		Map<Boolean, ArrayList<ArrayList<Object>>> results = database.executeQuery("INSERT INTO City (City.cityName) VALUES (?);", values);
		
		assertTrue(results.containsKey(true));
	}
	
	@Test
	public void testSelectQueryWithNoValues() {
		DatabaseWrapper database = new DatabaseWrapper("jdbc:mysql://192.168.0.69:3306/test_smartpark", "test", "test");
		
		LinkedHashMap<Integer, Object> values = new LinkedHashMap<>();
		Map<Boolean, ArrayList<ArrayList<Object>>> results = database.executeQuery("SELECT City.cityName FROM City WHERE City.cityName = (?);", values);
		
		assertTrue(results.containsKey(false));
	}
	
	@Test
	public void testSelectQueryWithValues() {
		DatabaseWrapper database = new DatabaseWrapper("jdbc:mysql://192.168.0.69:3306/test_smartpark", "test", "test");
		
		LinkedHashMap<Integer, Object> values = new LinkedHashMap<>();
		values.put(0, "Nottingham");
		Map<Boolean, ArrayList<ArrayList<Object>>> results = database.executeQuery("SELECT City.cityName FROM City WHERE City.cityName = (?);", values);
		
		assertTrue(results.containsKey(true));
	}
	
	@Test
	public void testSelectQueryWithValuesThatDoNotExist() {
		DatabaseWrapper database = new DatabaseWrapper("jdbc:mysql://192.168.0.69:3306/test_smartpark", "test", "test");
		
		LinkedHashMap<Integer, Object> values = new LinkedHashMap<>();
		values.put(0, "Bristol");
		Map<Boolean, ArrayList<ArrayList<Object>>> results = database.executeQuery("SELECT City.cityName FROM City WHERE City.cityName = (?);", values);
		
		assertTrue(results.containsKey(false));
	}
	
	@Test
	public void testUpdateQueryWithNoValues() {
		DatabaseWrapper database = new DatabaseWrapper("jdbc:mysql://192.168.0.69:3306/test_smartpark", "test", "test");
		
		LinkedHashMap<Integer, Object> values = new LinkedHashMap<>();
		Map<Boolean, ArrayList<ArrayList<Object>>> results = database.executeQuery("UPDATE City SET City.cityName = \"Derby\" WHERE City.cityName = (?);", values);
		
		assertTrue(results.containsKey(false));
	}
	
	@Test
	public void testUpdateQueryWithValues() {
		DatabaseWrapper database = new DatabaseWrapper("jdbc:mysql://192.168.0.69:3306/test_smartpark", "test", "test");
		
		LinkedHashMap<Integer, Object> values = new LinkedHashMap<>();
		values.put(0, "Leicester");
		Map<Boolean, ArrayList<ArrayList<Object>>> results = database.executeQuery("UPDATE City SET City.cityName = \"Derby\" WHERE City.cityName = (?);", values);
		
		assertTrue(results.containsKey(true));
	}
	
	@Test
	public void testUpdateQueryWithValuesThatDoNotExist() {
		DatabaseWrapper database = new DatabaseWrapper("jdbc:mysql://192.168.0.69:3306/test_smartpark", "test", "test");
		
		LinkedHashMap<Integer, Object> values = new LinkedHashMap<>();
		values.put(0, "Bristol");
		Map<Boolean, ArrayList<ArrayList<Object>>> results = database.executeQuery("UPDATE City SET City.cityName = \\\"Derby\\\" WHERE City.cityName = (?);", values);
		
		assertTrue(results.containsKey(false));
	}
	
	@Test
	public void testDeleteQueryWithNoValues() {
		DatabaseWrapper database = new DatabaseWrapper("jdbc:mysql://192.168.0.69:3306/test_smartpark", "test", "test");
		
		LinkedHashMap<Integer, Object> values = new LinkedHashMap<>();
		Map<Boolean, ArrayList<ArrayList<Object>>> results = database.executeQuery("DELETE FROM City WHERE City.cityName = (?);", values);
		
		assertTrue(results.containsKey(false));
	}
	
	@Test
	public void testDeleteQueryWithValues() {
		DatabaseWrapper database = new DatabaseWrapper("jdbc:mysql://192.168.0.69:3306/test_smartpark", "test", "test");
		
		LinkedHashMap<Integer, Object> values = new LinkedHashMap<>();
		values.put(0, "Derby");
		Map<Boolean, ArrayList<ArrayList<Object>>> results = database.executeQuery("DELETE FROM City WHERE City.cityName = (?);", values);
		
		assertTrue(results.containsKey(true));
	}
	
	@Test
	public void testDeleteQueryWithValuesThatDoNotExist() {
		DatabaseWrapper database = new DatabaseWrapper("jdbc:mysql://192.168.0.69:3306/test_smartpark", "test", "test");
		
		LinkedHashMap<Integer, Object> values = new LinkedHashMap<>();
		values.put(0, "Scotland");
		Map<Boolean, ArrayList<ArrayList<Object>>> results = database.executeQuery("DELETE FROM City WHERE City.cityName = (?);", values);
		
		assertTrue(results.containsKey(false));
	}
	
	@Test
	public void testCityExists() {
		DatabaseWrapper database = new DatabaseWrapper("jdbc:mysql://192.168.0.69:3306/test_smartpark", "test", "test");
		
		LinkedHashMap<Integer, Object> values = new LinkedHashMap<>();
		values.put(0, "Nottingham");
		Map<Boolean, ArrayList<ArrayList<Object>>> results = database.executeQuery(Queries.cityExists(), values);
				
		assertTrue(results.containsKey(true));
	}
	
	@Test
	public void testAddCity() {
		DatabaseWrapper database = new DatabaseWrapper("jdbc:mysql://192.168.0.69:3306/test_smartpark", "test", "test");
		
		LinkedHashMap<Integer, Object> values = new LinkedHashMap<>();
		values.put(0, "London");
		Map<Boolean, ArrayList<ArrayList<Object>>> results = database.executeQuery(Queries.addCity(), values);
				
		assertTrue(results.containsKey(true));
	}
	
	@Test
	public void testCarparkExists() {
		DatabaseWrapper database = new DatabaseWrapper("jdbc:mysql://192.168.0.69:3306/test_smartpark", "test", "test");
		
		LinkedHashMap<Integer, Object> values = new LinkedHashMap<>();
		values.put(0, "Test Carpark");
		Map<Boolean, ArrayList<ArrayList<Object>>> results = database.executeQuery(Queries.carparkExists(), values);
				
		assertTrue(results.containsKey(true));
	}
	
	@Test
	public void testAddCarpark() {
		DatabaseWrapper database = new DatabaseWrapper("jdbc:mysql://192.168.0.69:3306/test_smartpark", "test", "test");
		
		LinkedHashMap<Integer, Object> values = new LinkedHashMap<>();
		values.put(0, "New Carpark");
		values.put(1, 1.00);
		values.put(2, -0.15);
		values.put(3, 500);
		values.put(4, 1);
		Map<Boolean, ArrayList<ArrayList<Object>>> results = database.executeQuery(Queries.addCarpark(), values);
				
		assertTrue(results.containsKey(true));
	}
	
	@Test
	public void testGetCarparkIDs() {
		DatabaseWrapper database = new DatabaseWrapper("jdbc:mysql://192.168.0.69:3306/test_smartpark", "test", "test");
		
		LinkedHashMap<Integer, Object> values = new LinkedHashMap<>();
		values.put(0, "Nottingham");
		Map<Boolean, ArrayList<ArrayList<Object>>> results = database.executeQuery(Queries.getCarparkIDs(), values);
				
		assertTrue(results.containsKey(true));
	}
	
	@Test
	public void testAddFiveMinutes() {
		DatabaseWrapper database = new DatabaseWrapper("jdbc:mysql://192.168.0.69:3306/test_smartpark", "test", "test");
		
		LinkedHashMap<Integer, Object> values = new LinkedHashMap<>();
		values.put(0, 1);
		values.put(1, Calendar.getInstance().getTime().toString());
		values.put(2, 30);
		values.put(3, 1);
		Map<Boolean, ArrayList<ArrayList<Object>>> results = database.executeQuery(Queries.addFiveMinutes(), values);
				
		assertTrue(results.containsKey(true));
	}
	
	@Test
	public void testGetFiveMinutesEntries() {
		DatabaseWrapper database = new DatabaseWrapper("jdbc:mysql://192.168.0.69:3306/test_smartpark", "test", "test");
		
		LinkedHashMap<Integer, Object> values = new LinkedHashMap<>();
		values.put(0, "Nottingham");
		values.put(1, 1);
		values.put(2, 1);
		Map<Boolean, ArrayList<ArrayList<Object>>> results = database.executeQuery(Queries.getFiveMinutesEntries(), values);
				
		assertTrue(results.containsKey(true));
	}
	
	@Test
	public void testAddHourly() {
		DatabaseWrapper database = new DatabaseWrapper("jdbc:mysql://192.168.0.69:3306/test_smartpark", "test", "test");
		
		LinkedHashMap<Integer, Object> values = new LinkedHashMap<>();
		values.put(0, 1);
		values.put(1, Calendar.getInstance().getTime().toString());
		values.put(2, 30);
		Map<Boolean, ArrayList<ArrayList<Object>>> results = database.executeQuery(Queries.addHourly(), values);
				
		assertTrue(results.containsKey(true));
	}
	
	@Test
	public void testGetHourlyEntries() {
		DatabaseWrapper database = new DatabaseWrapper("jdbc:mysql://192.168.0.69:3306/test_smartpark", "test", "test");
		
		LinkedHashMap<Integer, Object> values = new LinkedHashMap<>();
		values.put(0, "Nottingham");
		values.put(1, 1);
		values.put(2, 1);
		Map<Boolean, ArrayList<ArrayList<Object>>> results = database.executeQuery(Queries.getHourlyEntries(), values);
				
		assertTrue(results.containsKey(true));
	}
	
	@Test
	public void testAddDaily() {
		DatabaseWrapper database = new DatabaseWrapper("jdbc:mysql://192.168.0.69:3306/test_smartpark", "test", "test");
		
		LinkedHashMap<Integer, Object> values = new LinkedHashMap<>();
		values.put(0, 1);
		values.put(1, Calendar.getInstance().getTime().toString());
		values.put(2, 2);
		Map<Boolean, ArrayList<ArrayList<Object>>> results = database.executeQuery(Queries.addDaily(), values);
				
		assertTrue(results.containsKey(true));
	}
}
