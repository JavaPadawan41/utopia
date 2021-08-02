package com.ss.utopia.model;

public class BookingPayment implements IModel
{
	private Booking booking = null;
	private String stripeId = null;
	private Boolean refunded = null;
	
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
	 * @return the stripeId
	 */
	public String getStripeId()
	{
		return stripeId;
	}
	/**
	 * @param stripeId the stripeId to set
	 */
	public void setStripeId(String stripeId)
	{
		this.stripeId = stripeId;
	}
	/**
	 * @return the refunded
	 */
	public boolean isRefunded()
	{
		return refunded;
	}
	/**
	 * @param refunded the refunded to set
	 */
	public void setRefunded(Boolean refunded)
	{
		this.refunded = refunded;
	}
	
}
