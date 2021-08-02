package com.ss.utopia.service;


import java.io.IOException;
import java.sql.SQLException;

import com.ss.utopia.menu.Menu;
import com.ss.utopia.service.admin.AdminService;
import com.ss.utopia.service.base.ConsoleMenuService;
import com.ss.utopia.service.employee.EmployeeService;
import com.ss.utopia.service.traveler.TravelerService;
import com.ss.utopia.view.View;

public class MainMenuService extends ConsoleMenuService
{
	public MainMenuService(View view, Menu menu, String quitOption) throws ClassNotFoundException, SQLException, IOException
	{
		super(view, menu, quitOption);
		this.lookup.put(1, new EmployeeService(view));
		this.lookup.put(2, new AdminService(view));
		this.lookup.put(3, new TravelerService(view));
	}

}
