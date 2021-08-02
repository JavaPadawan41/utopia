package com.ss.utopia.model;

public class AirplaneType implements IModel
{
	private Integer id = null;
	private Integer maxCapacity = null;
	
	/**
	 * @return the id
	 */
	public Integer getId()
	{
		return id;
	}
	/**
	 * @param i the id to set
	 */
	public void setId(Integer i)
	{
		this.id = i;
	}
	/**
	 * @return the maxCapacity
	 */
	public int getMaxCapacity()
	{
		return maxCapacity;
	}
	/**
	 * @param maxCapacity the maxCapacity to set
	 */
	public void setMaxCapacity(Integer maxCapacity)
	{
		this.maxCapacity = maxCapacity;
	}
	public static AirplaneType copy(AirplaneType other)
	{
		AirplaneType at = new AirplaneType();
		at.setId(other.getId());
		at.setMaxCapacity(other.getMaxCapacity());
		return at;
	}
}
