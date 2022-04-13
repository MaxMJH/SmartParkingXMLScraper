package com.mjh.xmlscraper;

import java.util.Objects;

public class Carpark {
	/*---- Fields ----*/
	private int id;
	private String text;
	private String parkingRecordVersionTime;
	private int parkingNumberOfSpaces;
	private float latitude;
	private float longitude;
	private int parkingNumberOfOccupiedSpaces;
	private String parkingSiteOpeningStatus;
	
	/*---- Constructors ----*/
	public Carpark() {
		this.id = -1;
		this.text = "";
		this.parkingRecordVersionTime = "";
		this.parkingNumberOfSpaces = -1;
		this.latitude = 0.0F;
		this.longitude = 0.0F;
		this.parkingNumberOfOccupiedSpaces = -1;
		this.parkingSiteOpeningStatus = "";
	}
	
	public Carpark(int id, String text, String parkingRecordVersionTime, int parkingNumberOfSpaces, float latitude, float longitude, int parkingNumberOfOccupiedSpaces, String parkingSiteOpeningStatus) {
		this.id = id;
		this.text = text;
		this.parkingRecordVersionTime = parkingRecordVersionTime;
		this.parkingNumberOfSpaces = parkingNumberOfSpaces;
		this.latitude = latitude;
		this.longitude = longitude;
		this.parkingNumberOfOccupiedSpaces = parkingNumberOfOccupiedSpaces;
		this.parkingSiteOpeningStatus = parkingSiteOpeningStatus;
	}

	/*---- Getters and Setters ----*/
	public int getId() {
		return this.id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getText() {
		return this.text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public String getParkingRecordVersionTime() {
		return this.parkingRecordVersionTime;
	}
	
	public void setParkingRecordVersionTime(String parkingRecordVersionTime) {
		this.parkingRecordVersionTime = parkingRecordVersionTime;
	}
	
	public int getParkingNumberOfSpaces() {
		return this.parkingNumberOfSpaces;
	}
	
	public void setParkingNumberOfSpaces(int parkingNumberOfSpaces) {
		this.parkingNumberOfSpaces = parkingNumberOfSpaces;
	}
	
	public float getLatitude() {
		return this.latitude;
	}
	
	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}
	
	public float getLongitude() {
		return this.longitude;
	}
	
	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}
	
	public int getParkingNumberOfOccupiedSpaces() {
		return this.parkingNumberOfOccupiedSpaces;
	}
	
	public void setParkingNumberOfOccupiedSpaces(int parkingNumberOfOccupiedSpaces) {
		this.parkingNumberOfOccupiedSpaces = parkingNumberOfOccupiedSpaces;
	}
	
	public String getParkingSiteOpeningStatus() {
		return this.parkingSiteOpeningStatus;
	}
	
	public void setParkingSiteOpeningStatus(String parkingSiteOpeningStatus) {
		this.parkingSiteOpeningStatus = parkingSiteOpeningStatus;
	}
	/*---- Overridden Methods ----*/
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Carpark) {
			return true;
		}
		
		if(this == obj) {
			return false;
		}
		
		Carpark other = (Carpark) obj;
		return this.id == other.id || (this.text.equals(other.text) && this.latitude == other.latitude && this.longitude == other.longitude && this.parkingNumberOfSpaces == other.parkingNumberOfSpaces);
	}
	
	@Override
	public String toString() {
		return "Carpark Name: " + this.text 
				+ "\nCarpark ID: " + this.id 
				+ "\nRecord Version Time: " + this.parkingRecordVersionTime 
				+ "\nNumber of Spaces: " + this.parkingNumberOfSpaces 
				+ "\nLatitude: " + this.latitude 
				+ "\nLongitude: " + this.longitude 
				+ "\nNumber of Occupied Spaces: " + this.parkingNumberOfOccupiedSpaces 
				+ "\nSite Opening Status: " + this.parkingSiteOpeningStatus;
	}
}