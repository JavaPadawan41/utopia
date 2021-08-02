package com.ss.utopia.menu;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GenericMenu implements Menu
{
	protected String title;
	protected List<Choice> options;
	
	public GenericMenu(String title, List<Object> options)
	{
		if (title == null)
			throw new NullPointerException("title");
		if (options == null)
			throw new NullPointerException("options");
		
		this.title = title;
		this.options = IntStream.rangeClosed(1, options.size()).boxed().map(i -> new Choice(options.get(i - 1), i)).collect(Collectors.toList());

	}
	
	public GenericMenu(String title)
	{
		this(title, new ArrayList<Object>());
	}

	@Override
	public String getTitle()
	{
		return this.title;
	}

	@Override
	public List<Choice> getOptions()
	{
		return List.copyOf(options);
	}
	
	public void add(Object c)
	{
		options.add(new Choice(c, options.size()));
	}
	
	public Object pop(int key)
	{
		return this.options.remove(key).getContent();
	}

}
