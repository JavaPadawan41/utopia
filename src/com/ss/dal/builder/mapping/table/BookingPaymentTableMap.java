package com.ss.dal.builder.mapping.table;


import com.ss.dal.builder.mapping.column.BooleanColumn;
import com.ss.dal.builder.mapping.column.IntegerColumn;
import com.ss.dal.builder.mapping.column.StringColumn;
import com.ss.dal.builder.mapping.table.base.TableMap;
import com.ss.utopia.model.BookingPayment;

public final class BookingPaymentTableMap extends TableMap<BookingPayment>
{
	//Fields for related Booking table
	private final IntegerColumn<BookingPayment> bookingId = new IntegerColumn<BookingPayment>((a, i) -> a.getBooking().setId(i));
	private final StringColumn<BookingPayment> stripeId = new StringColumn<BookingPayment>((a, i) -> a.setStripeId(i));
	private final BooleanColumn<BookingPayment> refunded = new BooleanColumn<BookingPayment>((a, i) -> a.setRefunded(i));
	
	private static volatile BookingPaymentTableMap instance;
	
	private BookingPaymentTableMap()
	{
		super();
		this.lookup.put("booking_payment.booking_id", this.bookingId);
		this.lookup.put("booking_payment.stripe_id", this.stripeId);
		this.lookup.put("booking_payment.refunded", this.refunded);
		
		this.lookup.put("booking.id", this.bookingId);

	}
	
	public static BookingPaymentTableMap getInstance()
	{
		if (instance == null)
			synchronized(BookingPaymentTableMap.class)
			{
				if (instance == null)
					instance = new BookingPaymentTableMap();
			}
		
		return instance;
	}
}
