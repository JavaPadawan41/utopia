package com.ss.dal.builder.mapping.table;


import com.ss.dal.builder.mapping.column.IntegerColumn;
import com.ss.dal.builder.mapping.column.LocalDateColumn;
import com.ss.dal.builder.mapping.column.StringColumn;
import com.ss.dal.builder.mapping.table.base.TableMap;
import com.ss.utopia.model.Passenger;

public final class PassengerTableMap extends TableMap<Passenger>
{
	private final IntegerColumn<Passenger> id = new IntegerColumn<Passenger>((a, i) -> a.setId(i));
	private final StringColumn<Passenger> givenName = new StringColumn<Passenger>((a, i) -> a.setGivenName(i));
	private final StringColumn<Passenger> familyName = new StringColumn<Passenger>((a, i) -> a.setFamilyName(i));
	private final LocalDateColumn<Passenger> dob = new LocalDateColumn<Passenger>((a, i) -> a.setDob(i));
	private final StringColumn<Passenger> gender = new StringColumn<Passenger>((a, i) -> a.setGender(i));
	private final StringColumn<Passenger> address = new StringColumn<Passenger>((a, i) -> a.setAddress(i));
	
	//Fields for the Passenger table
	private final IntegerColumn<Passenger> bookingId = new IntegerColumn<Passenger>((a, i) -> a.getBooking().setId(i));

	
	
	
	private static volatile PassengerTableMap instance;
	
	private PassengerTableMap()
	{
		super();
		this.lookup.put("passenger.id", this.id);
		this.lookup.put("passenger.booking_id", this.bookingId);
		this.lookup.put("passenger.given_name", this.givenName);
		this.lookup.put("passenger.family_name", this.familyName);
		this.lookup.put("passenger.dob", this.dob);
		this.lookup.put("passenger.gender", this.gender);
		this.lookup.put("passenger.address", this.address);
		
		this.lookup.put("booking.id", this.bookingId);
	}
	
	public static PassengerTableMap getInstance()
	{
		if (instance == null)
			synchronized(PassengerTableMap.class)
			{
				if (instance == null)
					instance = new PassengerTableMap();
			}
		
		return instance;
	}
}
