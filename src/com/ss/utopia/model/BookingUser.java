package com.ss.utopia.model;

public class BookingUser implements IModel
{
	private Booking booking = null;
	private User user = null;
	
	/**
	 * @return the booking
	 */
	public Booking getBooking()
	{
		return booking;
	}
	
	public void setBooking(Booking b)
	{
		this.booking = b;
	}
	
	public User getUser()
	{
		return this.user;
	}
	
	public void setUser(User u)
	{
		this.user = u;
	}
}
