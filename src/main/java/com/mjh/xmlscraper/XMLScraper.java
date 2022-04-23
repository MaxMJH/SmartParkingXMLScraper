package com.mjh.xmlscraper;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Class which sets creates and manages the XML Scraper.
 * 
 * This class requires that the entered URL is an XML in order to 
 * function. Once the XML is entered a DocumentBuilder instance parses
 * the content of the XML URL, to which a Document is returned. This document
 * typically contains all XML data such as elements and attributes. Then,
 * specific tags (elements) are searched for, and there attributes (if available) are stored
 * into a returning List.
 * 
 * @author MaxHarrisMJH@gmail.com
 */
public class XMLScraper {
	/*---- Properties ----*/
	/**
	 * An instance of the DocumentBuilder to which the XML URL's contents can be parsed.
	 */
	private DocumentBuilder documentBuilder;
	
	/**
	 * The XML URL that needs to be parsed by the scraper.
	 */
	private URL xmlURL;
	
	/**
	 * All elements (tags) that are of interest to the XML Scraper.
	 */
	private String[] tags;
	
	/*---- Constructor ----*/
	/**
	 * Constructor that initialises all of the class' properties. In order for the 
	 * XML Scraper to operate as expected, the xmlURL parameter MUST be an XML URL.
	 * The scraper should also be given a list of elements (tags) that need to be 
	 * collected so that they can be stored into the database.
	 * 
	 * @param xmlURL The XML URL that needs to be scraped.
	 * @param tags The elements that are of interest to the XML Scraper.
	 * @throws ParserConfigurationException
	 */
	public XMLScraper(URL xmlURL, String[] tags) throws ParserConfigurationException {
		// Create an instance of the XML DocumentBuilder.
		this.documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		this.xmlURL = xmlURL;
		this.tags = tags;
	}
	
	/*---- Methods ----*/
	/**
	 * The parseXML method returns all of the requested data specified in the class'
	 * constructor as well as the parameter of this method. The tagName parameter should
	 * be a re-occurring tag which splits each carpark from one another. Once the tagName
	 * parameter is specified, the DocumentBuilder parses the XML URL and grabs specified data.
	 * Once the data is collected and the DocumentBuilder has finished reading the XML, the data is
	 * returned as a List with each index containing all data requested under the class' tags property.
	 * The returned data can then be split and inserted into their appropriate tables via the DatabaseWrapper.
	 * 
	 * @param tagName A re-occurring element (tag) which splits the carparks apart.
	 * @return A multidimensional List which contains all requested carparking data.
	 * @throws SAXException
	 * @throws IOException
	 */
	public List<List<String>> parseXML(String tagName) throws SAXException, IOException {
		// An array which holds the value of each tag specified by tags member.
		List<List<String>> results = new ArrayList<List<String>>();
		
		// Ensure that the URL entered ends in .xml.
		if(this.xmlURL.toString().endsWith(".xml")) {
			// Get the entire XML document.
			Document document = this.documentBuilder.parse(this.xmlURL.openStream());
			
			// Grab re-occurring tag.
			NodeList list = document.getElementsByTagName(tagName);
			
			// Iterate through the tagged XML document.
			for(int i = 0; i < list.getLength(); i++) {
				Node node = list.item(i);
				results.add(new ArrayList<String>());
				
				// Ensure that the node is an Element.
				if(node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;				
					
					// Iterate through the tags array to grab the required data from each element (tag).
					for(int j = 0; j < this.tags.length; j++) {
						// Some of the specified tags may have attributes, if so add them to the list.
						if(element.hasAttribute(tags[j])) {
							results.get(i).add(element.getAttribute(tags[j]).toString().trim());
						} else {
							results.get(i).add(element.getElementsByTagName(tags[j]).item(0).getTextContent().trim());
						}
					}
				}
			}
		} else {
			// Return an array indicating that the URL entered did not end in XML.
			results.add(new ArrayList<String>());
			results.get(0).add("The URL you entered needs to be XML");
		}
		
		return results;
	}
	
	/**
	 * Returns the XML URL.
	 * 
	 * @return The XML URL.
	 */
	public URL getXMLURL() {
		return this.xmlURL;
	}
	
	/**
	 * Sets the class' xmlURL property to the specified URL.
	 * 
	 * @param xmlURL An URL which MUST end in .xml.
	 */
	public void setXMLURL(URL xmlURL)  {
		this.xmlURL = xmlURL;
	}
	
	/**
	 * Returns a String array containing all tags (elements). 
	 * 
	 * @return A String array containing all tags (elements).
	 */
	public String[] getTags() {
		return this.tags;
	}
	
	/**
	 * Sets the class' tags property to the specific String array containing tags (elements). 
	 * @param tags A String array containing tags (elements).
	 */
	public void setTags(String[] tags) {
		this.tags = tags;
	}
}
