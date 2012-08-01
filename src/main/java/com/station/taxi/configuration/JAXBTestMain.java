/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.station.taxi.configuration;

import com.station.taxi.configuration.jaxb.Config;
import com.station.taxi.configuration.jaxb.ConfigManager;

/**
 *
 * @author alex
 */
public class JAXBTestMain {
	private static final String CONFIG_XML = "configs/config1.xml";

	public static void main(String[] args) {
		
		
		ConfigManager manager = new ConfigManager();
		Config config = manager.load(CONFIG_XML);
		System.out.println("Config is: "+config.getOneSecPrice());
	}
}
