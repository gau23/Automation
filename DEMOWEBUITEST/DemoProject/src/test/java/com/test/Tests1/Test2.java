package com.test.Tests1;

import static com.automation.ConfigPropertyReader.getProperty;

import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

import com.automation.PreAndPostTestEvents;
import com.automation.TestDataHandler;
import com.test.Keywords.NewCustomerFunctions;
import com.test.Keywords.loginPageFunction;

import org.testng.annotations.Test;

public class Test2 extends PreAndPostTestEvents{
	loginPageFunction loginPage;
	public static boolean ExecutionFlag=true;
	
	public void init() {
//		loginPage = new loginPageFunction(driver);
	}
	
	@Test()
	public void test2() {
//		loginPage.loginToBankWeb(getProperty("URL"),getProperty("username"), getProperty("password"));
//		
		System.out.println(ExecutionFlag);
		if(!ExecutionFlag) {
			throw new SkipException("Skip the execution");
		}
		Assert.assertTrue(false);
		System.out.println("test2 executed");
		ExecutionFlag=false;
	}
}
