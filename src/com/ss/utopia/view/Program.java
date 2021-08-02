package com.ss.utopia.view;

import java.io.IOException;
import java.sql.SQLException;

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
		try
		{
			mainMenuSvc = new MainMenuService(new ConsoleView(System.out, System.in), new MainMenu(), "4");
			
		} catch (ClassNotFoundException | SQLException | IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args)
	{
		Program p = new Program();
		p.mainMenuSvc.start();
	}

}

