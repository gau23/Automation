package com.automation;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.IClass;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestListenerAdapter;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.automation.ExtentManager;
import com.automation.PreAndPostTestEvents;

import io.qameta.allure.Attachment;

public class ListenersEvents extends TestListenerAdapter {
	private static ExtentReports extent = ExtentManager.createInstance();
	private static ThreadLocal<ExtentTest> parentTest = new ThreadLocal<ExtentTest>();
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<ExtentTest>();
	
    @Override
   	public synchronized void onStart(ITestContext context) {
       	System.out.println("Extent Reports Version 3 Test Suite started!");
       	ExtentTest parent = extent.createTest(context.getName());
           parentTest.set(parent);
   	}
	
	
	@Override
	public synchronized void onTestStart(ITestResult tr) {
		Reporter.log("Test Started....\n",true);
		System.out.println((tr.getMethod().getMethodName() + " started!"));
		ExtentTest child = parentTest.get().createNode(tr.getMethod().getMethodName());
        test.set(child);
	}

	@Override
	public synchronized void onTestSuccess(ITestResult tr) {
		System.out.println((tr.getMethod().getMethodName() + " passed!"));
		test.get().pass("Test passed");
		
		Reporter.log("Test '" + tr.getName() + "' FINISHED \n",true);

		// This will print the class name in which the method is present
		log(tr.getTestClass());

		// This will print the priority of the method.
		// If the priority is not defined it will print the default priority as
		// 'o'
	


	}

	@Override
	public synchronized void onTestFailure(ITestResult result) {
		Object currentClass = result.getInstance();
        WebDriver webDriver = ((PreAndPostTestEvents) currentClass).getActiveObject();
        System.out.println((result.getMethod().getMethodName() + " failed!"));
		test.get().fail(result.getThrowable());
		 
		if (!result.isSuccess()) {
	        	  Reporter.log("Test Result: FAIL \n",true);
	            takeScreenshotOfFailureFromLocalMachine(result,webDriver);
	               
	        } else {
	        	Reporter.log("Test Result: PASS \n",true);
	        }
	}

	
	


	private synchronized void log(IClass testClass) {
		System.out.println(testClass);
	}
	@Override
	public   void onTestSkipped(ITestResult result) {
		System.out.println((result.getMethod().getMethodName() + " skipped!"));
		test.get().skip(result.getThrowable());
	}
	
	 @Attachment(value = "Failure screenshot", type="image/png")
	 public byte[] takeScreenshotOfFailureFromLocalMachine(ITestResult result,WebDriver driver) {
	        Calendar calendar = Calendar.getInstance();
	        SimpleDateFormat formater = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
	        String methodName = result.getName();
	        if (!result.isSuccess()) {
	            try {
	            	File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
	            	
	            	FileUtils.copyFile(scrFile, new File("target/failsafe-reports/screenshots/"+result.getTestClass().getName()+".jpg"));
	               String screenShotEncode = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
	               

	               Reporter.log("!!----------Capturing screenshot------------!!",true);
	               Reporter.log("<td><a href='data:image/png;base64, "+screenShotEncode+"'><img src='data:image/png;base64, "+screenShotEncode
	                       + "' height='800' width='1200' /></a></td>");
	           	test.get().fail("details", MediaEntityBuilder.createScreenCaptureFromBase64String(screenShotEncode).build());
	            
	           } catch (Exception ex) {
	            	System.out.println("Uh oh !, An exception occurred while capturing screenshot!");
	                ex.printStackTrace();
	            }
	        }
	        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
	         
	    }

}
