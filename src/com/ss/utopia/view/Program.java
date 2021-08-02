package com.ss.utopia.view;

import com.ss.utopia.menu.MainMenu;
import com.ss.utopia.service.EmptyService;
import com.ss.utopia.service.MainMenuService;
import com.ss.utopia.service.ServiceLookup;
import com.ss.utopia.service.base.ConsoleMenuService;

public class Program
{
	private ConsoleMenuService mainMenuSvc;
	
	public Program()
	{
		mainMenuSvc = new ConsoleMenuService(new ConsoleView(System.out, System.in), new MainMenu(), new ServiceLookup(), "4");
	}
	
	public static void main(String[] args)
	{

	}

}

