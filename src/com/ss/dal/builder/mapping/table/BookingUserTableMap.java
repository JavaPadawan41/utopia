package com.ss.dal.builder.mapping.table;


import com.ss.dal.builder.mapping.column.IntegerColumn;
import com.ss.dal.builder.mapping.table.base.TableMap;
import com.ss.utopia.model.BookingUser;

public final class BookingUserTableMap extends TableMap<BookingUser>
{
	private final IntegerColumn<BookingUser> bookingId = new IntegerColumn<BookingUser>((a, i) -> a.getBooking().setId(i));
	private final IntegerColumn<BookingUser> userId = new IntegerColumn<BookingUser>((a, i) -> a.getUser().setId(i));

	
	private static volatile BookingUserTableMap instance;
	
	private BookingUserTableMap()
	{
		super();
		this.lookup.put("booking_user.booking_id", this.bookingId);
		this.lookup.put("booking_user.user_id", this.userId);
	}
	
	public static BookingUserTableMap getInstance()
	{
		if (instance == null)
			synchronized(BookingUserTableMap.class)
			{
				if (instance == null)
					instance = new BookingUserTableMap();
			}
		
		return instance;
	}
}
