	package com.automation;


import static com.automation.ConfigPropertyReader.getProperty;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;
import org.testng.Reporter;

public class TestDataHandler {
	String readFilePath;
	String writeFilePath;
	File excelFileToRead;
	File globalVariablesExcelSheet;

	public TestDataHandler() {

		if (getProperty("globalVariableSheetPath") != null) {
			String configFilePath = new File(System.getProperty("user.dir")).getParent();
			String filePath = getProperty("globalVariableSheetPath");
			String writeFilePath = configFilePath.replace("WEBUITESTS", filePath);
			System.out.println(writeFilePath);
			globalVariablesExcelSheet = new File(writeFilePath);

		} else {
			writeFilePath = System.getProperty("user.dir") + File.separator + "Stores" + File.separator + "Files"
					+ File.separator + "GLOBLE_VARIABLES.xlsx";
			globalVariablesExcelSheet = new File(writeFilePath);
		}

	}

	public Map<String, String> readMainSheet(String fileName, String key) {
		Map<String, String> dataSet = new TreeMap<String, String>();
		try {
			readFilePath = System.getProperty("user.dir") + File.separator + "Stores" + File.separator + "Files"
					+ File.separator + "InputTestData" + File.separator + fileName + ".xlsx";
			excelFileToRead = new File(readFilePath);
			FileInputStream fis = new FileInputStream(excelFileToRead);
			XSSFWorkbook book = new XSSFWorkbook(fis);
			XSSFSheet sheet = book.getSheet("MAIN");
			Iterator<Row> itr = sheet.iterator();
			// Iterating over Excel file in Java
			int totalRows = sheet.getLastRowNum();
			System.out.println("total rows  in data sheet:" + totalRows);
			int keyRow = 0;
			for (int i = 1; i <= totalRows; i++) {
				System.out.println(sheet.getRow(i).getCell(1) + " & " + key);
				if (returnCellStringValue(sheet.getRow(i).getCell(1)).equals(key)) { // Append
																						// the
																						// key
																						// criteria
																						// here

					keyRow = i;
					break;
				}
			}

			int totalColumns = sheet.getRow(keyRow).getPhysicalNumberOfCells();

			for (int i = 0; i < totalColumns; i++) {
				dataSet.put(returnCellStringValue(sheet.getRow(0).getCell(i)),
						returnCellStringValue(sheet.getRow(keyRow).getCell(i)));
			}
			System.out.println(
					"The data set for Key " + sheet.getRow(keyRow).getCell(1) + "  is extracted from excel file  :-");
			System.out.println(dataSet);

			fis.close();
			book.close();
			return dataSet;
		} catch (FileNotFoundException fe) {
			fe.printStackTrace();
		} catch (IOException ie) {
			ie.printStackTrace();
		}
		return dataSet;

	}

	public List<String> readDataSheet(String fileName, String key) {
		List<String> dataList = new ArrayList<String>();
		try {
			readFilePath = System.getProperty("user.dir") + File.separator + "Stores" + File.separator + "Files"
					+ File.separator + "InputTestData" + File.separator + fileName + ".xlsx";
			excelFileToRead = new File(readFilePath);
			FileInputStream fis = new FileInputStream(excelFileToRead);
			XSSFWorkbook book = new XSSFWorkbook(fis);
			XSSFSheet sheet = book.getSheet("DATA");
			Iterator<Row> itr = sheet.iterator();
			// Iterating over Excel file in Java
			int totalRows = sheet.getLastRowNum();

			System.out.println("total rows  in data sheet:" + totalRows);

			for (int i = 1; i <= totalRows; i++) {

				if (returnCellStringValue(sheet.getRow(i).getCell(0)).equals(key)) { // Append
																						// the
																						// key
																						// criteria
																						// here
					dataList.add(returnCellStringValue(sheet.getRow(i).getCell(1)));

				}
			}

			System.out.println(dataList);
			fis.close();
			book.close();
			return dataList;
		} catch (FileNotFoundException fe) {
			fe.printStackTrace();
		} catch (IOException ie) {
			ie.printStackTrace();
		}
		return dataList;

	}

