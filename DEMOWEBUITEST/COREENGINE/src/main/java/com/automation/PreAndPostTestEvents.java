package com.automation;

import static com.automation.ConfigPropertyReader.*;

import java.io.File;
import java.lang.reflect.Method;

import org.aspectj.weaver.ast.Test;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

@Listeners({com.automation.ExtentTestNGITestListener.class})

public class PreAndPostTestEvents {
	protected webDriverCreator driver;

	public void init(){
		
	}
	
	@BeforeClass
	public void setup(){
		String browser = System.getProperty("browser",getProperty( System.getProperty("user.dir")+File.separator+"Config.properties","browser"));
		driver = new webDriverCreator(browser);
		init();
		}
		
		 @BeforeMethod
		    public void Initialization(Method method) {
		        System.out.println("__________________________________________________________________________");
		        
		 }
		 
		 @AfterMethod
		    public void captureScreenShotIfFailure(ITestResult result) {
			 System.out.println("******************************************************");
			 System.out.println("Test Name: " + result.getName());
		        if (!result.isSuccess()) {
		        	 System.out.println("Test Result: FAIL");
		                 
		        } else {
		        	 System.out.println("Test Result: PASS");
		        }
		        
		        System.out.println("******************************************************");
		        System.out.println( "__________________________________________________________________________");
		    }

	@AfterClass
	public void tearDown(){
		driver.closeBrowserSession();

		
		}
	
	public WebDriver getActiveObject(){
		return this.driver.getDriver();
	}
}

