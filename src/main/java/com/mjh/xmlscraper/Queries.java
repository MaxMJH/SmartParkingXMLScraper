package com.mjh.xmlscraper;

public class Queries {
	public static String cityExists() {
		return "SELECT City.cityID, City.cityName FROM City WHERE City.cityName = ?;";
	}
	
	public static String addCity() {
		return "INSERT INTO City (City.cityName) VALUES (?);";
	}
	
	public static String updateCity() {
		return "UPDATE City SET City.cityName = ? WHERE City.cityID = ?;";
	}
	
	public static String carparkExists() {
		return "SELECT Carpark.carparkID FROM Carpark WHERE Carpark.carparkName = ?;";
	}
	
	public static String addCarpark() {
		return "INSERT INTO Carpark (Carpark.carparkName, Carpark.latitude, Carpark.longitude, Carpark.totalSpaces, Carpark.cityID) VALUES (?, ?, ?, ?, ?);";
	}
	
	public static String getCarparkIDs() {
		return "SELECT Carpark.carparkID FROM Carpark INNER JOIN City ON Carpark.cityID = City.cityID WHERE City.cityName = ?;";
	}
	
	public static String addFiveMinutes() {
		return "INSERT INTO FiveMinutes (FiveMinutes.carparkID, FiveMinutes.recordVersionTime, FiveMinutes.occupiedSpaces, FiveMinutes.isOpen) VALUES (?, ?, ?, ?);";
	}
	
	public static String getFiveMinutesEntries() {
		return "SELECT FiveMinutes.carparkID, FiveMinutes.occupiedSpaces FROM FiveMinutes INNER JOIN Carpark ON FiveMinutes.carparkID = Carpark.carparkID INNER JOIN City ON Carpark.cityID = City.cityID WHERE City.cityName = ? AND Carpark.carparkID = ? ORDER BY FiveMinutes.fiveMinutesID DESC LIMIT ?;";
	}
	
	public static String addHourly() {
		return "INSERT INTO Hourly (Hourly.carparkID, Hourly.recordVersionTime, Hourly.averageOccupiedSpaces) VALUES (?, ?, ?);";
	}
	
	public static String getHourlyEntries() {
		return "SELECT Hourly.carparkID, Hourly.averageOccupiedSpaces FROM Hourly INNER JOIN Carpark ON Hourly.carparkID = Carpark.carparkID INNER JOIN City ON Carpark.cityID = City.cityID WHERE City.cityName = ? AND Carpark.carparkID = ? ORDER BY Hourly.hourlyID DESC LIMIT ?;";
	}
	
	public static String addDaily() {
		return "INSERT INTO Daily (Daily.carparkID, Daily.recordVersionTime, Daily.averageOccupiedSpaces) VALUES (?, ?, ?)";
	}
}
