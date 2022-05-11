package com.mjh.xmlscraper;

/**
 * Main class which stores all of the queries used for the prepared statements.
 *
 * This class acts as a storage area for all queries needed throughout the XML Scraper.
 * As all methods within the class are static, this class' methods can be used at any point
 * throughout the application.
 * 
 * @author MaxHarrisMJH@gmail.com
 */
public class Queries {
	/**
	 * Returns a string containing a query.
	 * 
	 * This query aims to see if a city's name exists within the database.
	 * 
	 * @return A query.
	 */
	public static String cityExists() {
		return "SELECT City.cityID, City.cityName FROM City WHERE City.cityName = ?;";
	}
	
	/**
	 * Returns a string containing a query.
	 * 
	 * This query aims to add a city to the database
	 * 
	 * @return A query.
	 */
	public static String addCity() {
		return "INSERT INTO City (City.cityName) VALUES (?);";
	}
	
	/**
	 * Returns a string containing a query.
	 * 
	 * This query aims to update a city's name specified by a city ID.
	 * 
	 * @return A query.
	 */
	public static String updateCity() {
		return "UPDATE City SET City.cityName = ? WHERE City.cityID = ?;";
	}
	
	/**
	 * Returns a string containing a query.
	 * 
	 * This query aims to see if a carpark exists specified by a carpark name.
	 * 
	 * @return A query.
	 */
	public static String carparkExists() {
		return "SELECT Carpark.carparkID FROM Carpark WHERE Carpark.carparkName = ?;";
	}
	
	/**
	 * Returns a string containing a query.
	 * 
	 * This query aims to add a carpark to the database.
	 * 
	 * @return A query.
	 */
	public static String addCarpark() {
		return "INSERT INTO Carpark (Carpark.carparkName, Carpark.latitude, Carpark.longitude, Carpark.totalSpaces, Carpark.cityID) VALUES (?, ?, ?, ?, ?);";
	}
	
	/**
	 * Returns a string containing a query.
	 * 
	 * This query aims to see get all carpark IDs from a specified city name.
	 * 
	 * @return A query.
	 */
	public static String getCarparkIDs() {
		return "SELECT Carpark.carparkID FROM Carpark INNER JOIN City ON Carpark.cityID = City.cityID WHERE City.cityName = ?;";
	}
	
	/**
	 * Returns a string containing a query.
	 * 
	 * This query aims to get all carparks from a specified city ID.
	 * 
	 * @return A query.
	 */
	public static String getCarparks() {
		return "SELECT Carpark.carparkName, Carpark.latitude, Carpark.longitude, Carpark.totalSpaces FROM Carpark WHERE Carpark.cityID = ?;";
	}
	
	/**
	 * Returns a string containing a query.
	 * 
	 * This query aims to add a five minutes entry to the database.
	 * 
	 * @return A query.
	 */
	public static String addFiveMinutes() {
		return "INSERT INTO FiveMinutes (FiveMinutes.carparkID, FiveMinutes.recordVersionTime, FiveMinutes.occupiedSpaces, FiveMinutes.isOpen) VALUES (?, ?, ?, ?);";
	}
	
	/**
	 * Returns a string containing a query.
	 * 
	 * This query aims to return a specified number of five minutes data for a
	 * specific city and carpark ID.
	 * 
	 * @return A query.
	 */
	public static String getFiveMinutesEntries() {
		return "SELECT FiveMinutes.carparkID, FiveMinutes.occupiedSpaces FROM FiveMinutes INNER JOIN Carpark ON FiveMinutes.carparkID = Carpark.carparkID INNER JOIN City ON Carpark.cityID = City.cityID WHERE City.cityName = ? AND Carpark.carparkID = ? ORDER BY FiveMinutes.fiveMinutesID DESC LIMIT ?;";
	}
	
	/**
	 * Returns a string containing a query.
	 * 
	 * This query aims to add an hourly entry to the database.
	 * 
	 * @return A query.
	 */
	public static String addHourly() {
		return "INSERT INTO Hourly (Hourly.carparkID, Hourly.recordVersionTime, Hourly.averageOccupiedSpaces) VALUES (?, ?, ?);";
	}
	
	/**
	 * Returns a string containing a query.
	 * 
	 * This query aims to return a specified number of hourly data for a
	 * specific city and car park ID.
	 * 
	 * @return A query.
	 */
	public static String getHourlyEntries() {
		return "SELECT Hourly.carparkID, Hourly.averageOccupiedSpaces FROM Hourly INNER JOIN Carpark ON Hourly.carparkID = Carpark.carparkID INNER JOIN City ON Carpark.cityID = City.cityID WHERE City.cityName = ? AND Carpark.carparkID = ? ORDER BY Hourly.hourlyID DESC LIMIT ?;";
	}
	
	/**
	 * Returns a string containing a query.
	 * 
	 * This query aims to add a daily entry to the database.
	 * 
	 * @return A query.
	 */
	public static String addDaily() {
		return "INSERT INTO Daily (Daily.carparkID, Daily.recordVersionTime, Daily.averageOccupiedSpaces) VALUES (?, ?, ?)";
	}
}
