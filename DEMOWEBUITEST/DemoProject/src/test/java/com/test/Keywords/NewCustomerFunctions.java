package com.test.Keywords;

import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import com.automation.webDriverCreator;
import com.test.PageObjects.homePageObjects;
import com.test.PageObjects.newCustomerPageObjects;


public class NewCustomerFunctions extends CommonFunctions {
	webDriverCreator driver;

	homePageObjects homePage = new homePageObjects();
	newCustomerPageObjects newCustomerPage= new newCustomerPageObjects();
	
	public NewCustomerFunctions(webDriverCreator driver) {
		super(driver);
		this.driver = driver;

	}
	
	public void AddNewCustomer() throws InterruptedException {
		driver.page.clickElement(homePage.newCustomer_Button);
		driver.page.enterText(newCustomerPage.customerName_TextField, "Gaurav");
		driver.page.clickElement(newCustomerPage.maleGender_RadioButton);
		driver.page.enterText(newCustomerPage.maleGender_RadioButton, Keys.TAB);
		driver.page.enterText(newCustomerPage.DOB_TextField,"12-02-2010");
		Thread.sleep(2000);
		driver.page.enterText(newCustomerPage.address_TextField,"XYZ");
		driver.page.enterText(newCustomerPage.city_TextField,"XYZ");
		driver.page.enterText(newCustomerPage.state_TextField,"XYZ");
		driver.page.enterText(newCustomerPage.PINNo_TextField,"110009");
		driver.page.enterText(newCustomerPage.mobileNumber_TextField,"1234567890");
		driver.page.enterText(newCustomerPage.emailid_TextField,"XY3Z@gmail.com");
		driver.page.enterText(newCustomerPage.password_TextField,"XYZgmail");
		driver.page.clickElement(newCustomerPage.submit_Button);
		Thread.sleep(5000);
		
		
		
		
		
		
	}
}
	
