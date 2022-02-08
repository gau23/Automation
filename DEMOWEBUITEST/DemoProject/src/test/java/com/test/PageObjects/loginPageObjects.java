package com.test.PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class loginPageObjects {
	
	protected WebDriver driver;
	
	public By userName_TextField = By.name("uid");
	public By password_TextField = By.name("password");
	public By login_Button = By.name("btnLogin");
	public By logInGreeting = By.xpath("//marquee");

}
