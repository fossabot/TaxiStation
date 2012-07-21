/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.station.taxi.sockets;

import com.station.taxi.spring.StationContext;
import org.springframework.context.ApplicationContext;

/**
 *
 * @author alex
 */
public class SocketStationContext extends StationContext {
	
	public SocketStationContext(ApplicationContext applicationContext) {
		super(applicationContext);
	}
	
	public Server createServer() {
		Server server = (Server)getApplicationContext().getBean("server", this);
		return server;
	}
	
}
