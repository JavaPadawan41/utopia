package com.ss.utopia.service.employee;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import com.ss.dal.factory.FlightFactory;
import com.ss.dal.factory.UserFactory;
import com.ss.utopia.dal.FlightRepository;
import com.ss.utopia.dal.UserRepository;
import com.ss.utopia.menu.GenericMenu;
import com.ss.utopia.menu.Menu;
import com.ss.utopia.model.Flight;
import com.ss.utopia.model.User;
import com.ss.utopia.service.ConnectionManager;
import com.ss.utopia.service.base.ConsoleMenuService;
import com.ss.utopia.view.View;

public class MangeFlightService extends ConsoleMenuService
{
	private FlightRepository flightRepo;
	private UserRepository userRepo;

	public MangeFlightService(View view, Menu menu, String quitOption) throws ClassNotFoundException, SQLException, IOException
	{
		super(view, new GenericMenu("List of Flights"), quitOption);
		
		this.flightRepo = new FlightRepository(ConnectionManager.getConnection(), FlightFactory.getInstance());
		this.userRepo = new UserRepository(ConnectionManager.getConnection(), UserFactory.getInstance());
	}
	
	@Override
	public void start()
	{
		boolean idValid = false;
		Integer parsed;
		String raw;
		User user;
		List<Flight> managedFlights;
		
		while (!idValid)
		{
			this.view.displayMessage("Enter your User ID");
			raw = this.view.getUserInput();
			
			//Get the User's ID number from the console and validate it against the DB
			try
			{
				parsed = Integer.parseInt(raw);
				user = userRepo.get(parsed);
				idValid = user != null;	
				
				//Retrieve the list of managed flights and use it to build the current service level
				if (idValid)
				{
					managedFlights = flightRepo.getManagedFlights(user);
				}
				
			}
			catch (NumberFormatException e)
			{
				this.view.displayMessage("Please enter an unsigned integer\n");
			}
			catch (SQLException e)
			{
				this.view.displayMessage("Unable to fetch data from the underlying store. Exiting...");
				return;
			}
			
			
		}
			
		
		super.start();
	}

}
