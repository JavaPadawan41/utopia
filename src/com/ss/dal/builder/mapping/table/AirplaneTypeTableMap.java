package com.ss.dal.builder.mapping.table;


import com.ss.dal.builder.mapping.column.IntegerColumn;
import com.ss.dal.builder.mapping.table.base.TableMap;
import com.ss.utopia.model.AirplaneType;

public final class AirplaneTypeTableMap extends TableMap<AirplaneType>
{
	private final IntegerColumn<AirplaneType> setId = new IntegerColumn<AirplaneType>((a, i) -> a.setId(i));
	private final IntegerColumn<AirplaneType> setMaxCapacity = new IntegerColumn<AirplaneType>((a, i) -> a.setMaxCapacity(i));
	private static volatile AirplaneTypeTableMap instance;
	
	private AirplaneTypeTableMap()
	{
		super();
		this.lookup.put("airplane_type.id", this.setId);
		this.lookup.put("airplane_type.max_capacity", this.setMaxCapacity);
	}
	
	public static AirplaneTypeTableMap getInstance()
	{
		if (instance == null)
			synchronized(AirplaneTypeTableMap.class)
			{
				if (instance == null)
					instance = new AirplaneTypeTableMap();
			}
		
		return instance;
	}
}
