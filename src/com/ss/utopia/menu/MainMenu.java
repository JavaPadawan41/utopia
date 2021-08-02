package com.ss.utopia.menu;

import java.util.List;

public class MainMenu extends GenericMenu
{
	
	public MainMenu()
	{
		super("Welcome to the Utopia Airlines Management System.\n\nPlease select your user category from below:", 
				List.of("Employee/Agent", "Administrator", "Traveler", "Exit"));
	}
}
