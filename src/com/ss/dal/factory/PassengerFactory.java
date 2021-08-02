package com.ss.dal.factory;


import com.ss.dal.builder.mapping.table.PassengerTableMap;
import com.ss.dal.factory.base.FactoryBase;
import com.ss.utopia.model.Passenger;

public final class PassengerFactory extends FactoryBase<Passenger>
{
	
	private static volatile PassengerFactory instance;

	private PassengerFactory()
	{
		super(PassengerTableMap.getInstance());
		// TODO Auto-generated constructor stub
	}
	
	public static PassengerFactory getInstance()
	{
		if (instance == null)
			synchronized(PassengerFactory.class)
			{
				if (instance == null)
					instance = new PassengerFactory();
			}
		
		return instance;
	}

	@Override
	public Passenger createNew()
	{
		Passenger p = new Passenger();
		p.setBooking(BookingFactory.getInstance().createNew());
		
		return p;
	}
}
