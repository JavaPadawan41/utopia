package com.ss.utopia.service.base;

import java.util.function.Function;

import com.ss.utopia.menu.Menu;
import com.ss.utopia.view.View;

public class ConsoleMenuService extends ConsoleService
{

	protected Menu menu;
	protected String quitOption;
	
	public ConsoleMenuService(View view, Menu menu, String quitOption)
	{
		super(view);
		if (menu == null)
			throw new NullPointerException("menu");
		
		this.menu = menu;
		this.quitOption = quitOption.toUpperCase();
	}
	
	@Override
	public void start()
	{
		String input;
		boolean quitToPrevious = false;
		Integer parsed;
		Service next;
		
		this.view.updateMenu(this.menu);
		
		while (!quitToPrevious)
		{
			this.view.refresh();
			input = this.view.getUserInput();
			
			if (validate(input))
			{
				parsed = Integer.parseInt(input);
				next = lookup.get(parsed);
				next.start();
			}
			else if (input.toUpperCase().contentEquals(this.quitOption.toUpperCase()))
			{
				quitToPrevious = true;
			}
			else
			{ 
				view.displayMessage(String.format("Invalid option: %s", input));
			}
			
			
		}

	}
	
	/***
	 * parses the string to determine if it is a valid input
	 * @param validator Function that accepts strings and returns a boolean, which determines the validity of input
	 * @param input Input to validate
	 * @return boolean
	 */
	protected boolean validate(Function<String, Boolean> validator, String input)
	{
		return validator.apply(input);
	}
	
	/***
	 * parses the string to determine if it is a valid input
	 * @param input Input to validate
	 * @return boolean
	 */
	protected boolean validate(String input)
	{
		Function<String, Boolean> validator = (s) -> 
		{
			int parsed;
			boolean result;
			
			try
			{
				parsed = Integer.parseInt(s);
				result = this.lookup.containsKey(parsed);
			}
			catch (NumberFormatException e)
			{
				result = false;
			}
			
			return result;
		};
		
		return validate(validator, input);
	}
}