	public List<Map<String, String>> readDataSheetAndReturnMapList(String fileName, String key) {
		List<Map<String, String>> dataList = new LinkedList<Map<String, String>>();
		try {
			readFilePath = System.getProperty("user.dir") + File.separator + "Stores" + File.separator + "Files"
					+ File.separator + "InputTestData" + File.separator + fileName + ".xlsx";
			excelFileToRead = new File(readFilePath);
			FileInputStream fis = new FileInputStream(excelFileToRead);
			XSSFWorkbook book = new XSSFWorkbook(fis);
			XSSFSheet sheet = book.getSheet("DATA");
			System.out.println(sheet);
			// Iterating over Excel file in Java
			int totalRows = sheet.getLastRowNum();
			int totalColumns = sheet.getRow(0).getPhysicalNumberOfCells();

			System.out.println("total rows  in data sheet:" + totalRows + " and  total columns :" + totalColumns);

			for (int i = 1; i <= totalRows; i++) {

				if (returnCellStringValue(sheet.getRow(i).getCell(0)).equals(key)) { // Append
																						// the
																						// key
																						// criteria
																						// here
					Map<String, String> localMap = new HashMap<String, String>();
					localMap.clear();
					for (int j = 0; j < totalColumns; j++) {
						localMap.put(returnCellStringValue(sheet.getRow(0).getCell(j)),
								returnCellStringValue(sheet.getRow(i).getCell(j)));
					}

					dataList.add(localMap);

				}
			}

			System.out.println(dataList);
			fis.close();
			book.close();
			return dataList;
		} catch (FileNotFoundException fe) {
			fe.printStackTrace();
		} catch (IOException ie) {
			ie.printStackTrace();
		}
		return dataList;

	}
	
	/*
	 * Purpose:- Added Overwrite method to handle the dynamic column index for Data sheet
	 * Author- Anup
	 */
	public List<Map<String, String>> readDataSheetAndReturnMapList(String fileName, String key, int columnIndex) {
		List<Map<String, String>> dataList = new LinkedList<Map<String, String>>();
		try {
			readFilePath = System.getProperty("user.dir") + File.separator + "Stores" + File.separator + "Files"
					+ File.separator + "InputTestData" + File.separator + fileName + ".xlsx";
			excelFileToRead = new File(readFilePath);
			FileInputStream fis = new FileInputStream(excelFileToRead);
			XSSFWorkbook book = new XSSFWorkbook(fis);
			XSSFSheet sheet = book.getSheet("DATA");
			System.out.println(sheet);
			// Iterating over Excel file in Java
			int totalRows = sheet.getLastRowNum();
			int totalColumns = sheet.getRow(0).getPhysicalNumberOfCells();

			System.out.println("total rows  in data sheet:" + totalRows + " and  total columns :" + totalColumns);

			for (int i = 1; i <= totalRows; i++) {
				if ((sheet.getRow(i).getCell(columnIndex)!=null) && (key!=null)) { 
					if (key.equals(returnCellStringValue(sheet.getRow(i).getCell(columnIndex)))){
						//if (returnCellStringValue(sheet.getRow(i).getCell(0)).equals(key)) { // Append
																						// the
																						// key
																						// criteria
																						// here
						Map<String, String> localMap = new HashMap<String, String>();
						localMap.clear();
						for (int j = 0; j < totalColumns; j++) {
							if ((sheet.getRow(0).getCell(j)!=null) && (sheet.getRow(i).getCell(j)!=null)) {
								localMap.put(returnCellStringValue(sheet.getRow(0).getCell(j)),
									returnCellStringValue(sheet.getRow(i).getCell(j)));
							}
						}
					dataList.add(localMap);
					}
				}
			}
			System.out.println(dataList);
			fis.close();
			book.close();
			return dataList;
		} catch (FileNotFoundException fe) {
			fe.printStackTrace();
		} catch (IOException ie) {
			ie.printStackTrace();
		}
		return dataList;
	}
	
	
	public List<Map<String, String>> readOutputExpectedSheet(String fileName, String sheetName) {
		List<Map<String, String>> dataList = new LinkedList<Map<String, String>>();
		try {
			readFilePath = System.getProperty("user.dir") + File.separator + "Stores" + File.separator + "Files"
					+ File.separator + "OutputExpectedData" + File.separator + fileName + ".xlsx";
			excelFileToRead = new File(readFilePath);
			FileInputStream fis = new FileInputStream(excelFileToRead);
			XSSFWorkbook book = new XSSFWorkbook(fis);
			XSSFSheet sheet = book.getSheet(sheetName);
			System.out.println(sheet);
			// Iterating over Excel file in Java
			int totalRows = sheet.getLastRowNum();
			int totalColumns = sheet.getRow(0).getPhysicalNumberOfCells();

			System.out.println("total rows  in data sheet:" + totalRows + " and  total columns :" + totalColumns);

			for (int i = 1; i <= totalRows; i++) {

				
					Map<String, String> localMap = new HashMap<String, String>();
					localMap.clear();
					for (int j = 0; j < totalColumns; j++) {
						localMap.put(returnCellStringValue(sheet.getRow(0).getCell(j)),
								returnCellStringValue(sheet.getRow(i).getCell(j)));
					}

					dataList.add(localMap);

				
			}

			System.out.println(dataList);
			fis.close();
			book.close();
			return dataList;
		} catch (FileNotFoundException fe) {
			fe.printStackTrace();
		} catch (IOException ie) {
			ie.printStackTrace();
		}
		return dataList;

	}

