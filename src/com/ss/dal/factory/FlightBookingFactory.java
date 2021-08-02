package com.ss.dal.factory;


import com.ss.dal.builder.mapping.table.FlightBookingTableMap;
import com.ss.dal.factory.base.FactoryBase;
import com.ss.utopia.model.FlightBooking;

public final class FlightBookingFactory extends FactoryBase<FlightBooking>
{
	
	private static volatile FlightBookingFactory instance;

	private FlightBookingFactory()
	{
		super(FlightBookingTableMap.getInstance());
		// TODO Auto-generated constructor stub
	}
	
	public static FlightBookingFactory getInstance()
	{
		if (instance == null)
			synchronized(FlightBookingFactory.class)
			{
				if (instance == null)
					instance = new FlightBookingFactory();
			}
		
		return instance;
	}

	@Override
	public FlightBooking createNew()
	{
		FlightBooking fb = new FlightBooking();
		fb.setBooking(BookingFactory.getInstance().createNew());
		fb.setFlight(FlightFactory.getInstance().createNew());
		
		return fb;
	}
}
