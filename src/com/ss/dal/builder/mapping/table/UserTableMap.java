package com.ss.dal.builder.mapping.table;


import com.ss.dal.builder.mapping.column.IntegerColumn;
import com.ss.dal.builder.mapping.column.StringColumn;
import com.ss.dal.builder.mapping.table.base.TableMap;
import com.ss.utopia.model.User;

public final class UserTableMap extends TableMap<User>
{
	//Fields for the user table
	private final IntegerColumn<User> userId = new IntegerColumn<User>((a, i) -> a.setId(i));
	private final StringColumn<User> userGivenName = new StringColumn<User>((a, i) -> a.setGivenName(i));
	private final StringColumn<User> userFamilyName = new StringColumn<User>((a, i) -> a.setFamilyName(i));
	private final StringColumn<User> userUserName = new StringColumn<User>((a, i) -> a.setUserName(i));
	private final StringColumn<User> userEmail = new StringColumn<User>((a, i) -> a.setEmail(i));
	private final StringColumn<User> userPassword = new StringColumn<User>((a, i) -> a.setPassword(i));
	private final StringColumn<User> userPhone = new StringColumn<User>((a, i) -> a.setPhone(i));
	
	//Fields for the role table
	private final IntegerColumn<User> userRoleId = new IntegerColumn<User>((a, i) -> a.getRole().setId(i));
	private final StringColumn<User> userRoleName = new StringColumn<User>((a, i) -> a.getRole().setName(i));
	
	
	private static volatile UserTableMap instance;
	
	private UserTableMap()
	{
		super();
		this.lookup.put("user.id", this.userId);
		this.lookup.put("user.role_id", this.userRoleId);
		this.lookup.put("user.given_name", this.userGivenName);
		this.lookup.put("user.family_name", this.userFamilyName);
		this.lookup.put("user.username", this.userUserName);
		this.lookup.put("user.email", this.userEmail);
		this.lookup.put("user.password", this.userPassword);
		this.lookup.put("user.phone", this.userPhone);
		
		this.lookup.put("user_role.id", this.userRoleId);
		this.lookup.put("user_role.name", this.userRoleName);
	}
	
	public static UserTableMap getInstance()
	{
		if (instance == null)
			synchronized(UserTableMap.class)
			{
				if (instance == null)
					instance = new UserTableMap();
			}
		
		return instance;
	}
}
