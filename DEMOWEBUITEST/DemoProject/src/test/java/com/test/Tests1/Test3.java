package com.test.Tests1;

import org.testng.SkipException;
import org.testng.annotations.Test;

import com.automation.PreAndPostTestEvents;

import org.testng.annotations.Test;

public class Test3 extends PreAndPostTestEvents{
	boolean flag=Test2.ExecutionFlag;
	@Test()
	public void test3() {
		System.out.println(Test2.ExecutionFlag);
		if(flag) {
			throw new SkipException("Skip the execution");
		}
		
		System.out.println("test3 executed");
		Test2.ExecutionFlag=true;
	}
}
