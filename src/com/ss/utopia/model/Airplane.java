package com.ss.utopia.model;

public class Airplane implements IModel
{
	private Integer id = null;
	private AirplaneType type = null;
	
	public Integer getId()
	{
		return id;
	}
	public void setId(Integer id)
	{
		this.id = id;
	}
	public AirplaneType getType()
	{
		return type;
	}
	public void setType(AirplaneType type)
	{
		this.type = type;
	}
}
