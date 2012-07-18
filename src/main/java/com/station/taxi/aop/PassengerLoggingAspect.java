/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.station.taxi.aop;

import com.station.taxi.IPassenger;
import com.station.taxi.events.PassengerEventListener;
import com.station.taxi.logger.PassengerLogger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 *
 * @author alex
 */
@Aspect
public class PassengerLoggingAspect {
	private PassengerLogger mLogger;
	
	public void init() {
		mLogger = new PassengerLogger();
	}
	
	@Before("execution(* com.station.taxi.IPassenger.run(..))")
    public void logStart(JoinPoint joinPoint) {
		update(joinPoint, PassengerEventListener.START);
    }

	@AfterReturning("execution(* com.station.taxi.IPassenger.onArrival(..))")
	public void logOnArrival(JoinPoint joinPoint) {
		update(joinPoint, PassengerEventListener.ARRIVED);
	}
	
	@AfterReturning("execution(* com.station.taxi.IPassenger.drive(..))")
	public void logExitQueue(JoinPoint joinPoint) {
		update(joinPoint, PassengerEventListener.EXIT_QUEUE);
	}
	
	@AfterReturning("execution(* com.station.taxi.IPassenger.enterCab(..))")
	public void logEnterCab(JoinPoint joinPoint) {
		update(joinPoint, PassengerEventListener.TRANSIT);
	}

	@AfterReturning("execution(* com.station.taxi.IPassenger.interrupt(..))")
	public void logInterrupt(JoinPoint joinPoint) {
		update(joinPoint, PassengerEventListener.INTERRUPT);
	}

	private void update(JoinPoint joinPoint, int type) {
		IPassenger p = (IPassenger)joinPoint.getTarget();				
		mLogger.update(type, p);
	}
}
