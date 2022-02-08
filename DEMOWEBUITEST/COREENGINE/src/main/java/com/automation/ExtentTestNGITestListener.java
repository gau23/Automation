package com.automation;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

//import com.ExtentReports.ExtentTestNGReportBuilder;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;


public class ExtentTestNGITestListener implements ITestListener{
	private static ExtentReports extent = ExtentManager.createInstance();
	private static ThreadLocal<ExtentTest> parentTest = new ThreadLocal<ExtentTest>();
	public static ThreadLocal<ExtentTest> test = new ThreadLocal<ExtentTest>();
	
	public synchronized void onStart(ITestContext context) {
		System.out.println("Extent Reports Version 3 Test Suite started!");
		ExtentTest parent = extent.createTest(context.getName());
		parentTest.set(parent);
	}

	public synchronized void onFinish(ITestContext context) {
		System.out.println(("Extent Reports Version 3  Test Suite is ending!"));
		extent.flush();
	}

	public synchronized void onTestStart(ITestResult result) {
		System.out.println((result.getMethod().getMethodName() + " started!"));
		ExtentTest child = parentTest.get().createNode(result.getMethod().getMethodName());
		test.set(child);
	
	}

	public synchronized void onTestSuccess(ITestResult result) {
		System.out.println((result.getMethod().getMethodName() + " passed!"));
		test.get().pass("Test passed");
		
	}

	public synchronized void onTestFailure(ITestResult result) {
		Object currentClass = result.getInstance();
		WebDriver webDriver = ((PreAndPostTestEvents) currentClass).getActiveObject();
	        System.out.println((result.getMethod().getMethodName() + " failed!"));
			test.get().fail(result.getThrowable());
		 if (!result.isSuccess()) {
	            try {
	            	File scrFile = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
	            	
	            	FileUtils.copyFile(scrFile, new File("target/failsafe-reports/screenshots/"+result.getTestClass().getName()+".png"));
	               String screenShotEncode = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.BASE64);
	             
	               Reporter.log("!!----------Capturing screenshot------------!!",true);
	               Reporter.log("<td><a href='data:image/png;base64, "+screenShotEncode+"'><img src='data:image/png;base64, "+screenShotEncode
	                       + "' height='800' width='1200' /></a></td>");
	           	test.get().fail("Failure Screenshot -->", MediaEntityBuilder.createScreenCaptureFromBase64String(screenShotEncode).build());
	             
	             } catch (Exception ex) {
	            	System.out.println("Uh oh !, An exception occurred while capturing screenshot!");
	                ex.printStackTrace();
	            }
		 }
       
	}

	public synchronized void onTestSkipped(ITestResult result) {
		System.out.println((result.getMethod().getMethodName() + " skipped!"));
		test.get().skip(result.getThrowable());
	}

	public synchronized void onTestFailedButWithinSuccessPercentage(ITestResult result) {

	}
}
