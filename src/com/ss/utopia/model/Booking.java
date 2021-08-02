package com.ss.utopia.model;

import java.util.List;

public class Booking implements IModel
{
	private Integer id = null;
	private Boolean isActive = null;
	private String confirmationCode = null;
	private User bookingAgent = null;
	private User bookingUser = null;
	private BookingGuest bookingGuest = null;
	private List<Flight> flights = null;
	
	
	/**
	 * @return the id
	 */
	public Integer getId()
	{
		return id;
	}
	/**
	 * @return the flights
	 */
	public List<Flight> getFlights()
	{
		return flights;
	}
	/**
	 * @return the bokingAgent
	 */
	public User getBookingAgent()
	{
		return bookingAgent;
	}
	/**
	 * @param bokingAgent the bokingAgent to set
	 */
	public void setBookingAgent(User bokingAgent)
	{
		this.bookingAgent = bokingAgent;
	}
	/**
	 * @return the bookingUser
	 */
	public User getBookingUser()
	{
		return bookingUser;
	}
	/**
	 * @param bookingUser the bookingUser to set
	 */
	public void setBookingUser(User bookingUser)
	{
		this.bookingUser = bookingUser;
	}
	/**
	 * @return the bookingGuest
	 */
	public BookingGuest getBookingGuest()
	{
		return bookingGuest;
	}
	/**
	 * @param bookingGuest the bookingGuest to set
	 */
	public void setBookingGuest(BookingGuest bookingGuest)
	{
		this.bookingGuest = bookingGuest;
	}
	/**
	 * @param flights the flights to set
	 */
	public void setFlights(List<Flight> flights)
	{
		this.flights = flights;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id)
	{
		this.id = id;
	}
	/**
	 * @return the isActive
	 */
	public boolean isActive()
	{
		return isActive;
	}
	/**
	 * @param isActive the isActive to set
	 */
	public void setActive(Boolean isActive)
	{
		this.isActive = isActive;
	}
	/**
	 * @return the confirationCode
	 */
	public String getConfirmationCode()
	{
		return confirmationCode;
	}
	/**
	 * @param confirationCode the confirationCode to set
	 */
	public void setConfirmationCode(String confirationCode)
	{
		this.confirmationCode = confirationCode;
	}
}
