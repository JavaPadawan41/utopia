package com.ss.dal.builder.mapping.table;


import com.ss.dal.builder.mapping.column.BooleanColumn;
import com.ss.dal.builder.mapping.column.IntegerColumn;
import com.ss.dal.builder.mapping.column.StringColumn;
import com.ss.dal.builder.mapping.table.base.TableMap;
import com.ss.utopia.model.BookingGuest;

public final class BookingGuestTableMap extends TableMap<BookingGuest>
{
	//Fields for related booking table
	private final IntegerColumn<BookingGuest> bookingId = new IntegerColumn<BookingGuest>((a, i) -> a.getBooking().setId(i));
	private final BooleanColumn<BookingGuest> bookingIsActive = new BooleanColumn<BookingGuest>((a, i) -> a.getBooking().setActive(i));
	private final StringColumn<BookingGuest> bookingConfirmationCode = new StringColumn<BookingGuest>((a, i) -> a.getBooking().setConfirmationCode(i));
	
	private final StringColumn<BookingGuest> contactEmail = new StringColumn<BookingGuest>((a, i) -> a.setEmail(i));
	private final StringColumn<BookingGuest> contactPhone = new StringColumn<BookingGuest>((a, i) -> a.setPhone(i));
	
	private static volatile BookingGuestTableMap instance;
	
	private BookingGuestTableMap()
	{
		super();
		this.lookup.put("booking_guest.booking_id", this.bookingId);
		this.lookup.put("booking_guest.contact_email", this.contactEmail);
		this.lookup.put("booking_guest.contact_phone", this.contactPhone);
		
		this.lookup.put("booking.id", this.bookingId);
		this.lookup.put("booking.is_active", this.bookingIsActive);
		this.lookup.put("booking.confirmation_code", this.bookingConfirmationCode);
	}
	
	public static BookingGuestTableMap getInstance()
	{
		if (instance == null)
			synchronized(BookingGuestTableMap.class)
			{
				if (instance == null)
					instance = new BookingGuestTableMap();
			}
		
		return instance;
	}
}
