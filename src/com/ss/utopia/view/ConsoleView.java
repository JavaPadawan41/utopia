package com.ss.utopia.view;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import com.ss.utopia.menu.Choice;
import com.ss.utopia.menu.Menu;

public class ConsoleView implements View
{
	private Menu menu;
	private PrintStream out;
	private InputStream in;
	private Scanner kbd;
	
	public ConsoleView(PrintStream out, InputStream in)
	{
		this.out = out;
		kbd = new Scanner(in);
	}
	
	
	@Override
	public void updateMenu(Menu m)
	{
		this.menu = m;

	}

	@Override
	public void refresh()
	{
		this.out.println(menu.getTitle());
		this.blankLines(2);
		
		for (Choice c: this.menu.getOptions())
		{
			this.out.println(c.toString());
		}

	}

	@Override
	public void displayMessage(String msg)
	{
		this.out.println(msg);

	}

	public void blankLines(int count)
	{
		for (int i = 0; i < count; i++)
			this.out.println();

	}

	@Override
	public String getUserInput()
	{
		return kbd.nextLine();
	}

}
