package com.ss.dal.factory;


import com.ss.dal.builder.mapping.table.UserTableMap;
import com.ss.dal.factory.base.FactoryBase;
import com.ss.utopia.model.User;

public final class UserFactory extends FactoryBase<User>
{
	
	private static volatile UserFactory instance;
	private static final String BiFunction = null;

	private UserFactory()
	{
		super(UserTableMap.getInstance());
		// TODO Auto-generated constructor stub
	}
	
	public static UserFactory getInstance()
	{
		if (instance == null)
			synchronized(UserFactory.class)
			{
				if (instance == null)
					instance = new UserFactory();
			}
		
		return instance;
	}

	@Override
	public User createNew()
	{
		User u = new User();
		u.setRole(UserRoleFactory.getInstance().createNew());
		
		return u;
	}
}
