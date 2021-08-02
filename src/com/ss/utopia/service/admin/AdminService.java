package com.ss.utopia.service.admin;

import java.util.List;

import com.ss.utopia.menu.GenericMenu;
import com.ss.utopia.service.base.ConsoleMenuService;
import com.ss.utopia.view.View;

public class AdminService extends ConsoleMenuService
{

	public AdminService(View view)
	{
		super(view, new GenericMenu("Administrative Options:", 
				List.of("Edit Flights", "Edit Seats for a flight", "Edit tickets/passengers",
						"Edit Airports", "Edit Travelers", "Edit Employees", "Override Tric Cancellation", 
						"Quit to Previous")), "8");
	}
	
}
