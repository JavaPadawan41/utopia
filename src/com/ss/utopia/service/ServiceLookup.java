package com.ss.utopia.service;

import java.util.HashMap;
import java.util.Map;

import com.ss.utopia.service.base.Service;

public class ServiceLookup
{
	private Map<Object, Service> lookup;
	
	public ServiceLookup()
	{
		this.lookup = new HashMap<Object, Service>();
	}
	
	public Boolean containsKey(Object key)
	{
		return this.lookup.containsKey(key);
	}
	
	public void put(Object key, Service value)
	{
		this.lookup.put(key, value);
	}
	
	public Service get(Object key)
	{
		return this.lookup.get(key);
	}
}
