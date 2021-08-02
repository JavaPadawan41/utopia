package com.ss.dal.factory;


import com.ss.dal.builder.mapping.table.BookingUserTableMap;
import com.ss.dal.factory.base.FactoryBase;
import com.ss.utopia.model.BookingUser;

public final class BookingUserFactory extends FactoryBase<BookingUser>
{
	
	private static volatile BookingUserFactory instance;

	private BookingUserFactory()
	{
		super(BookingUserTableMap.getInstance());
		// TODO Auto-generated constructor stub
	}
	
	public static BookingUserFactory getInstance()
	{
		if (instance == null)
			synchronized(BookingUserFactory.class)
			{
				if (instance == null)
					instance = new BookingUserFactory();
			}
		
		return instance;
	}

	@Override
	public BookingUser createNew()
	{
		BookingUser instance = new BookingUser();
		instance.setBooking(BookingFactory.getInstance().createNew());
		instance.setUser(UserFactory.getInstance().createNew());
		
		return instance;
	}
}
