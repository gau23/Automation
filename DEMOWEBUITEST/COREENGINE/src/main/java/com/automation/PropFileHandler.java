package com.automation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.Properties;



public class PropFileHandler {
	
	
	Properties properties;
	File file;
	String folderName;
	
	String filePath = System.getProperty("user.dir")+File.separator+"src"+File.separator+"test"+File.separator+
			"resources"+File.separator+"PropFiles"+File.separator+"test"+File.separator;
	
	
	public PropFileHandler(String fileName){
		properties = new Properties();
		System.out.println(fileName);
		String[] names = fileName.split("\\.+");
		System.out.println(names.length);
		fileName = names[names.length-1];
		folderName = names[names.length-2];
		filePath = filePath+ folderName;
		file = new File(filePath);
		if(!file.exists())
			file.mkdir();
		filePath = filePath+File.separator+fileName+".properties";
		file = new File(filePath);
		if(file.exists())
			file.delete();
		try {
			file.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * This method is used to read the value of the given property from the properties file.
	 * 
	 * @param property : the property whose value is to be fetched.
	 * @return the value of the given property.
	 */

	
	
	public String readProperty(String property) {
		InputStream inPropFile = null;
		try {
			
			inPropFile = new FileInputStream(filePath);
			properties.load(inPropFile);
		} catch (IOException e) {
		}
		String value=properties.getProperty(property);	
		
		return value;
	}

/**
 * This method is used to write the value of the property in property file.
 * @param property
 * @param value
 * @throws IOException
 */
	public void writeToFile(String property, String value)  {
		try {
			InputStream inPropFile = new FileInputStream(filePath);
			properties.load(inPropFile);
			inPropFile.close();
			OutputStream outPropFile = new FileOutputStream(filePath);
			properties.setProperty(property, value);
			properties.store(outPropFile, null);
			outPropFile.close();
		} catch (IOException e) {
			System.out.println("Unable to write value in property file");
			e.printStackTrace();
		}
	}
	
	public void writeToFile(Map<String, String> storedData)  {
		try {
			System.out.println(filePath);
			InputStream inPropFile = new FileInputStream(filePath);
			properties.load(inPropFile);
			inPropFile.close();
			OutputStream outPropFile = new FileOutputStream(filePath);
			properties.putAll(storedData);
			properties.store(outPropFile, null);
			outPropFile.close();
		} catch (Exception e) {
			System.out.println("Unable to write value in property file");
			e.printStackTrace();
		}
	}
	
}
