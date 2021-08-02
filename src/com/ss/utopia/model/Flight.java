package com.ss.utopia.model;

import java.time.ZonedDateTime;

public class Flight implements IModel
{
	private Integer id = null;
	private Route route = null;
	private Airplane plane = null;
	private ZonedDateTime departureTime = null;
	private Integer reservedSeats = null;
	private Integer availableSeats = null;
	private Double seatPrice = null;
	
	/**
	 * @return the id
	 */
	public int getId()
	{
		return id;
	}
	/**
	 * @return the availableSeats
	 */
	public Integer getAvailableSeats()
	{
		return availableSeats;
	}
	/**
	 * @param availableSeats the availableSeats to set
	 */
	public void setAvailableSeats(Integer availableSeats)
	{
		this.availableSeats = availableSeats;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id)
	{
		this.id = id;
	}
	/**
	 * @return the route
	 */
	public Route getRoute()
	{
		return route;
	}
	/**
	 * @param route the route to set
	 */
	public void setRoute(Route route)
	{
		this.route = route;
	}
	/**
	 * @return the plane
	 */
	public Airplane getPlane()
	{
		return plane;
	}
	/**
	 * @param plane the plane to set
	 */
	public void setPlane(Airplane plane)
	{
		this.plane = plane;
	}
	/**
	 * @return the departureTime
	 */
	public ZonedDateTime getDepartureTime()
	{
		return departureTime;
	}
	/**
	 * @param departureTime the departureTime to set
	 */
	public void setDepartureTime(ZonedDateTime departureTime)
	{
		this.departureTime = departureTime;
	}
	/**
	 * @return the reservedSeats
	 */
	public int getReservedSeats()
	{
		return reservedSeats;
	}
	/**
	 * @param reservedSeats the reservedSeats to set
	 */
	public void setReservedSeats(int reservedSeats)
	{
		this.reservedSeats = reservedSeats;
	}
	/**
	 * @return the seatPrice
	 */
	public double getSeatPrice()
	{
		return seatPrice;
	}
	/**
	 * @param seatPrice the seatPrice to set
	 */
	public void setSeatPrice(double seatPrice)
	{
		this.seatPrice = seatPrice;
	}
	
	
}
