package com.automation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Calendar;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.testng.Assert;
import org.testng.Reporter;

public class PDFHandler {
	
	public String readPDFDocumentFromOutputExpectedData( String Filename) {
		String readFilePath = "";
			readFilePath = System.getProperty("user.dir") + File.separator+"Stores"+File.separator+"Files"+File.separator+"OutputExpectedData";	
		File pdffile = new File(readFilePath);
		// File pdffile = new File("D:/BulkSwitch.pdf");
		String text = "";
		try {
			// Parse the PDF. Unrestricted main memory will be used for buffering PDF
			// streams.
			PDDocument pd = PDDocument.load(pdffile);
			System.out.println("Number of pages in the document: " + pd.getNumberOfPages());
			PDFTextStripper stripper = new PDFTextStripper();
			text = stripper.getText(pd).trim();
			// System.out.println(text);
			pd.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return text;
	}
	
	public String readPDFDocumentFromCustomLocation(String path, String Filename) {
		File pdffile = new File(path+File.separator+Filename+".pdf");
		String text = "";
		try {
			// Parse the PDF. Unrestricted main memory will be used for buffering PDF
			// streams.
			PDDocument pd = PDDocument.load(pdffile);
			System.out.println("Number of pages in the document: " + pd.getNumberOfPages());
			PDFTextStripper stripper = new PDFTextStripper();
			text = stripper.getText(pd).trim();
			// System.out.println(text);
			pd.close();
			
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return text;
	}
	
	public boolean verifyPDFDownloaded(String initials) {
		String fileName = null;
		boolean bName = false;
		Calendar c = Calendar.getInstance();
		
		int iCount = 0;
		File dir = new File(System.getProperty("user.dir")+File.separator +"target");
		File[] files = dir.listFiles();
		for (File f : files) {

			fileName = f.getName();
			if (fileName.startsWith(initials) && fileName.endsWith(".pdf")) {
				iCount++;
				Reporter.log("Found file :" + fileName);
				bName = true;
			}
		}
		System.out.println("File Count In Folder ::" + iCount);
		Assert.assertTrue(iCount > 0);
		return bName;
		
	}
}
