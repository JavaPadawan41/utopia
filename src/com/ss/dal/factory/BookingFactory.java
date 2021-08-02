package com.ss.dal.factory;


import java.util.ArrayList;
import java.util.List;

import com.ss.dal.builder.mapping.table.BookingTableMap;
import com.ss.dal.factory.base.FactoryBase;
import com.ss.utopia.model.Booking;
import com.ss.utopia.model.Flight;

public final class BookingFactory extends FactoryBase<Booking>
{
	
	private static volatile BookingFactory instance;

	private BookingFactory()
	{
		super(BookingTableMap.getInstance());
		// TODO Auto-generated constructor stub
	}
	
	public static BookingFactory getInstance()
	{
		if (instance == null)
			synchronized(BookingFactory.class)
			{
				if (instance == null)
					instance = new BookingFactory();
			}
		
		return instance;
	}

	@Override
	public Booking createNew()
	{
		Booking b = new Booking();
		b.setBookingAgent(UserFactory.getInstance().createNew());
		b.setBookingUser(UserFactory.getInstance().createNew());
		b.setBookingGuest(BookingGuestFactory.getInstance().createNew(b));
		b.setFlights(new ArrayList<Flight>());
		
		// TODO Auto-generated method stub
		return b;
	}
}
