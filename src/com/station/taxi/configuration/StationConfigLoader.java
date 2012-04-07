package com.station.taxi.configuration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.station.taxi.Cab;
import com.station.taxi.Passanger;
import com.station.taxi.Station;
import com.station.taxi.TaxiMeter;

public class StationConfigLoader {
	private String mFileName;

	public StationConfigLoader(String fileName) {
		mFileName = fileName;
	}
	
	public Station load() throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(new File(mFileName));
        doc.getDocumentElement().normalize();
        
        // parse <system> tag
        TaxiMeter meter = readTaxiMeter(doc);
        Station station = readStation(doc, meter);
        ArrayList<Cab> taxis = readTaxiCabs(doc);
        ArrayList<Passanger> passangers = readPassangers(doc);
   
        
        
		return station;
	}

	private ArrayList<Passanger> readPassangers(Document doc) {
		ArrayList<Passanger> result = new ArrayList<Passanger>();
		
        NodeList taxis = doc.getElementsByTagName("passenger");
        for(int i=0; i<taxis.getLength() ; i++){
        	 NamedNodeMap attrs = taxis.item(i).getAttributes();
             String name = attrs.getNamedItem("name").getNodeValue();
             String destination = attrs.getNamedItem("destination").getNodeValue();
        	 result.add(new Passanger(name, destination));
        }
		return result;		
	}

	private ArrayList<Cab> readTaxiCabs(Document doc) {
		ArrayList<Cab> result = new ArrayList<Cab>();

        NodeList taxis = doc.getElementsByTagName("taxi");
        for(int i=0; i<taxis.getLength() ; i++){
        	 NamedNodeMap attrs = taxis.item(i).getAttributes();
             String cabNum = attrs.getNamedItem("number").getNodeValue();
             String whileWaiting = attrs.getNamedItem("whileWaiting").getNodeValue();
        	 result.add(new Cab(Integer.valueOf(cabNum), whileWaiting));
        }
		return result;
	}

	private Station readStation(Document doc, TaxiMeter meter) {
        NodeList stations = doc.getElementsByTagName("station");
        
        NamedNodeMap attrs = stations.item(0).getAttributes();
        String stationName = attrs.getNamedItem("name").getNodeValue();
        String maxWaitingTaxis = attrs.getNamedItem("maxWaitingTaxis").getNodeValue();
        
		return new Station(stationName, Integer.valueOf(maxWaitingTaxis), meter);
	}

	/**
	 * @param doc
	 */
	private TaxiMeter readTaxiMeter(Document doc) {
		String pricePerSecond = doc.getDocumentElement().getAttribute("pricePerSecond");
        String startPrice = doc.getDocumentElement().getAttribute("startPrice");
        
        return new TaxiMeter(
       		Double.parseDouble(startPrice),
        	Double.parseDouble(pricePerSecond)
        );
	}	
	
	
}
