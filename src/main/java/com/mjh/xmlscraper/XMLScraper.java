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

public class XMLScraper {
	/*---- Fields ----*/
	private DocumentBuilder documentBuilder;
	private URL xmlURL;
	private String[] tags;
	
	/*---- Constructor ----*/
	public XMLScraper(URL xmlURL, String[] tags) throws ParserConfigurationException {
		this.documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		this.xmlURL = xmlURL;
		this.tags = tags;
	}
	
	/*---- Methods ----*/
	// Must account for typos, throw error message if there is.
	public List<List<String>> parseXML(String tagName) throws SAXException, IOException {
		// An array which holds the value of each tag specified by tags member.
		List<List<String>> results = new ArrayList<List<String>>();
		
		if(this.xmlURL.toString().endsWith(".xml")) {
			// Add an exception which checks if the file is actually XML, rather than just the extension.
			
			// Get the entire XML document.
			Document document = this.documentBuilder.parse(this.xmlURL.openStream());
			
			// Grab re-occurring tag.
			NodeList list = document.getElementsByTagName(tagName);
			
			// Iterate through XML document.
			for(int i = 0; i < list.getLength(); i++) {
				Node node = list.item(i);
				results.add(new ArrayList<String>());
				
				if(node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;				
					
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
			results.add(new ArrayList<String>());
			results.get(0).add("The URL you entered needs to be XML");
		}
		
		return results;
	}
	
	public URL getXMLURL() {
		return this.xmlURL;
	}
	
	public void setXMLURL(URL xmlURL)  {
		this.xmlURL = xmlURL;
	}
	
	public String[] getTags() {
		return this.tags;
	}
	
	public void setTags(String[] tags) {
		this.tags = tags;
	}
}
