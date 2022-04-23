package com.mjh.xmlscraper;

import java.util.Objects;

/**
 * Class which stores all relevant carparking information.
 * 
 * This class acts as a storage for all carparking information received
 * from the XML Scraper. At this current level, each of the class' properties
 * correspond to an element from the XML. As only one dataset has been found,
 * there is an assumption that other XML datasets will conform to the same layout.
 * 
 * @author MaxHarrisMJH@gmail.com
 */
public class Carpark {
	/*---- Properties ----*/
	/**
	 * Property which stores the value of the id element in the XML. 
	 */
	private int id;
	
	/**
	 * Property which stores the value of the text element in the XML. 
	 */
	private String text;
	
	/**
	 * Property which stores the value of the parkingRecordVersionTime element in the XML. 
	 */
	private String parkingRecordVersionTime;
	
	/**
	 * Property which stores the value of the parkingNumberOfSpaces element in the XML. 
	 */
	private int parkingNumberOfSpaces;
	
	/**
	 * Property which stores the value of the latitude element in the XML. 
	 */
	private float latitude;
	
	/**
	 * Property which stores the value of the longitude element in the XML. 
	 */
	private float longitude;
	
	/**
	 * Property which stores the value of the parkingNumberOfOccupiedSpaces element in the XML. 
	 */
	private int parkingNumberOfOccupiedSpaces;
	
	/**
	 * Property which stores the value of the parkingSiteOpeningStatus element in the XML. 
	 */
	private String parkingSiteOpeningStatus;
	
	/*---- Constructors ----*/
	/**
	 * Constructor that initialises all of the class' default properties.
	 * In order to change these default values, it is necessary to use their
	 * respective get and set methods provided by this class.
	 */
	public Carpark() {
		this(-1, "", "", -1, 0.0F, 0.0F, -1, "");
	}
	
	/**
	 * Overloaded constructor that initialises all of the class' properties to that
	 * specified by the parameters.
	 * 
	 * @param id The value of the XML's id element.
	 * @param text The value of the XML's text element.
	 * @param parkingRecordVersionTime The value of the XML's parkingRecordVersionTime element.
	 * @param parkingNumberOfSpaces The value of the XML's parkingNumberOfSpaces element.
	 * @param latitude The value of the XML's latitude element.
	 * @param longitude The value of the XML's longitude element.
	 * @param parkingNumberOfOccupiedSpaces The value of the XML's parkingNumberOfOccupiedSpaces element.
	 * @param parkingSiteOpeningStatus The value of the XML's parkingSiteOpeningStatus element.
	 */
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
	/**
	 * Returns the value stored within the class' id property.
	 * @return Integer value of the class' id property.
	 */
	public int getId() {
		return this.id;
	}
	
	/**
	 * Sets the value stored within the class' id property
	 * @param id The integer representation of the XML's id element.
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * Returns the value stored within the class' text property.
	 * @return String value of the class' text property.
	 */
	public String getText() {
		return this.text;
	}
	
	/**
	 * Sets the value stored within the class' text property
	 * @param text The String representation of the XML's text element.
	 */
	public void setText(String text) {
		this.text = text;
	}
	
	/**
	 * Returns the value stored within the class' parkingRecordVersionTime property.
	 * @return String value of the class' parkingRecordVersionTime property.
	 */
	public String getParkingRecordVersionTime() {
		return this.parkingRecordVersionTime;
	}
	
	/**
	 * Sets the value stored within the class' parkingRecordVersionTime property
	 * @param parkingRecordVersionTime The String representation of the XML's parkingRecordVersionTime element.
	 */
	public void setParkingRecordVersionTime(String parkingRecordVersionTime) {
		this.parkingRecordVersionTime = parkingRecordVersionTime;
	}
	
	/**
	 * Returns the value stored within the class' parkingNumberOfSpaces property.
	 * @return Integer value of the class' parkingNumberOfSpaces property.
	 */
	public int getParkingNumberOfSpaces() {
		return this.parkingNumberOfSpaces;
	}
	
	/**
	 * Sets the value stored within the class' parkingNumberOfSpaces property
	 * @param parkingNumberOfSpaces The integer representation of the XML's parkingNumberOfSpaces element.
	 */
	public void setParkingNumberOfSpaces(int parkingNumberOfSpaces) {
		this.parkingNumberOfSpaces = parkingNumberOfSpaces;
	}
	
	/**
	 * Returns the value stored within the class' latitude property.
	 * @return Float value of the class' latitude property.
	 */
	public float getLatitude() {
		return this.latitude;
	}
	
	/**
	 * Sets the value stored within the class' latitude property
	 * @param latitude The float representation of the XML's latitude element.
	 */
	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}
	
	/**
	 * Returns the value stored within the class' longitude property.
	 * @return Float value of the class' longitude property.
	 */
	public float getLongitude() {
		return this.longitude;
	}
	
	/**
	 * Sets the value stored within the class' longitude property
	 * @param longitude The float representation of the XML's longitude element.
	 */
	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}
	
	/**
	 * Returns the value stored within the class' parkingNumberOfOccupiedSpaces property.
	 * @return Integer value of the class' parkingNumberOfOccupiedSpaces property.
	 */
	public int getParkingNumberOfOccupiedSpaces() {
		return this.parkingNumberOfOccupiedSpaces;
	}
	
	/**
	 * Sets the value stored within the class' parkingNumberOfOccupiedSpaces property
	 * @param parkingNumberOfOccupiedSpaces The integer representation of the XML's parkingNumberOfOccupiedSpaces element.
	 */
	public void setParkingNumberOfOccupiedSpaces(int parkingNumberOfOccupiedSpaces) {
		this.parkingNumberOfOccupiedSpaces = parkingNumberOfOccupiedSpaces;
	}
	
	/**
	 * Returns the value stored within the class' parkingSiteOpeningStatus property.
	 * @return String value of the class' parkingSiteOpeningStatus property.
	 */
	public String getParkingSiteOpeningStatus() {
		return this.parkingSiteOpeningStatus;
	}
	
	/**
	 * Sets the value stored within the class' parkingSiteOpeningStatus property
	 * @param parkingSiteOpeningStatus The String representation of the XML's parkingSiteOpeningStatus element.
	 */
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