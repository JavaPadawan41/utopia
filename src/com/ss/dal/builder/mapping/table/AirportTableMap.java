package com.ss.dal.builder.mapping.table;


import com.ss.dal.builder.mapping.column.StringColumn;
import com.ss.dal.builder.mapping.table.base.TableMap;
import com.ss.utopia.model.Airport;

public final class AirportTableMap extends TableMap<Airport>
{
	private final StringColumn<Airport> iataId = new StringColumn<Airport>((a, i) -> a.setIataId(i));
	private final StringColumn<Airport> city = new StringColumn<Airport>((a, i) -> a.setCity(i));
	private static volatile AirportTableMap instance;
	
	private AirportTableMap()
	{
		super();
		this.lookup.put("airport.iata_id", this.iataId);
		this.lookup.put("airport.city", this.city);
	}
	
	public static AirportTableMap getInstance()
	{
		if (instance == null)
			synchronized(AirportTableMap.class)
			{
				if (instance == null)
					instance = new AirportTableMap();
			}
		
		return instance;
	}
}
