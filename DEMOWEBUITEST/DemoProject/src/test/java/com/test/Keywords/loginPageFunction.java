package com.test.Keywords;

import static com.automation.ExtentTestNGITestListener.test;

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
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.automation.*;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.test.Keywords.CommonFunctions;
import com.test.PageObjects.loginPageObjects;

public class loginPageFunction extends CommonFunctions {
	webDriverCreator driver;
	private static String strLogActual;
	private static String strLogExpected;
	private static String strHeader;
	private static String tableStyle;
	public static HashMap<Integer, String> hmTableHeader = new HashMap();
	public static String[] SkipFieldsVerify = new String[]{"Transaction Ref", "Investment Ref", "Certificate ID",
			"Transaction Reference"};
	public static int intCurrentColumnIndex = 0;
	loginPageObjects loginPage = new loginPageObjects();
	
	public loginPageFunction(webDriverCreator driver) {
		super(driver);
		this.driver = driver;

	}

	public void loginToBankWeb(String baseurl,String UserName, String Password) {
		
		driver.launchApplication(baseurl);
		driver.page.waitForElementToDisplay(loginPage.userName_TextField);
		driver.page.sendKeysByActionBuilder(loginPage.userName_TextField, UserName);
	
		//test.get().pass("Username",MediaEntityBuilder.createScreenCaptureFromBase64String("base64").build());
		
		driver.page.sendKeysByActionBuilder(loginPage.password_TextField, Password);
		//test.get().pass("Password",MediaEntityBuilder.createScreenCaptureFromBase64String("base64").build());
		
		driver.page.clickElement(loginPage.login_Button);
		driver.page.holdExecution(2000);
		driver.page.pageRefresh();
		String expectedPagetitle="Guru99 Bank Manager HomePage";
		driver.page.verifyPageTitleContains(expectedPagetitle);
		
		

	}
	
	public void loginToBankWeb(String baseurl) {
		driver.launchApplication(baseurl);
		
	}
	
	public static void ExcelCompare(String expectedFilePath,String actualFilePath,String expectedSheetName,String actualSheetName ,boolean ignorePostDecimalValues) throws IOException {
		System.out.println("_____________________________________________________________Starting File Comparision...");

		try {
			System.out.println("Expected File:-" + expectedFilePath);
			System.out.println("Actual File:-" + actualFilePath);
			FileInputStream expectedExcel = new FileInputStream(new File(expectedFilePath));
			FileInputStream actualExcel = new FileInputStream(new File(actualFilePath));
			XSSFWorkbook expectedWorkbook = new XSSFWorkbook(expectedExcel);
			XSSFWorkbook actualWorkbook = new XSSFWorkbook(actualExcel);
			XSSFSheet expectedSheet = expectedWorkbook.getSheet(expectedSheetName);
			XSSFSheet actualSheet = actualWorkbook.getSheet(actualSheetName);
			System.out.println("\n~~~~~~Comparing Expected Vs. Retrieved results~~~~~~~~");
			if (compareTwoSheets(expectedSheet, actualSheet)) {
				System.out.println("\n\nThe two excel sheets are Equal");
				//test.get().pass("API Log Link : <a href='file:///C:/temp/htmlOutput.html'>API LINK</a>");
				test.get().pass("API Log Link : <a href='./ExcelComparsionResults/htmlOutput.html'>API LINK</a>");
				
			} else {
				System.out.println("\n\nThe two excel sheets are Not Equal ⚠");
				//test.get().warning("API Log Link : <a href='file:///C:/temp/htmlOutput.html'>API LINK</a>");
				test.get().warning("API Log Link : <a href='./ExcelComparsionResults/htmlOutput.html' target='_blank' rel='noopener noreferrer'>API LINK</a>");
				
				
			}
			

			expectedExcel.close();
			actualExcel.close();
			expectedWorkbook.close();
			actualWorkbook.close();
		} catch (Exception var7) {
			var7.printStackTrace();
			FileWriter fstream = new FileWriter("c://temp//exception.txt");
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(var7.toString());
			out.close();
		}

	}

