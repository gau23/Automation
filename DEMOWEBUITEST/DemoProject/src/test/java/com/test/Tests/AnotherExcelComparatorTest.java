package com.test.Tests;


	

	import static com.automation.ConfigPropertyReader.getProperty;

import java.io.BufferedWriter;
	import java.io.DataOutputStream;
	import java.io.File;
	import java.io.FileInputStream;
	import java.io.FileNotFoundException;
	import java.io.FileOutputStream;
	import java.io.FileWriter;
	import java.io.IOException;
	import java.text.ParseException;
	import java.text.SimpleDateFormat;
	import java.time.LocalDate;
	import java.time.ZonedDateTime;
	import java.time.format.DateTimeFormatter;
	import java.util.Arrays;
	import java.util.HashMap;
	import java.util.Locale;
	import org.apache.poi.xssf.usermodel.XSSFCell;
	import org.apache.poi.xssf.usermodel.XSSFRow;
	import org.apache.poi.xssf.usermodel.XSSFSheet;
	import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.automation.PreAndPostTestEvents;
import com.automation.TestDataHandler;
import com.test.Keywords.NewCustomerFunctions;
import com.test.Keywords.loginPageFunction;

	public class AnotherExcelComparatorTest extends PreAndPostTestEvents  {
		static loginPageFunction loginPage;
		private static String strLogActual;
		private static String strLogExpected;
		private static String strHeader;
		private static String tableStyle;
//		public static String expectedFilePath = System.getProperty("expectedFile");
//		public static String actualFilePath = System.getProperty("actualFile");
//		public static String expectedSheetName = System.getProperty("expectedSheetName");
//		public static String actualSheetName = System.getProperty("actualSheetName");
//		public static boolean ignorePostDecimalValues = Boolean.parseBoolean(System.getProperty("ignorePostDecimalValues"));
		public static HashMap<Integer, String> hmTableHeader = new HashMap();
		public static String[] SkipFieldsVerify = new String[]{"Transaction Ref", "Investment Ref", "Certificate ID",
				"Transaction Reference"};
		public static int intCurrentColumnIndex = 0;

		
		
		@Test
		public static void main() throws IOException
		{
			String expectedFilePath="C:\\Users\\EX889DG\\eclipse-workspace\\TestProjectDemo1\\Stores\\Files\\DataTable.xlsx";
			String actualFilePath="C:\\Users\\EX889DG\\eclipse-workspace\\TestProjectDemo1\\Stores\\Files\\DataTable1.xlsx";
			String expectedSheetName="Data1";
			String actualSheetName="Data1";
			//loginPage.loginToBankWeb(getProperty("URL"));
			String path=System.getProperty("user.dir");
			System.out.println(path);
			String chromeDriverPath=path+"\\src\\main\\resources\\Drivers\\Chromdriver\\chromedriver.exe";
			System.out.println(chromeDriverPath);
			System.setProperty("webdriver.chrome.driver", chromeDriverPath);
			ChromeOptions p = new ChromeOptions();
			p.addArguments("--disable-notifications");
			WebDriver driver=new ChromeDriver(p);
			WebDriverWait  wait= new WebDriverWait(driver, 10);
			String fileURL = System.getProperty("user.dir");
			driver.get(fileURL + "/src/test/resources/htmlFiles/WebTable.html");
			loginPage.ExcelCompare(expectedFilePath ,actualFilePath,expectedSheetName ,actualSheetName,false);
			
			//Reporter.addStepLog("<a href='file:///C:/Users/anbajaj/Desktop/test.xml'>Response</a>"); 
		}
		
	}

