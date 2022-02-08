package com.automation;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;

public class webDriverCreator {
	protected  WebDriver driver;
	private final browserFactory wdfactory;
	static String browser;


	public PageLibrary page;

	public WebDriver getDriver() {
		return this.driver;
	}

	public webDriverCreator(String browserName) {
		wdfactory = new browserFactory();
		browser = browserName;
		testInitiator(browserName);
	}

	private void testInitiator(String browserName) {  //---<<---<<---The manifestation method 
		_configureBrowser(browserName);
		_initPage();

	}

	private void _configureBrowser(String browserName) {
		driver = wdfactory.getDriver(browserName);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Integer.parseInt("60"),TimeUnit.SECONDS);

	}

	private void _initPage() {

		page = new PageLibrary(driver);

	}
	
	public void launchApplication(String baseurl) {
		driver.manage().deleteAllCookies();
		System.out.println("inside Browser details");
		driver.get(baseurl);
		System.out.println("\nThe application url is :- " + baseurl);
		//handleSSLCertificateCondition();
	}

	public void closeBrowserSession() {
		driver.quit();
		
	}
}