	public static boolean compareTwoSheets(XSSFSheet expected, XSSFSheet actual) {
		int firstRow1 = expected.getFirstRowNum();
		int lastRow1 = expected.getLastRowNum() > actual.getLastRowNum()
				? expected.getLastRowNum()
				: actual.getLastRowNum();
		strLogActual = "<table><th>Actual Result</th>";
		strLogExpected = "<table><th>Expected Result</th>";
		strHeader = "<tr style=background-color:Turquoise>";
		tableStyle = "<style>table {border-collapse: collapse;} th, td {border: 1px solid black; height:8px;padding: 2px;text-align: left;}</style>";
		boolean resultFlag = true;

		for (int i = firstRow1; i <= lastRow1; ++i) {
			System.out.print("\nComparing Row " + i + "==>");
			strLogActual = strLogActual + "<tr>";
			strLogExpected = strLogExpected + "<tr>";
			XSSFRow expectedRow = expected.getRow(i);
			XSSFRow actualRow = actual.getRow(i);
			if (i != 0) {
				if (!compareTwoRows(expectedRow, actualRow)) {
					resultFlag = false;
					System.out.print("==>Row " + i + " - NOT EQUAL ⚠");
				} else {
					System.out.print("==>Row " + i + " - Equal");
				}
			} else {
				for (int k = 0; k < expected.getRow(i).getLastCellNum(); ++k) {
					hmTableHeader.put(k, expected.getRow(i).getCell(k).toString());
					if (!Arrays.asList(SkipFieldsVerify).contains(expected.getRow(i).getCell(k).getStringCellValue())) {
						strHeader = strHeader + "<th>" + expected.getRow(i).getCell(k) + "</th>";
					}
				}

				strHeader = strHeader + "</tr>";
				strLogExpected = strLogExpected + strHeader;
				strLogActual = strLogActual + strHeader;
			}

			strLogExpected = strLogExpected + "</tr>";
			strLogActual = strLogActual + "</tr>";
		}

		strLogExpected = strLogExpected + "</table>";
		strLogActual = strLogActual + "</table>";
		if (!resultFlag) {
			//generateHTMLFile(System.getProperty("user.dir") + File.separator + "Stores" + File.separator + "Files"
				//	+ File.separator + "temp" + File.separator +"htmlOutput" + ".html", tableStyle + strLogActual + "<br>" + strLogExpected);
			generateHTMLFile(System.getProperty("user.dir") + File.separator + "TestReport" + File.separator + "ExcelComparsionResults"
					+ File.separator  +"htmlOutput" + ".html", tableStyle + strLogActual + "<br>" + strLogExpected);
		
		} else {
			//generateHTMLFile(System.getProperty("user.dir") + File.separator + "Stores" + File.separator + "Files"
				//	+ File.separator + "temp" + File.separator +"htmlOutput" + ".html", tableStyle + strLogActual);
			generateHTMLFile(System.getProperty("user.dir") + File.separator + "TestReport" + File.separator + "ExcelComparsionResults"
					+ File.separator  +"htmlOutput" + ".html", tableStyle + strLogActual);
		}

		return resultFlag;
	}

	public static boolean compareTwoRows(XSSFRow expectedRow, XSSFRow actualRow) {
		if (expectedRow == null && actualRow == null) {
			return true;
		} else {
			short actualFirstCell;
			short actualLastCell;
			if (expectedRow != null && actualRow != null) {
				actualFirstCell = expectedRow.getFirstCellNum();
				actualLastCell = expectedRow.getLastCellNum();
				boolean equalRows = true;

				for (int i = actualFirstCell; i <= actualLastCell - 1; ++i) {
					intCurrentColumnIndex = i;
					XSSFCell expectedCell = expectedRow.getCell(i);
					XSSFCell actualCell = actualRow.getCell(i);
					if (!Arrays.asList(SkipFieldsVerify).contains(hmTableHeader.get(i))) {
						if (expectedCell.getStringCellValue().toUpperCase().equals("X")) {
							strLogActual = strLogActual + "<td style=background-color:silver>"
									+ actualCell.getStringCellValue() + "</td>";
							strLogExpected = strLogExpected + "<td style=background-color:silver>"
									+ expectedCell.getStringCellValue() + "</td>";
						} else {
							strLogExpected = strLogExpected + "<td>" + expectedCell.getStringCellValue() + "</td>";
							if (!compareTwoCells(expectedCell, actualCell)) {
								equalRows = false;
								strLogActual = strLogActual + "<td style=background-color:red>"
										+ actualCell.getStringCellValue() + "</td>";
								System.out.print("❌" + expectedCell.getStringCellValue() + "!="
										+ actualCell.getStringCellValue() + "|");
							} else {
								strLogActual = strLogActual + "<td >" + actualCell.getStringCellValue() + "</td>";
								System.out.print("✔" + expectedCell.getStringCellValue() + "=="
										+ actualCell.getStringCellValue() + "|");
							}
						}
					}
				}

				return equalRows;
			} else {
				int i;
				XSSFCell actualCell;
				if (expectedRow == null) {
					actualFirstCell = actualRow.getFirstCellNum();
					actualLastCell = actualRow.getLastCellNum();

					for (i = actualFirstCell; i <= actualLastCell - 1; ++i) {
						intCurrentColumnIndex = i;
						actualCell = actualRow.getCell(i);
						strLogExpected = strLogExpected + "<td> </td>";
						strLogActual = strLogActual + "<td style=background-color:red>"
								+ actualCell.getStringCellValue() + "</td>";
						System.out.print(
								"❌" + actualCell.getStringCellValue() + "!=" + actualCell.getStringCellValue() + "|");
					}
				} else {
					actualFirstCell = expectedRow.getFirstCellNum();
					actualLastCell = expectedRow.getLastCellNum();

					for (i = actualFirstCell; i <= actualLastCell - 1; ++i) {
						intCurrentColumnIndex = i;
						actualCell = expectedRow.getCell(i);
						strLogActual = strLogActual + "<td style=background-color:red> </td>";
						strLogExpected = strLogExpected + "<td>" + actualCell.getStringCellValue() + "</td>";
						System.out.print(
								"❌" + actualCell.getStringCellValue() + "!=" + actualCell.getStringCellValue() + "|");
					}
				}

				return false;
			}
		}
	}

