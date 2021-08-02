package com.ss.dal.builder.mapping.table;


import com.ss.dal.builder.mapping.column.BooleanColumn;
import com.ss.dal.builder.mapping.column.IntegerColumn;
import com.ss.dal.builder.mapping.column.StringColumn;
import com.ss.dal.builder.mapping.table.base.TableMap;
import com.ss.utopia.model.BookingAgent;

public final class BookingAgentTableMap extends TableMap<BookingAgent>
{
	private final IntegerColumn<BookingAgent> bookingId = new IntegerColumn<BookingAgent>((a, i) -> a.getBooking().setId(i));
	private final BooleanColumn<BookingAgent> bookingIsActive = new BooleanColumn<BookingAgent>((a, i) -> a.getBooking().setActive(i));
	private final StringColumn<BookingAgent> bookingConfirmationCode = new StringColumn<BookingAgent>((a, i) -> a.getBooking().setConfirmationCode(i));
	
	//Fields for the user table
	private final IntegerColumn<BookingAgent> userId = new IntegerColumn<BookingAgent>((a, i) -> a.getAgent().setId(i));
	private final StringColumn<BookingAgent> userGivenName = new StringColumn<BookingAgent>((a, i) -> a.getAgent().setGivenName(i));
	private final StringColumn<BookingAgent> userFamilyName = new StringColumn<BookingAgent>((a, i) -> a.getAgent().setGivenName(i));
	private final StringColumn<BookingAgent> userUserName = new StringColumn<BookingAgent>((a, i) -> a.getAgent().setGivenName(i));
	private final StringColumn<BookingAgent> userEmail = new StringColumn<BookingAgent>((a, i) -> a.getAgent().setGivenName(i));
	private final StringColumn<BookingAgent> userPassword = new StringColumn<BookingAgent>((a, i) -> a.getAgent().setGivenName(i));
	private final StringColumn<BookingAgent> userPhone = new StringColumn<BookingAgent>((a, i) -> a.getAgent().setGivenName(i));
	
	//Fields for the role table
	private final IntegerColumn<BookingAgent> userRoleId = new IntegerColumn<BookingAgent>((a, i) -> a.getAgent().getRole().setId(i));
	private final StringColumn<BookingAgent> userRoleName = new StringColumn<BookingAgent>((a, i) -> a.getAgent().getRole().setName(i));
	
	private static volatile BookingAgentTableMap instance;
	
	private BookingAgentTableMap()
	{
		super();
		this.lookup.put("booking_agent.booking_id", this.bookingId);
		this.lookup.put("booking_agent.agent_id", this.userId);
		
		this.lookup.put("booking.id", this.bookingId);
		this.lookup.put("booking.is_active", this.bookingIsActive);
		this.lookup.put("booking.confirmation_code", this.bookingConfirmationCode);
		
		this.lookup.put("user.id", this.userId);
		this.lookup.put("user.given_name", this.userGivenName);
		this.lookup.put("user.family_name", this.userFamilyName);
		this.lookup.put("user.username", this.userUserName);
		this.lookup.put("user.email", this.userEmail);
		this.lookup.put("user.password", this.userPassword);
		this.lookup.put("user.phone", this.userPhone);
		
		this.lookup.put("user_role.id", this.userRoleId);
		this.lookup.put("user.role_id", this.userRoleId);
		this.lookup.put("user_role.name", this.userRoleName);
	}
	
	public static BookingAgentTableMap getInstance()
	{
		if (instance == null)
			synchronized(BookingAgentTableMap.class)
			{
				if (instance == null)
					instance = new BookingAgentTableMap();
			}
		
		return instance;
	}
}
