package com.ss.utopia.service.employee;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.ss.dal.factory.FlightFactory;
import com.ss.utopia.dal.FlightRepository;
import com.ss.utopia.menu.GenericMenu;
import com.ss.utopia.service.ConnectionManager;
import com.ss.utopia.service.base.ConsoleMenuService;
import com.ss.utopia.view.View;

public class EmployeeService extends ConsoleMenuService
{

	public EmployeeService(View view) throws ClassNotFoundException, SQLException, IOException
	{
		super(view, new GenericMenu("Employee Options:", List.of("See the flights you manage", "Quit to previous")), 
				"2");
		
		this.lookup.put(1, () -> this.manageFlights());
	}
	
	protected void manageFlights()
	{
		view.displayMessage("You chose to manage flights");
	}
	
}
