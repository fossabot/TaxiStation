package com.station.taxi.logger;

import com.station.taxi.Passenger;
import com.station.taxi.events.PassengerEventListener;

public class PassengerLogger extends PassengerEventListener {

	public PassengerLogger(Passenger passenger) {
		LoggerWrapper.addPassengerLogger(passenger);
	}

	@Override
	public void update(int type, Passenger passenger) {
		switch (type) {
		case ARRIVED:
			String destination = passenger.getDestination();
			double price = passenger.getPaidPrice();
			logPassenger(passenger, "Arrived at " + destination + " paid " + price);
			break;
		case INTERRUPT:
			logPassenger(passenger, "Passanger interupt requested...");		
			break;
		case START:
			logPassenger(passenger, "Passanger is ready and running...");
			break;
		case EXIT_QUEUE:
			logPassenger(passenger, "Waited too long leaving line angrily");
			break;
		default:
			break;
		}

	}

	/**
	 * Write a passenger action
	 * @param p
	 * @param message
	 */
	private static void logPassenger(Passenger p,String message)
	{
		LoggerWrapper.log(String.format(PassengerFilter.PATTERN,p.getPassangerName()) + " " + message);
	}
}
