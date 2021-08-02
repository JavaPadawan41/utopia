package com.ss.utopia.model;

public class Route implements IModel
{
	private Integer id = null;
	private Airport origin = null;
	private Airport destination = null;
	
	public void setId(Integer value)
	{
		this.id = value;
	}
	
	public Integer getId()
	{
		return this.id;
	}
	/**
	 * @return the origin
	 */
	public Airport getOrigin()
	{
		return origin;
	}
	/**
	 * @param origin the origin to set
	 */
	public void setOrigin(Airport origin)
	{
		this.origin = origin;
	}
	/**
	 * @return the destination
	 */
	public Airport getDestination()
	{
		return destination;
	}
	/**
	 * @param destination the destination to set
	 */
	public void setDestination(Airport destination)
	{
		this.destination = destination;
	}
	
}
