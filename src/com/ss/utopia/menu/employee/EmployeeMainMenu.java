package com.ss.utopia.menu.employee;

import java.util.List;

import com.ss.utopia.menu.GenericMenu;

public class EmployeeMainMenu extends GenericMenu
{

	public EmployeeMainMenu()
	{
		super("Employee Options:", List.of("See the flights you manage", "Quit to previous"));
	}
	
}
