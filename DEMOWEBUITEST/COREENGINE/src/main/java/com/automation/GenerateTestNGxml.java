package com.automation;

import static com.automation.ConfigPropertyReader.getProperty;
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
import org.testng.xml.XmlSuite.ParallelMode;
import org.testng.xml.XmlTest;



public class GenerateTestNGxml {
	static Workbook wb;
	static Sheet ws, ws1, ws2, ws3;
	static File excelFileToRead;
	static String readFilePath,testFlag, date, keyword,suiteName,parameter;
	static TestNG testNG;
	static Map<Integer,List<String>> testMap = new HashMap<Integer,List<String>>();
	static int testFlagColumnIndex = 0,testCaseIDColumnIndex = 0,testClassNameColumnIndex = 0 ;
	
	public static void generateTestSuite(String suiteFileName)  {
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
			for(int i =0 ; i < sheet.getRow(0).getLastCellNum();i++) {
				System.out.println("index : "+ i+ ", column name :" + sheet.getRow(0).getCell(i).toString());
				switch(sheet.getRow(0).getCell(i).toString().trim()) {
				case "TestRun"			: testFlagColumnIndex = i;
										  break;
				case "ExecutionDate" 	: testCaseIDColumnIndex = i;
										  parameter = "Date"; 	
				
										  break;
				case "TestCaseID"		: testCaseIDColumnIndex = i;
										  parameter = "TestCaseID";
										  break;	
				case "KeywordTestName"	: testClassNameColumnIndex = i;
										  break;
								
				}
//				System.out.println("testFlagColumnIndex : "+ testFlagColumnIndex);
//				System.out.println("testCaseIDColumnIndex : "+ testCaseIDColumnIndex);
//				System.out.println("testClassNameColumnIndex : "+ testClassNameColumnIndex);
				
			}
			
			 
			
		
			for (int r = 1,count=1; r <= totalRows; r++) {
				
				try{
			testFlag = sheet.getRow(r).getCell(testFlagColumnIndex).toString().trim();
			
			if (testFlag.matches("x")) {
				date = sheet.getRow(r).getCell(testCaseIDColumnIndex).toString().trim();
				keyword = sheet.getRow(r).getCell(testClassNameColumnIndex).toString().trim();
				testList.add(keyword);
				testMap.put(count++, Arrays.asList(date,keyword));
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
		draftTestNGtestSuite(testMap);
		
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
	
	public static  void draftTestNGtestSuite(Map<Integer,List<String>> testMap){
		
		 TestNG myTestNG = new TestNG();	 
		 XmlSuite mySuite = new XmlSuite();
		 mySuite.setName(suiteName);
		 
		 if(System.getProperty("ParallelMode",getProperty("ParallelMode")).equalsIgnoreCase("true")) {
			 mySuite.setParallel(ParallelMode.TESTS);			  
			 mySuite.setThreadCount(2);
			 	 
		 }
		
			 
			 
		 
		 List<XmlTest> myTests = new ArrayList<XmlTest>();
		 
		 for(Map.Entry<Integer, List<String>> map : testMap.entrySet()){
		
		
			 XmlTest myTest = new XmlTest(mySuite);
			 
			 myTest.setName(map.getKey()+".) web Test : "+map.getValue().get(0) + " on "+ map.getValue().get(1));
			 myTest.addParameter(parameter, map.getValue().get(0));
			 myTest.setXmlClasses(Arrays.asList(new XmlClass("com.bravura.tests."+map.getValue().get(1))));
		
			 myTests.add(myTest);
		 }

	
		 
		 mySuite.setTests(myTests);
		 
		 List<XmlSuite> mySuites = new ArrayList<XmlSuite>();
		 mySuites.add(mySuite);
		 
		 	myTestNG.setXmlSuites(mySuites);
			
//---------For printing and storing the testNG xml file. 
		 		System.out.println(mySuite.toXml());
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
