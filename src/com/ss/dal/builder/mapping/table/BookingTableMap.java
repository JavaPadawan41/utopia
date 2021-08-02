package com.ss.dal.builder.mapping.table;


import com.ss.dal.builder.mapping.column.BooleanColumn;
import com.ss.dal.builder.mapping.column.IntegerColumn;
import com.ss.dal.builder.mapping.column.StringColumn;
import com.ss.dal.builder.mapping.table.base.TableMap;
import com.ss.utopia.model.Booking;

public final class BookingTableMap extends TableMap<Booking>
{
	//Booking table fields
	private final IntegerColumn<Booking> id = new IntegerColumn<Booking>((a, i) -> a.setId(i));
	private final BooleanColumn<Booking> isActive = new BooleanColumn<Booking>((a, i) -> a.setActive(i));
	private final StringColumn<Booking> confirmationCode = new StringColumn<Booking>((a, i) -> a.setConfirmationCode(i));
	
	//Fields for the related booking_agent entity
	private final IntegerColumn<Booking> agentID = new IntegerColumn<Booking>((a, i) -> a.getBookingAgent().setId(i));

	//Fields for the related booking_user entity
	private final IntegerColumn<Booking> userID = new IntegerColumn<Booking>((a, i) -> a.getBookingUser().setId(i));
	
	//Fields for booking_guest entity
	private final StringColumn<Booking> guestContactEmail = new StringColumn<Booking>((a, i) -> a.getBookingGuest().setEmail(i));

	
	//Fields for the related
	
	
	
	private static volatile BookingTableMap instance;
	
	private BookingTableMap()
	{
		super();
		this.lookup.put("booking.id", this.id);
		this.lookup.put("booking.is_active", this.isActive);
		this.lookup.put("booking.confirmation_code", this.confirmationCode);
		this.lookup.put("booking_agent.agent_id", this.agentID);
		this.lookup.put("booking_user.user_id", this.userID);
		this.lookup.put("booking_guest.contact_email", this.guestContactEmail);
		
	}
	
	public static BookingTableMap getInstance()
	{
		if (instance == null)
			synchronized(BookingTableMap.class)
			{
				if (instance == null)
					instance = new BookingTableMap();
			}
		
		return instance;
	}
}
