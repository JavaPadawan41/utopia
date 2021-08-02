package com.ss.utopia.model;

public class Airport implements IModel
{
	private String iataId = null;
	private String city = null;
	
	
	/**
	 * @return the iataId
	 */
	public String getIataId()
	{
		return iataId;
	}
	/**
	 * @param iataId the iataId to set
	 */
	public void setIataId(String iataId)
	{
		this.iataId = iataId;
	}
	/**
	 * @return the city
	 */
	public String getCity()
	{
		return city;
	}
	/**
	 * @param city the city to set
	 */
	public void setCity(String city)
	{
		this.city = city;
	}
}
