package com.test.PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class newCustomerPageObjects {
	protected WebDriver driver;
	
	public By customerName_TextField = By.name("name");
	public By maleGender_RadioButton = By.xpath("//input[@value='m']");
	public By femaleGender_RadioButton = By.xpath("//input[@value='f']");
	public By DOB_TextField = By.name("dob");
	public By address_TextField = By.name("addr");
	public By city_TextField = By.name("city");
	public By state_TextField = By.name("state");
	public By PINNo_TextField = By.name("pinno");
	public By mobileNumber_TextField = By.name("telephoneno");
	public By emailid_TextField = By.name("emailid");
	public By password_TextField = By.name("password");
	public By submit_Button = By.xpath("//input[@type='submit']");
	
}
