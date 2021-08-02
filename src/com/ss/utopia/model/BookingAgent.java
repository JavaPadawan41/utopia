package com.ss.utopia.model;

public class BookingAgent implements IModel
{
	private Booking booking = null;
	private User agent = null;
	
	
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
	/**
	 * @return the agent
	 */
	public User getAgent()
	{
		return agent;
	}
	/**
	 * @param agent the agent to set
	 */
	public void setAgent(User agent)
	{
		this.agent = agent;
	}
	
}
