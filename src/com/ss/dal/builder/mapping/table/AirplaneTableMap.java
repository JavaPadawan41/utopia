package com.ss.dal.builder.mapping.table;

import com.ss.dal.builder.mapping.column.IntegerColumn;
import com.ss.dal.builder.mapping.table.base.TableMap;
import com.ss.utopia.model.Airplane;


public final class AirplaneTableMap extends TableMap<Airplane>
{
	IntegerColumn<Airplane> id = new IntegerColumn<Airplane>((a, i) -> a.setId(i));
	IntegerColumn<Airplane> typeId = new IntegerColumn<Airplane>((a, i) -> a.getType().setId(i));
	IntegerColumn<Airplane> maxCapacity = new IntegerColumn<Airplane>((a, i) -> a.getType().setMaxCapacity(i));
	
	private static volatile AirplaneTableMap instance;
	
	private AirplaneTableMap()
	{
		super();
		this.lookup.put("airplane.id", this.id);
		this.lookup.put("airplane_type.id", typeId);
		this.lookup.put("airplane_type.max_capacity", maxCapacity);
		this.lookup.put("airplane.type_id", typeId);
	}
	
	public static AirplaneTableMap getInstance()
	{
		if (instance == null)
			synchronized(AirplaneTableMap.class)
			{
				if (instance == null)
					instance = new AirplaneTableMap();
			}
		return instance;
	}
}
