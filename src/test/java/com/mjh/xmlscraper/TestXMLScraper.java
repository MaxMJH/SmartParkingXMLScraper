package com.mjh.xmlscraper;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.xml.sax.SAXException;

public class TestXMLScraper {
	@Test
	public void testXMLScraperInstance() throws MalformedURLException, ParserConfigurationException {
		XMLScraper xmlScraper = null;
		
		String[] tags = {"id", "value", "parkingRecordVersionTime", "parkingNumberOfSpaces", "latitude", "longitude", "parkingNumberOfOccupiedSpaces", "parkingSiteOpeningStatus"};
		
		xmlScraper = new XMLScraper(new URL("https://geoserver.nottinghamcity.gov.uk/parking/defstatus.xml"), tags);
		assertNotNull(xmlScraper);
	}
	
	@Test
	public void testParseXMLWithCorrectURL() throws ParserConfigurationException, SAXException, IOException {
		String[] tags = {"id", "value", "parkingRecordVersionTime", "parkingNumberOfSpaces", "latitude", "longitude", "parkingNumberOfOccupiedSpaces", "parkingSiteOpeningStatus"};
		XMLScraper xmlScraper = new XMLScraper(new URL("https://geoserver.nottinghamcity.gov.uk/parking/defstatus.xml"), tags);
		
		List<List<String>> results = xmlScraper.parseXML("carPark");
		assertEquals(results.size(), 22);
	}
	
	@Test
	public void testParseXMLWithIncorrectURL() throws ParserConfigurationException, SAXException, IOException {
		String[] tags = {"id", "value", "parkingRecordVersionTime", "parkingNumberOfSpaces", "latitude", "longitude", "parkingNumberOfOccupiedSpaces", "parkingSiteOpeningStatus"};
		XMLScraper xmlScraper = new XMLScraper(new URL("https://geoserver.nottinghamcity.gov.uk/parking/defstatus"), tags);
		
		List<List<String>> results = xmlScraper.parseXML("carPark");
		assertEquals(results.size(), 1);
	}
}
