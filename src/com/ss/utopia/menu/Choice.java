package com.ss.utopia.menu;

public class Choice
{
	private Object content;
	private Integer ordinal;
	private static final String formatString = "%d)\t%s";
	
	public Choice(Object content, Integer ordinal)
	{
		this.content = content;
		this.ordinal = ordinal;
	}
	
	public String getContent()
	{
		return this.getContent().toString();
	}
	
	public Integer getOrdinal()
	{
		return this.ordinal;
	}
	
	@Override
	public String toString()
	{
		return String.format(formatString, this.ordinal, this.content.toString());
	}
}
