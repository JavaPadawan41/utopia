package com.ss.dal.factory;


import com.ss.dal.builder.mapping.table.AirportTableMap;
import com.ss.dal.factory.base.FactoryBase;
import com.ss.utopia.model.Airport;

public final class AirportFactory extends FactoryBase<Airport>
{
	
	private static volatile AirportFactory instance;

	private AirportFactory()
	{
		super(AirportTableMap.getInstance());
		// TODO Auto-generated constructor stub
	}
	
	public static AirportFactory getInstance()
	{
		if (instance == null)
			synchronized(AirportFactory.class)
			{
				if (instance == null)
					instance = new AirportFactory();
			}
		
		return instance;
	}

	@Override
	public Airport createNew()
	{
		// TODO Auto-generated method stub
		return new Airport();
	}
}
