package com.ss.dal.factory;


import com.ss.dal.builder.mapping.table.BookingAgentTableMap;
import com.ss.dal.factory.base.FactoryBase;
import com.ss.utopia.model.BookingAgent;

public final class BookingAgentFactory extends FactoryBase<BookingAgent>
{
	
	private static volatile BookingAgentFactory instance;

	private BookingAgentFactory()
	{
		super(BookingAgentTableMap.getInstance());
		// TODO Auto-generated constructor stub
	}
	
	public static BookingAgentFactory getInstance()
	{
		if (instance == null)
			synchronized(BookingAgentFactory.class)
			{
				if (instance == null)
					instance = new BookingAgentFactory();
			}
		
		return instance;
	}

	@Override
	public BookingAgent createNew()
	{
		BookingAgent instance = new BookingAgent();
		instance.setBooking(BookingFactory.getInstance().createNew());
		instance.setAgent(UserFactory.getInstance().createNew());
		return instance;
	}
}
