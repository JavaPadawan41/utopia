package com.ss.utopia.model;

public class FlightBooking implements IModel
{
	private Flight flight = null;
	private Booking booking = null;
	
	/**
	 * @return the flight
	 */
	public Flight getFlight()
	{
		return flight;
	}
	/**
	 * @param flight the flight to set
	 */
	public void setFlight(Flight flight)
	{
		this.flight = flight;
	}
	/**
	 * @return the booking
	 */
	public Booking getBooking()
	{
		return booking;
	}
	/**
	 * @param booking the booking to set
	 */
	public void setBooking(Booking booking)
	{
		this.booking = booking;
	}
	
}
