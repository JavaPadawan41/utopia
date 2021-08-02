package com.ss.dal.factory;


import com.ss.dal.builder.mapping.table.FlightTableMap;
import com.ss.dal.factory.base.FactoryBase;
import com.ss.utopia.model.Flight;

public final class FlightFactory extends FactoryBase<Flight>
{
	
	private static volatile FlightFactory instance;

	private FlightFactory()
	{
		super(FlightTableMap.getInstance());
		// TODO Auto-generated constructor stub
	}
	
	public static FlightFactory getInstance()
	{
		if (instance == null)
			synchronized(FlightFactory.class)
			{
				if (instance == null)
					instance = new FlightFactory();
			}
		
		return instance;
	}

	@Override
	public Flight createNew()
	{
		Flight f = new Flight();
		f.setPlane(AirplaneFactory.getInstance().createNew());
		f.setRoute(RouteFactory.getInstance().createNew());
		
		return f;
	}
}
