package com.ss.dal.factory;


import com.ss.dal.builder.mapping.table.BookingGuestTableMap;
import com.ss.dal.factory.base.FactoryBase;
import com.ss.utopia.model.Booking;
import com.ss.utopia.model.BookingGuest;

public final class BookingGuestFactory extends FactoryBase<BookingGuest>
{
	
	private static volatile BookingGuestFactory instance;

	private BookingGuestFactory()
	{
		super(BookingGuestTableMap.getInstance());
		// TODO Auto-generated constructor stub
	}
	
	public static BookingGuestFactory getInstance()
	{
		if (instance == null)
			synchronized(BookingGuestFactory.class)
			{
				if (instance == null)
					instance = new BookingGuestFactory();
			}
		
		return instance;
	}

	@Override
	public BookingGuest createNew()
	{
		BookingGuest instance = new BookingGuest();
		instance.setBooking(BookingFactory.getInstance().createNew());
		
		return instance;
	}
	

	public BookingGuest createNew(Booking b)
	{
		BookingGuest instance = new BookingGuest();
		instance.setBooking(b);
		
		return instance;
	}
}
