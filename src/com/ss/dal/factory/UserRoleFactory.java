package com.ss.dal.factory;


import com.ss.dal.builder.mapping.table.UserRoleTableMap;
import com.ss.dal.factory.base.FactoryBase;
import com.ss.utopia.model.UserRole;

public final class UserRoleFactory extends FactoryBase<UserRole>
{
	
	private static volatile UserRoleFactory instance;

	private UserRoleFactory()
	{
		super(UserRoleTableMap.getInstance());
		// TODO Auto-generated constructor stub
	}
	
	public static UserRoleFactory getInstance()
	{
		if (instance == null)
			synchronized(UserRoleFactory.class)
			{
				if (instance == null)
					instance = new UserRoleFactory();
			}
		
		return instance;
	}

	@Override
	public UserRole createNew()
	{
		// TODO Auto-generated method stub
		return new UserRole();
	}
}
