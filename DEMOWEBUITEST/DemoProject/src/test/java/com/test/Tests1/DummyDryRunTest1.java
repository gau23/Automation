package com.test.Tests1;

import static com.automation.ConfigPropertyReader.*;

import java.util.Map;

import org.junit.Assert;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.automation.*;
import com.test.Keywords.NewCustomerFunctions;
import com.test.Keywords.loginPageFunction;

import jdk.internal.org.jline.utils.Log;

import com.automation.PreAndPostTestEvents;
import com.automation.TestDataHandler;

public class DummyDryRunTest1 extends PreAndPostTestEvents {
	loginPageFunction loginPage;
	Map<String, String> dataTable;
	TestDataHandler testData;
	NewCustomerFunctions newCustomer;
	
	public void init() {
		testData = new TestDataHandler();
		loginPage = new loginPageFunction(driver);
//		newCustomer=new NewCustomerFunctions(driver);
	}
	
	@Parameters({ "TestCaseID" })
	@Test
	public void TC01_LogIntoTheApplication(@Optional("T2004282_02") String TestCaseID) throws InterruptedException {
		loginPage.loginToBankWeb(getProperty("URL"),getProperty("username"), getProperty("password"));
		dataTable=testData.readMainSheet("NewCustomerDDT", TestCaseID.replace("\"", "")) ;
		System.out.println("********"+dataTable.get("TestStepID")+"*******");
		int a=1;
		int b=2;
		int c=a+b;
		
		Assert.assertEquals(c, b);
		
		//newCustomer.AddNewCustomer();
	}
	
	//@Test
//	public void TC02_AddNewCustomer() throws InterruptedException {
//		newCustomer.AddNewCustomer();
//		
//		
//	}
}
