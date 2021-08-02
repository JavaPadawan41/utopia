package com.ss.dal.factory;


import com.ss.dal.builder.mapping.table.AirplaneTableMap;
import com.ss.dal.factory.base.FactoryBase;
import com.ss.utopia.model.Airplane;

public final class AirplaneFactory extends FactoryBase<Airplane>
{

	private static volatile AirplaneFactory instance;
	
	private AirplaneFactory()
	{
		super(AirplaneTableMap.getInstance());
		// TODO Auto-generated constructor stub
	}
	
	public static AirplaneFactory getInstance()
	{
		if (instance == null)
			synchronized(AirplaneFactory.class)
			{
				if (instance == null)
					instance = new AirplaneFactory();
			}
		
		return instance;
	}

	@Override
	public Airplane createNew()
	{
		// TODO Auto-generated method stub
		Airplane a = new Airplane();
		a.setType(AirplaneTypeFactory.getInstance().createNew());
		return a;
	}
}