	private String returnCellStringValue(Cell cell) {
		String result = null;
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_STRING:
			result = cell.getRichStringCellValue().getString();
			break;
		case Cell.CELL_TYPE_NUMERIC:
			if (DateUtil.isCellDateFormatted(cell)) {
				result = cell.getDateCellValue().toString();
			}
			if (DateUtil.isCellInternalDateFormatted(cell)) {

			} else {
				cell.setCellType(Cell.CELL_TYPE_STRING);
				result = String.valueOf(cell.getRichStringCellValue().getString());
			}
			break;
		case Cell.CELL_TYPE_BOOLEAN:
			result = String.valueOf(cell.getBooleanCellValue());
			break;
		case Cell.CELL_TYPE_FORMULA:
			result = String.valueOf(cell.getCellFormula());
			break;

		default:

		}
		return result;
	}

	public void verifyFileInDownloads(String initials) {
		String fileName = null;
		boolean bName = false;
		Calendar c = Calendar.getInstance();
		String strDate = String.valueOf(c.getTimeInMillis());
		int iCount = 0;
		File dir = new File(System.getProperty("user.dir") + File.separator +"target");
		File[] files = dir.listFiles();
		for (File f : files) {

			fileName = f.getName();
			
			if (fileName.startsWith(initials) && fileName.endsWith(".csv")
					&& fileName.contains(strDate.substring(0, 7))) {
				iCount++;
				System.out.println(strDate.substring(0, 7));
				Reporter.log("Found file :" + fileName);
			}
		}
		System.out.println("File Count In Folder ::" + iCount);
		Assert.assertTrue(iCount > 0);

		for (File f : files) {
			fileName = f.getName();
			if (fileName.startsWith(initials) && fileName.endsWith(".csv")
					&& fileName.contains(strDate.substring(0, 7))) {
				f.delete();
				Reporter.log("Deleting file :" + fileName);
			}
		}

	}

	public void appendSheet(String key, String value) {
		try {
			boolean keyPersists = false;

			FileInputStream fis = new FileInputStream(globalVariablesExcelSheet);
			XSSFWorkbook book = new XSSFWorkbook(fis);
			XSSFSheet sheet = book.getSheetAt(0);
			Iterator<Row> itr = sheet.iterator();

			while (itr.hasNext()) {
				Row row = itr.next();
				if (row.getCell(0)!=null){	//Added by Anup to handle blank cell value
					//if (row.getCell(0).getStringCellValue().contains(key)) { Disabled by Anup on 18/11/19, to make sure only matching key value fetch from global variable
					if (row.getCell(0).getStringCellValue().equalsIgnoreCase(key)) {
						row.getCell(1).setCellValue(value);
						keyPersists = true;
						System.out.println("Key already persists in the file!");
						break;
					}
				}	
			}

			if (keyPersists == false) {
				fis.close();
				int rownum = sheet.getLastRowNum();
				rownum += 1;
				Row row = sheet.createRow(rownum);

				row.createCell(0).setCellValue(key);
				row.createCell(1).setCellValue(value);
				System.out.println("New key added in the target excel file ");
			}
			fis.close();
			FileOutputStream os = new FileOutputStream(globalVariablesExcelSheet);
			book.write(os);
			book.close();
			os.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public boolean loadTCProjectGlobalSheet(String projectName){
		File parentDir = new File(System.getProperty("user.dir")).getParentFile();
		writeFilePath = parentDir +File.separator+ projectName + File.separator + "Stores" + File.separator + "Files"
				+ File.separator + "GLOBLE_VARIABLES.xlsx";
		System.out.println("Loaded TC project global sheet from : "+writeFilePath);
		
		return true;
	}

	public String getGlobalVariablesValue(String Key) {
		String result = null;
		try {
		

			FileInputStream fis = new FileInputStream(globalVariablesExcelSheet);
			XSSFWorkbook book = new XSSFWorkbook(fis);
			XSSFSheet sheet = book.getSheetAt(0);
			Iterator<Row> itr = sheet.iterator();
			System.out.println("Searching  for "+Key+" at location :"+globalVariablesExcelSheet);
			while (itr.hasNext()) {
				Row row = itr.next();
				//if (row.getCell(0).getStringCellValue().contains(Key)) {		Disabled by Anup on 18/11/19, to make sure only matching key value fetch from global variable
				if (row.getCell(0).getStringCellValue().equalsIgnoreCase(Key)) {
					result = row.getCell(1).getStringCellValue();
				    System.out.println("Found value : "+ result+"for key :"+Key);
					
					break;
				}
			}

			
			fis.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;

	}
	
	//Added by MAReddy - for exporting table to excel files.
		public void exportTableToFile(String FileName,String SheetName,List<Map<String,String>> TableData) {
			try {
				String FilePath=System.getProperty("user.dir") + File.separator + "Stores" + File.separator + "Files"
						+ File.separator + FileName + ".xlsx";
				File file = new File(FilePath);
				if(file.exists()) {
					file.delete();
					System.out.println("File existed so Deleted");
				}

				FileOutputStream os = new FileOutputStream(FilePath);
				XSSFWorkbook book = new XSSFWorkbook();
				XSSFSheet sheet = book.createSheet(SheetName);
				Iterator<Row> itr = sheet.iterator();
				int columnCount =0, rowCount = 1;
				 //Setting column names
				for(Map<String,String> firstSet:TableData) {
					int i=0;
					 Row row = sheet.createRow(0);
					for(String Key:firstSet.keySet()) {
						row.createCell(i).setCellValue(Key);
						System.out.print("|"+Key+"|");
						i++;
					}
					columnCount = i;
					break;
				}
				System.out.println();
				for(Map<String,String> allSet:TableData) {
					
				Row row = sheet.createRow(rowCount++);
					for(int j=0; j<columnCount;j++) {
						row.createCell(j).setCellValue(allSet.get(sheet.getRow(0).getCell(j).getStringCellValue()));
						System.out.print("|"+allSet.get(sheet.getRow(0).getCell(j).getStringCellValue())+"|");
					}
					System.out.println();
					
				}
			    

			
				
				
				book.write(os);
				book.close();
				os.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public static String currentPath() {
			String readFilePath = System.getProperty("user.dir");
			return readFilePath;
		}

}
