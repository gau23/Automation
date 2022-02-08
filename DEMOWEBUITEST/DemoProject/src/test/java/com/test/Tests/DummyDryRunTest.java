package com.test.Tests;

import static com.automation.ConfigPropertyReader.*;

import java.util.Map;

import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.automation.*;
import com.test.Keywords.NewCustomerFunctions;
import com.test.Keywords.loginPageFunction;

import com.automation.PreAndPostTestEvents;
import com.automation.TestDataHandler;

public class DummyDryRunTest extends PreAndPostTestEvents {
	loginPageFunction loginPage;
	Map<String, String> dataTable;
	TestDataHandler testData;
	NewCustomerFunctions newCustomer;
	
	public void init() {
		testData = new TestDataHandler();
		 loginPage=new loginPageFunction(driver);
	}
	
	@Parameters({ "TestCaseID" })
	@Test
	public void TC01_LogIntoTheApplication(@Optional("Optional") String TestCaseID) throws InterruptedException {
		loginPage.loginToBankWeb(getProperty("URL"),getProperty("username"), getProperty("password"));
		//dataTable=testData.readMainSheet("NewCustomerDDT", TestCaseID.replace("\"", "")) ;
		System.out.println("********"+TestCaseID+"*******");
		
		//newCustomer.AddNewCustomer();
	}
	
	//@Test
//	public void TC02_AddNewCustomer() throws InterruptedException {
//		newCustomer.AddNewCustomer();
//		
//		
//	}
}
