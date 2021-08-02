package com.ss.dal.builder.mapping.table;


import com.ss.dal.builder.mapping.column.IntegerColumn;
import com.ss.dal.builder.mapping.table.base.TableMap;
import com.ss.utopia.model.FlightBooking;


public final class FlightBookingTableMap extends TableMap<FlightBooking>
{
	//Fields for teh booking table
	private final IntegerColumn<FlightBooking> bookingId = new IntegerColumn<FlightBooking>((a, i) -> a.getBooking().setId(i));
	//Fields for the flight table
	private final IntegerColumn<FlightBooking> flightId = new IntegerColumn<FlightBooking>((a, i) -> a.getFlight().setId(i));

	
	
	
	private static volatile FlightBookingTableMap instance;
	
	private FlightBookingTableMap()
	{
		super();
		this.lookup.put("flight_bookings.flight_id", this.flightId);
		this.lookup.put("flight_bookings.booking_id", this.bookingId);
		
		this.lookup.put("booking.id", this.bookingId);
		this.lookup.put("flight.id", this.flightId);
		
	}
	
	public static FlightBookingTableMap getInstance()
	{
		if (instance == null)
			synchronized(FlightBookingTableMap.class)
			{
				if (instance == null)
					instance = new FlightBookingTableMap();
			}
		
		return instance;
	}
}
