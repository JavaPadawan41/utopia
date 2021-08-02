package com.ss.dal.builder.mapping.table;


import com.ss.dal.builder.mapping.column.IntegerColumn;
import com.ss.dal.builder.mapping.column.StringColumn;
import com.ss.dal.builder.mapping.table.base.TableMap;
import com.ss.utopia.model.UserRole;

public final class UserRoleTableMap extends TableMap<UserRole>
{
	//Fields for the user table
	private final IntegerColumn<UserRole> id = new IntegerColumn<UserRole>((a, i) -> a.setId(i));
	private final StringColumn<UserRole> name = new StringColumn<UserRole>((a, i) -> a.setName(i));

	
	
	private static volatile UserRoleTableMap instance;
	
	private UserRoleTableMap()
	{
		super();
		this.lookup.put("user_role.id", this.id);
		this.lookup.put("user_role.name", this.name);
	}
	
	public static UserRoleTableMap getInstance()
	{
		if (instance == null)
			synchronized(UserRoleTableMap.class)
			{
				if (instance == null)
					instance = new UserRoleTableMap();
			}
		
		return instance;
	}
}
