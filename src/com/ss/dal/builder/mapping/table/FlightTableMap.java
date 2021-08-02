package com.ss.dal.builder.mapping.table;


import com.ss.dal.builder.mapping.column.DoubleColumn;
import com.ss.dal.builder.mapping.column.IntegerColumn;
import com.ss.dal.builder.mapping.column.StringColumn;
import com.ss.dal.builder.mapping.column.ZonedDateTimeColumn;
import com.ss.dal.builder.mapping.table.base.TableMap;
import com.ss.utopia.model.Flight;


public final class FlightTableMap extends TableMap<Flight>
{
	//Fields for the flight table
	private final IntegerColumn<Flight> id = new IntegerColumn<Flight>((a, i) -> a.setId(i));
	private final IntegerColumn<Flight> routeId = new IntegerColumn<Flight>((a, i) -> a.getRoute().setId(i));
	private final IntegerColumn<Flight> airplaneId = new IntegerColumn<Flight>((a, i) -> a.getPlane().setId(i));
	private final ZonedDateTimeColumn<Flight> departureTime = new ZonedDateTimeColumn<Flight>((a, i) -> a.setDepartureTime(i));
	private final IntegerColumn<Flight> reservedSeats = new IntegerColumn<Flight>((a, i) -> a.setReservedSeats(i));
	private final DoubleColumn<Flight> seatPrice = new DoubleColumn<Flight>((a, i) -> a.setSeatPrice(i));
	private final IntegerColumn<Flight> availableSeats = new IntegerColumn<Flight>((a, i) -> a.setAvailableSeats(i));
	private final StringColumn<Flight> originAirportIataId = new StringColumn<Flight>((a, i) -> a.getRoute().getOrigin().setIataId(i));
	private final StringColumn<Flight> originAirportCity = new StringColumn<Flight>((a, i) -> a.getRoute().getOrigin().setCity(i));
	private final StringColumn<Flight> destAirportIataId = new StringColumn<Flight>((a, i) -> a.getRoute().getDestination().setIataId(i));
	private final StringColumn<Flight> destAirportCity = new StringColumn<Flight>((a, i) -> a.getRoute().getDestination().setCity(i));

	
	
	private static volatile FlightTableMap instance;
	
	private FlightTableMap()
	{
		super();
		this.lookup.put("flight.id", this.id);
		this.lookup.put("flight.route_id", this.routeId);
		this.lookup.put("flight.airplane_id", this.airplaneId);
		this.lookup.put("flight.departure_time", this.departureTime);
		this.lookup.put("flight.reserved_seats", this.reservedSeats);
		this.lookup.put("flight.seat_price", this.seatPrice);
		this.lookup.put(".available_seats", this.availableSeats);
		
		this.lookup.put("airplane.id", this.airplaneId);

		
		this.lookup.put("route.id", this.routeId);
		this.lookup.put("route.origin_id", this.originAirportIataId);
		this.lookup.put("route.destination_id", this.destAirportIataId);
		
		this.lookup.put("origin.iata_id", this.originAirportIataId);
		this.lookup.put("airport.origin_city", this.originAirportCity);
		
		this.lookup.put("destination.iata_id", this.destAirportIataId);
		this.lookup.put("airport.destination_city", this.destAirportCity);
	}
	
	public static FlightTableMap getInstance()
	{
		if (instance == null)
			synchronized(FlightTableMap.class)
			{
				if (instance == null)
					instance = new FlightTableMap();
			}
		
		return instance;
	}
}
