package com.ss.dal.factory;


import com.ss.dal.builder.mapping.table.RouteTableMap;
import com.ss.dal.factory.base.FactoryBase;
import com.ss.utopia.model.Route;

public final class RouteFactory extends FactoryBase<Route>
{
	
	private static volatile RouteFactory instance;

	private RouteFactory()
	{
		super(RouteTableMap.getInstance());
		// TODO Auto-generated constructor stub
	}
	
	public static RouteFactory getInstance()
	{
		if (instance == null)
			synchronized(RouteFactory.class)
			{
				if (instance == null)
					instance = new RouteFactory();
			}
		
		return instance;
	}

	@Override
	public Route createNew()
	{
		Route r = new Route();
		r.setDestination(AirportFactory.getInstance().createNew());
		r.setOrigin(AirportFactory.getInstance().createNew());
		
		return r;
	}
}
