package com.ss.utopia.test.dal;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;


/***
 * Runs unit tests for the Week 1 assignments
 * @param args
 */
public class TestRunner
{

	public static void main(String[] args)
	{
		Result r = JUnitCore.runClasses(TestSuite.class);
		
		for (Failure f: r.getFailures())
		{
			System.out.println(f.toString());
		}
		
		System.out.println(r.wasSuccessful());

	}

}
