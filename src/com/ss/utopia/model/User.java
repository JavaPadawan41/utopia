package com.ss.utopia.model;

import java.util.List;

public class User implements IModel
{
	private Integer id = null;
	private UserRole role = null;
	private String givenName = null;
	private String familyName = null;
	private String userName = null;
	private String email = null;
	private String password = null;
	private String phone = null;
	private List<Booking> bookings = null;
	
	/**
	 * @return the bookings
	 */
	public List<Booking> getBookings()
	{
		return bookings;
	}
	/**
	 * @param bookings the bookings to set
	 */
	public void setBookings(List<Booking> bookings)
	{
		this.bookings = bookings;
	}
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
	 * @return the role
	 */
	public UserRole getRole()
	{
		return role;
	}
	/**
	 * @param role the role to set
	 */
	public void setRole(UserRole role)
	{
		this.role = role;
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
	 * @return the userName
	 */
	public String getUserName()
	{
		return userName;
	}
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName)
	{
		this.userName = userName;
	}
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
	 * @return the password
	 */
	public String getPassword()
	{
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password)
	{
		this.password = password;
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
	
}
