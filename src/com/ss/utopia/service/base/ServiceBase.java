package com.ss.utopia.service.base;


import com.ss.utopia.service.ServiceLookup;


public abstract class ServiceBase implements Service
{
	protected ServiceLookup lookup;
	
	public ServiceBase()
	{
		this.lookup = new ServiceLookup();
	}

	@Override
	public abstract void start();

}
