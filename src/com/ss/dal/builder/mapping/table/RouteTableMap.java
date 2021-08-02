package com.ss.dal.builder.mapping.table;


import com.ss.dal.builder.mapping.column.IntegerColumn;
import com.ss.dal.builder.mapping.column.StringColumn;
import com.ss.dal.builder.mapping.table.base.TableMap;
import com.ss.utopia.model.Route;

public final class RouteTableMap extends TableMap<Route>
{
	private final IntegerColumn<Route> id = new IntegerColumn<Route>((a, i) -> a.setId(i));
	
	//Fields for the airport table: Origin
	private final StringColumn<Route> originAirportIataId = new StringColumn<Route>((a, i) -> a.getOrigin().setIataId(i));
	private final StringColumn<Route> originAirportCity = new StringColumn<Route>((a, i) -> a.getOrigin().setCity(i));
	
	//Fields for the airport table: Dest
	private final StringColumn<Route> destAirportIataId = new StringColumn<Route>((a, i) -> a.getDestination().setIataId(i));
	private final StringColumn<Route> destAirportCity = new StringColumn<Route>((a, i) -> a.getDestination().setCity(i));
	
	
	private static volatile RouteTableMap instance;
	
	private RouteTableMap()
	{
		super();
		this.lookup.put("route.id", this.id);
		this.lookup.put("route.route_id", this.id);
		this.lookup.put("route.origin_id", this.originAirportIataId);
		this.lookup.put("route.destination_id", this.destAirportIataId);
		
		this.lookup.put("airport.origin_iata_id", this.originAirportIataId);
		this.lookup.put("airport.origin_city", this.originAirportCity);
		
		this.lookup.put("airport.dest_iata_id", this.destAirportIataId);
		this.lookup.put("airport.dest_city", this.destAirportCity);
	}
	
	public static RouteTableMap getInstance()
	{
		if (instance == null)
			synchronized(RouteTableMap.class)
			{
				if (instance == null)
					instance = new RouteTableMap();
			}
		
		return instance;
	}
}
