package com.ss.utopia.model;

import java.time.LocalDate;

public class Passenger implements IModel
{
	private Integer id = null;
	private Booking booking = null;
	private String givenName = null;
	private String familyName = null;
	private LocalDate dob = null;
	private String gender = null;
	private String address = null;
	
	/**
	 * @return the id
	 */
	public int getId()
	{
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id)
	{
		this.id = id;
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
	/**
	 * @return the givenName
	 */
	public String getGivenName()
	{
		return givenName;
	}
	/**
	 * @param givenName the givenName to set
	 */
	public void setGivenName(String givenName)
	{
		this.givenName = givenName;
	}
	/**
	 * @return the familyName
	 */
	public String getFamilyName()
	{
		return familyName;
	}
	/**
	 * @param familyName the familyName to set
	 */
	public void setFamilyName(String familyName)
	{
		this.familyName = familyName;
	}
	/**
	 * @return the dob
	 */
	public LocalDate getDob()
	{
		return dob;
	}
	/**
	 * @param dob the dob to set
	 */
	public void setDob(LocalDate dob)
	{
		this.dob = dob;
	}
	/**
	 * @return the gender
	 */
	public String getGender()
	{
		return gender;
	}
	/**
	 * @param gender the gender to set
	 */
	public void setGender(String gender)
	{
		this.gender = gender;
	}
	/**
	 * @return the address
	 */
	public String getAddress()
	{
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address)
	{
		this.address = address;
	}
	
}
