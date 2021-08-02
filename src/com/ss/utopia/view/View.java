package com.ss.utopia.view;

import com.ss.utopia.menu.Menu;

public interface View
{
	void updateMenu(Menu m);
	void refresh();
	void displayMessage(String msg);
	String getUserInput();
}
