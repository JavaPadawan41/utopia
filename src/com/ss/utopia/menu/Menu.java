package com.ss.utopia.menu;

import java.util.List;

public interface Menu
{
	String getTitle();
	List<Choice> getOptions();
	void add(Object o);
}
