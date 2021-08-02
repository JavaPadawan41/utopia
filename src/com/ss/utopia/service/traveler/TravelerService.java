package com.ss.utopia.service.traveler;

import java.util.List;

import com.ss.utopia.menu.GenericMenu;
import com.ss.utopia.menu.Menu;
import com.ss.utopia.service.base.ConsoleMenuService;
import com.ss.utopia.view.View;

public class TravelerService extends ConsoleMenuService
{

	public TravelerService(View view)
	{
		super(view, new GenericMenu("Traverl Options: ", List.of("Book a Ticket", "Cancel an Upcoming Trip", "Quit to Previous")), "3");
		// TODO Auto-generated constructor stub
	}

}
