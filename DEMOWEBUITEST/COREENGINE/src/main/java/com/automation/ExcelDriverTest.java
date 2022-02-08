package com.automation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;



public class ExcelDriverTest {
	static Workbook wb;
	static Sheet ws, ws1, ws2, ws3;
	static File excelFileToRead;
	static String readFilePath,testFlag, date, keyword,suiteName,tgName, packageName;
	static TestNG testNG;
	static Map<Integer,List<String>> testMap = new HashMap<Integer,List<String>>();


	public static void runExcelTestSuite(String suiteFileName)  {
		List<String> testList = new ArrayList<String>();

		try{
			suiteName = suiteFileName;
			readFilePath= System.getProperty("user.dir")+File.separator+"Stores"+File.separator+"Files"+File.separator+"TestWorkFlow"+File.separator+suiteFileName+".xlsx";
			excelFileToRead = new File(readFilePath); 
			FileInputStream fis = new FileInputStream(excelFileToRead); 
			XSSFWorkbook book = new XSSFWorkbook(fis); 
			XSSFSheet sheet = book.getSheetAt(0);
			// get TestCases sheet
			int totalRows = sheet.getLastRowNum();

//			for (int r = 1,count=1; r <= totalRows; r++) {
//
//				try{
//					testFlag = sheet.getRow(r).getCell(3).toString();
//
//					if (testFlag.matches("x")) {
//						//date = sheet.getRow(r).getCell(2).toString();
//						tgName= sheet.getRow(r).getCell(1).toString();
//						keyword = sheet.getRow(r).getCell(5).toString();
//						testList.add(keyword);
//						//testMap.put(count++, Arrays.asList(date,keyword));
//						testMap.put(count++, Arrays.asList(tgName,keyword));
//						// Read another column for date that will act as ID or KEY , and store the upper read column as VALUE 
//						// Convert the list into map
//					}
//
//
//				}catch(NullPointerException e){
//
//
//				}
//
//			}
			
			for (int r = 1,count=1; r <= totalRows; r++) {

				try{
					testFlag = sheet.getRow(r).getCell(4).toString();

					if (testFlag.matches("x")) {
						//date = sheet.getRow(r).getCell(2).toString();
						tgName= sheet.getRow(r).getCell(2).toString();
						keyword = sheet.getRow(r).getCell(6).toString();
						packageName=sheet.getRow(r).getCell(7).toString();
						testList.add(keyword);
						
						//testMap.put(count++, Arrays.asList(date,keyword));
						testMap.put(count++, Arrays.asList(tgName,keyword,packageName));
						System.out.println(testMap);
						// Read another column for date that will act as ID or KEY , and store the upper read column as VALUE 
						// Convert the list into map
					}


				}catch(NullPointerException e){


				}

			}
			System.out.println("Following list of tests shall be executed - \n ");	



			for(Map.Entry<Integer, List<String>> entry : testMap.entrySet()){
				System.out.println(entry.getKey() +" : "+entry.getValue());
			}

			System.out.println("\n");
			runTestNGTests(testMap);

		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		} catch (SecurityException e) {

			e.printStackTrace();
		} catch (IllegalArgumentException e) {

			e.printStackTrace();
		} 

	}

	public static  void runTestNGTests(Map<Integer,List<String>> testMap){
		//Create an instance on TestNG
		TestNG myTestNG = new TestNG();	 
		//Create an instance of XML Suite and assign a name for it. 
		XmlSuite mySuite = new XmlSuite();
		mySuite.setName(suiteName);
		mySuite.setParallel(XmlSuite.ParallelMode.TESTS);  
		mySuite.setThreadCount(3);
		mySuite.onListenerElement("com.automation.ExtentTestNGITestListener");

		//Create a list which can contain the tests that you want to run.
		List<XmlTest> myTests = new ArrayList<XmlTest>();

		for(Map.Entry<Integer, List<String>> map : testMap.entrySet()){

			//Create an instance of XmlTest and assign a name for it.
			XmlTest myTest = new XmlTest(mySuite);

			myTest.setName(map.getKey()+".) web Test : "+map.getValue().get(0) + " on "+ map.getValue().get(1));		 
			//myTest.addParameter("Date", map.getValue().get(0));
			//Add any parameters that you want to set to the Test. 
			myTest.addParameter("TestCaseID", map.getValue().get(0));
			//myTest.setXmlClasses(Arrays.asList(new XmlClass("com.test.Tests."+map.getValue().get(1))));
			myTest.setXmlClasses(Arrays.asList(new XmlClass(map.getValue().get(2)+"."+map.getValue().get(1))));
			myTests.add(myTest);
		}



		mySuite.setTests(myTests);

		List<XmlSuite> mySuites = new ArrayList<XmlSuite>();
		mySuites.add(mySuite);

		myTestNG.setXmlSuites(mySuites);
		//myTestNG.run();
		//---------For printing and storing the testNG xml file. 
				 		System.out.println(mySuite.toXml());
				 		//myTestNG.run();
				 			FileWriter writer;
				 				try {
				 						writer = new FileWriter(new File("MyMasterSuite.xml"));
				 							writer.write(mySuite.toXml());
				 								writer.flush();
				 									writer.close();
				 									System.out.println(new File("MyMasterSuite.xml").getAbsolutePath());
				 						} catch (IOException e) {
				 					
				 					e.printStackTrace();
				 						}




	}
}
