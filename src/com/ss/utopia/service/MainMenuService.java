package com.ss.utopia.service;


import com.ss.utopia.menu.Menu;
import com.ss.utopia.service.base.ConsoleMenuService;
import com.ss.utopia.service.employee.EmployeeService;
import com.ss.utopia.view.View;

public class MainMenuService extends ConsoleMenuService
{
	public MainMenuService(View view, Menu menu, String quitOption)
	{
		super(view, menu, quitOption);
		this.lookup.put(1, new EmployeeService(view, "2"));
		this.lookup.put(2, () -> view.displayMessage("You chose Option 2"));
		this.lookup.put(3, () -> view.displayMessage("You chose Option 3"));
	}

}
