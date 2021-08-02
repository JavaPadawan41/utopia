package com.ss.dal.factory;


import com.ss.dal.builder.mapping.table.BookingPaymentTableMap;
import com.ss.dal.factory.base.FactoryBase;
import com.ss.utopia.model.BookingPayment;

public final class BookingPaymentFactory extends FactoryBase<BookingPayment>
{
	
	private static volatile BookingPaymentFactory instance;

	private BookingPaymentFactory()
	{
		super(BookingPaymentTableMap.getInstance());
		// TODO Auto-generated constructor stub
	}
	
	public static BookingPaymentFactory getInstance()
	{
		if (instance == null)
			synchronized(BookingPaymentFactory.class)
			{
				if (instance == null)
					instance = new BookingPaymentFactory();
			}
		
		return instance;
	}

	@Override
	public BookingPayment createNew()
	{
		BookingPayment instance = new BookingPayment();
		instance.setBooking(BookingFactory.getInstance().createNew());
		
		return instance;
	}
}
