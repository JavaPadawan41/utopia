package com.ss.dal.factory;


import com.ss.dal.builder.mapping.table.AirplaneTypeTableMap;
import com.ss.dal.factory.base.FactoryBase;
import com.ss.utopia.model.AirplaneType;

public final class AirplaneTypeFactory extends FactoryBase<AirplaneType>
{
	
	private static volatile AirplaneTypeFactory instance;

	private AirplaneTypeFactory()
	{
		super(AirplaneTypeTableMap.getInstance());
		// TODO Auto-generated constructor stub
	}
	
	public static AirplaneTypeFactory getInstance()
	{
		if (instance == null)
			synchronized(AirplaneTypeFactory.class)
			{
				if (instance == null)
					instance = new AirplaneTypeFactory();
			}
		
		return instance;
	}

	@Override
	public AirplaneType createNew()
	{
		// TODO Auto-generated method stub
		return new AirplaneType();
	}
}
