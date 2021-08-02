package com.ss.utopia.model;

public class BookingGuest implements IModel
{
	private Booking booking = null;
	private String email = null;
	private String phone = null;
	/**
	 * @return the email
	 */
	public String getEmail()
	{
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email)
	{
		this.email = email;
	}
	/**
	 * @return the phone
	 */
	public String getPhone()
	{
		return phone;
	}
	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	/**
	 * @return the bookingId
	 */
	public Booking getBooking()
	{
		return booking;
	}
	/**
	 * @param bookingId the bookingId to set
	 */
	public void setBooking(Booking booking)
	{
		this.booking = booking;
	}
}
