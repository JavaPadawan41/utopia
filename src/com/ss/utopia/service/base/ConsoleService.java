package com.ss.utopia.service.base;



import com.ss.utopia.service.ServiceLookup;
import com.ss.utopia.view.View;

public abstract class ConsoleService extends ServiceBase
{
	protected View view;
	protected ServiceLookup lookup;
	
	public ConsoleService(View view)
	{
		super();
		if (view == null)
			throw new NullPointerException("view");
		
		
		this.view = view;
		this.lookup = new ServiceLookup();
	}

	@Override
	public abstract void start();
	
	public void SetLookup(ServiceLookup l)
	{
		if (lookup == null)
			throw new NullPointerException("l");
		
		this.lookup = l;
	}

}
