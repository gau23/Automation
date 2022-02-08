package com.automation;

import static com.automation.ConfigPropertyReader.*;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

public class browserFactory {
	private static String browser;
	private static final DesiredCapabilities capabilities = new DesiredCapabilities();

	String environment = System.getProperty( "env",getProperty( System.getProperty("user.dir")+File.separator+"Config.properties","env"));
	private static String chromeDriverPath ;

	public browserFactory() {
		if (environment.equalsIgnoreCase("local")) {
			if (System.getProperty("os.name").toLowerCase().contains("windows")) {
				chromeDriverPath = System.getProperty("user.dir")+File.separator+"src"+File.separator+"main"+File.separator+"resources"+File.separator+"Drivers"+File.separator+"Chromdriver"+File.separator+"chromedriver.exe";
			}
		}
	}
	
	public WebDriver getDriver(String browserName) {
		browser = browserName;
		System.out.println("Test Browser is :" + browser);
		if (browser.equalsIgnoreCase("chrome")) {
			return getChromeDriver(chromeDriverPath);
		}
		return null;
}

	private static WebDriver getChromeDriver(String driverpath) {
		System.setProperty("webdriver.chrome.driver", driverpath);
		Map<String, Object> prefs = new HashMap<String, Object>();
		prefs.put("download.default_directory", System.getProperty("user.dir") + File.separator + "target");
		ChromeOptions options = new ChromeOptions();

		options.addArguments("--disable-extensions");
		options.addArguments("test-type");
		options.addArguments("--no-sandbox");
		options.addArguments("--disable-dev-shm-usage");
		options.setExperimentalOption("prefs", prefs);
		return new ChromeDriver(options);
	}
}
