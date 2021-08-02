package com.ss.utopia.service.admin;

import com.ss.utopia.menu.administrator.AdminMainMenu;
import com.ss.utopia.menu.employee.EmployeeMainMenu;
import com.ss.utopia.service.base.ConsoleMenuService;
import com.ss.utopia.view.View;

public class AdminService extends ConsoleMenuService
{

	public AdminService(View view, String quitOption)
	{
		super(view, new AdminMainMenu(), quitOption);
	}
	
}
