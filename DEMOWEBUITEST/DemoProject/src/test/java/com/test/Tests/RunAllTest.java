package com.test.Tests;

import static com.automation.ConfigPropertyReader.getProperty;
import static com.automation.ExtentTestNGITestListener.test;

import com.automation.ExcelDriverTest;

public class RunAllTest {

	public static void main(String[] args) {
		String fileName = System.getProperty("suiteFile", getProperty("suiteFile"));
		ExcelDriverTest.runExcelTestSuite(fileName);
		}

}