	public static boolean compareTwoCells(XSSFCell cell1, XSSFCell cell2) {
		if (cell1 == null && cell2 == null) {
			return true;
		} else if (cell1 != null && cell2 != null) {
			if (cell1.getStringCellValue().isEmpty() && cell2.getStringCellValue().isEmpty()) {
				return true;
			} else if (!cell1.getStringCellValue().isEmpty() && !cell2.getStringCellValue().isEmpty()) {
				boolean equalCells = false;
				Double intCell1;
				Double intCell2;
				switch (cell1.getCellType()) {
					case 0 :
						if (cell1.getNumericCellValue() == cell2.getNumericCellValue()) {
							equalCells = true;
						}
						break;
					case 1 :
					boolean ignorePostDecimalValues = false;
					if (isDateTimeField(cell1.getStringCellValue())
								&& isDateTimeField(cell2.getStringCellValue())) {
							if (convertDate(cell1.getStringCellValue())
									.equals(convertDate(cell2.getStringCellValue()))) {
								equalCells = true;
							}
						} else if (cell1.getStringCellValue().equals(cell2.getStringCellValue())) {
							equalCells = true;
						} else if (ignorePostDecimalValues && cell1.getStringCellValue()
								.substring(0, cell1.getStringCellValue().indexOf(".")).equals(cell2.getStringCellValue()
										.substring(0, cell1.getStringCellValue().indexOf(".")))) {
							equalCells = true;
						} else if (cell1.getStringCellValue().matches("[0-9]*([.]{0,1})[0-9]*")
								&& cell2.getStringCellValue().matches("[0-9]*([.]{0,1})[0-9]*")) {
							intCell1 = Double.parseDouble(cell1.getStringCellValue());
							intCell2 = Double.parseDouble(cell2.getStringCellValue());
							if (Double.compare(intCell1, intCell2) == 0) {
								equalCells = true;
							}
						}
						break;
					case 2 :
						if (cell1.getCellFormula().equals(cell2.getCellFormula())) {
							equalCells = true;
						}
						break;
					case 3 :
						if (cell2.getCellType() == 3) {
							equalCells = true;
						}
						break;
					case 4 :
						if (cell1.getBooleanCellValue() == cell2.getBooleanCellValue()) {
							equalCells = true;
						}
						break;
					case 5 :
						if (cell1.getErrorCellValue() == cell2.getErrorCellValue()) {
							equalCells = true;
						}
						break;
					default :
						if (cell1.getStringCellValue().equals(cell2.getStringCellValue())) {
							equalCells = true;
						} else if (cell1.getStringCellValue().equals(cell2.getStringCellValue())) {
							equalCells = true;
						} else if (cell1.getStringCellValue().matches("[0-9]*([.]{0,1})[0-9]*")
								&& cell2.getStringCellValue().matches("[0-9]*([.]{0,1})[0-9]*")) {
							intCell1 = Double.parseDouble(cell1.getStringCellValue());
							intCell2 = Double.parseDouble(cell2.getStringCellValue());
							if (Double.compare(intCell1, intCell2) == 0) {
								equalCells = true;
							}
						}
				}

				return equalCells;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public static void generateHTMLFile(String fileName, String content) {
		try {
			DataOutputStream dos = new DataOutputStream(new FileOutputStream(fileName, false));
			dos.writeBytes(content);
			dos.close();
		} catch (FileNotFoundException var3) {
			System.out.println("There is an error while creating File");
			var3.printStackTrace();
		} catch (IOException var4) {
			System.out.println("There is an error while writing to File");
			var4.printStackTrace();
		}

	}

	public static String convertDate(String strDate) {
		DateTimeFormatter f = DateTimeFormatter.ofPattern("E MMM dd HH:mm:ss z uuuu").withLocale(Locale.getDefault());
		ZonedDateTime zdt = ZonedDateTime.parse(strDate, f);
		LocalDate ld = zdt.toLocalDate();
		DateTimeFormatter fLocalDate = DateTimeFormatter.ofPattern("dd-MMM-uuuu");
		String output = ld.format(fLocalDate);
		return output;
	}

	public static boolean isDateTimeField(String inDate) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy");
		dateFormat.setLenient(false);

		try {
			dateFormat.parse(inDate.trim());
			return true;
		} catch (ParseException var3) {
			return false;
		}
	}
	
	
}
