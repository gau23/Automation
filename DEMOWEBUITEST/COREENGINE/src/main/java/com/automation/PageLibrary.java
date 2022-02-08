package com.automation;

import java.awt.AWTException;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import io.github.sukgu.Shadow;




public class PageLibrary extends seleniumCore{

	protected WebDriver webdriver;
	protected Shadow shadow ;

	public PageLibrary(WebDriver driver) {
		super(driver);
		this.webdriver = driver;
		shadow = new Shadow(driver);
        shadow.setImplicitWait(2);
	}




	public WebElement finder(By locator) {
		WebElement webElement = null;
		try {
			webElement = driver.findElement(locator);
			
			wait.waitForElementToBeVisible(webElement).isDisplayed();
		} catch (NoSuchElementException e1) {
			logMessage("❌ [ELEMENT NOT FOUND] : You might have to update the locator:-" + locator);
		}catch (StaleElementReferenceException e2){
			logMessage("⚠ [Stale Element Exception!!] : Refinding the element after 5 seconds" + locator);
			holdExecution(5000);
			webElement  = driver.findElement(locator);
		}


		return webElement;
	}



	public List<WebElement> findAll(By locator){
		List<WebElement> webElements = null;

		try {
			webElements = driver.findElements(locator);
			for(WebElement element:webElements)
			{
				logMessage("Elmenets is displayed"+element.getText());
			}
		} catch (NoSuchElementException e) {
			logMessage("❌ [ELEMENT NOT FOUND] : You might have to update the locator:-" + locator);
		}


		return webElements;
	}

	//_____________________________________________________________________________________________
	/*
	 * new wrapper method for find elements inside shadow root. Use CSS selectors only.
	 */

	public WebElement shadowRootFinder(String  cssLocator) {
		WebElement webElement = null;
		try {
			webElement = shadow.findElement(cssLocator);
			//	            ElementGuard.guard(driver,webElement); //not guarding the webelements under shadow root
			wait.waitForElementToBeVisible(webElement).isDisplayed();
		} catch (NoSuchElementException e1) {
			logMessage("❌ [ELEMENT NOT FOUND] : You might have to update the locator:-" + cssLocator.toString());
		}catch (StaleElementReferenceException e2){
			logMessage("⚠ [Stale Element Exception!!] : Refinding the element after 5 seconds" + cssLocator.toString());
			holdExecution(5000);
			webElement = shadow.findElement(cssLocator);
		}


		return webElement;

	}

	public WebElement shadowRootFinder(WebElement parent, String  cssLocator) {
		WebElement webElement = null;
		try {
			webElement = shadow.findElement(parent, cssLocator);
			//	            ElementGuard.guard(driver,webElement); //not guarding the webelements under shadow root
			wait.waitForElementToBeVisible(webElement).isDisplayed();
		} catch (NoSuchElementException e1) {
			logMessage("❌ [ELEMENT NOT FOUND] : You might have to update the locator:-" + cssLocator.toString());
		}catch (StaleElementReferenceException e2){
			logMessage("⚠ [Stale Element Exception!!] : Refinding the element after 5 seconds" + cssLocator.toString());
			holdExecution(5000);
			webElement = shadow.findElement(cssLocator);
		}


		return webElement;

	}

	//_____________________________________________________________________________________________
	/*
	 * new wrapper method for find elements inside shadow root. Use CSS selectors only.
	 */

	public List<WebElement> findAllInShadowRoot(String  cssLocator) {
		List<WebElement> webElements = null;
		try {
			webElements = shadow.findElements(cssLocator);
			for(WebElement element:webElements)
			{
				logMessage("Elmenets is displayed"+element.getText());
			}
		} catch (NoSuchElementException e) {
			logMessage("❌ [ELEMENT NOT FOUND] : You might have to update the locator:-" + cssLocator);
		}


		return webElements;

	}
	public List<WebElement> findAllInShadowRoot(WebElement parent, String  cssLocator) {
		List<WebElement> webElements = null;
		try {
			webElements = shadow.findElements(parent, cssLocator);
			for(WebElement element:webElements)
			{
				logMessage("Elmenets is displayed"+element.getText());
			}
		} catch (NoSuchElementException e) {
			logMessage("❌ [ELEMENT NOT FOUND] : You might have to update the locator:-" + cssLocator);
		}


		return webElements;

	}

	public List<WebElement> isElementsCheckedAndEnabled(By locator) {
		List<WebElement> elementName =driver.findElements(locator);
		try
		{
			for(WebElement element:elementName)
			{
				logMessage("Element is Enabled"+element.isEnabled() + " Element Defination:="+element.getText());

			}
		}catch (NoSuchElementException e) {
			logMessage("❌ [ELEMENT NOT FOUND] : You might have to update the locator:-" + locator);
		}

		return elementName;

	}
	public WebElement findFreshElement(By locator){ // To handle stale Element reference exception
		WebElement webElement = null;
		int attempts =0;
		while(attempts < 10){
			try {
				wait.hardWait(2);
				webElement = driver.findElement(locator);
				webElement.isDisplayed();
				break;
			} catch (StaleElementReferenceException e) {
				logMessage("⚠ Stale Element Reference Exception ... Refinding element after 2 seconds.. ");
				attempts+=1;
			}catch(NoSuchElementException e){
				logMessage("❌ [ELEMENT NOT FOUND] : You might have to update the locator:-" + locator);
				attempts+=1;     
			}
		}
		return webElement;

	}
	//__________________________________________________________________________________________

	/*
	 * wrapper method for send/get text operation
	 */

	public void verifyElementText(By locator, String expectedText) {

		WebElement elementName = findFreshElement(locator);

		wait.waitForElementToBeVisible(elementName);  
		logMessage("Expected text: "+expectedText);
		logMessage("Actual text: "+elementName.getText());
		Assert.assertEquals( elementName.getText().trim(), expectedText,"❌ ASSERT FAILED: element '" + elementName
				+ "' Text is not as expected: ");
		logMessage("✔ ASSERT PASSED: element " + elementName
				+ " is visible and Text is " + expectedText);
	}

	//added by sami 
	public String getTextOfElement(By locator)
	{
		WebElement elementName = findFreshElement(locator);
		String text=elementName.getText();
		return text;
	}

	public void verifyElementValue(By locator, String expectedValue){
		WebElement elementName = findFreshElement(locator);

		wait.waitForElementToBeVisible(elementName);  
		String actualValue = elementName.getAttribute("value");
		logMessage("Expected value: "+expectedValue);
		logMessage("Actual value: "+actualValue);
		Assert.assertEquals(actualValue , expectedValue,"❌ ASSERT FAILED: element '" + elementName.getText()
		+ "' Text is not as expected: ");
		logMessage("✔ ASSERT PASSED: element " + elementName.getText()
		+ " is visible and Text is " + expectedValue);
	}



	public void verifyElementTextContains(By locator ,
			String expectedText) {
		WebElement elementName = findFreshElement(locator);
		String actualText = elementName.getText();
		wait.waitForElementToBeVisible(elementName);
		wait.waitForElementToContainsText(elementName, expectedText);
		Assert.assertTrue(actualText
				.trim().toUpperCase().contains(expectedText.toUpperCase()));
		logMessage("✔ ASSERT PASSED: element " + elementName.getClass()
		+ " is visible and Text is " + expectedText);
	}

	public void softAssert(String actualText,String ExpectedText){
		SoftAssert sAssert = new SoftAssert();
		sAssert.assertEquals(actualText,ExpectedText);
	}

	public void enterText(By locator, String text) {

		if(text == null){
			logMessage("(!) Due to empty text , Skipping selection for locator" + locator.toString() );
			return;
		}
		WebElement element = finder(locator);
		wait.waitForElementToBeClickable(element);
		//	      clickUsingActionScript(element);

		element.clear();
		holdExecution(3000);
		element.sendKeys(text);
		logMessage("Entered Text " + text + "  in the "+element.getAttribute("id")+" element");

	}

	// Added overload method to handle to send actual keyboard keys
	public void enterText(By locator, Keys key) {
		WebElement element = finder(locator);
		wait.waitForElementToBeClickable(element);
		//		      clickUsingActionScript(element);

		element.sendKeys(key);
		logMessage("Used key " + key + "  in the "+element.getAttribute("id")+" element");

	}




	//___________________________________________________________________________________________
	/*
	 * Wrapper methods for click operation
	 * 
	 */
	public boolean clickElement(By locator){

		WebElement element = finder(locator);
		try {
			wait.waitForElementToBeVisible(element);
			wait.waitForElementToBeClickable(element);
			//scrollDown(element);
			element.click();
			logMessage("✔ Clicked Element " + element
					+ " Successfully!");
		} catch (StaleElementReferenceException ex1) {
			holdExecution(4000);
			element = findFreshElement(locator);
			element.click();
			logMessage("⚠ Clicked Element " + element
					+ " after catching Stale Element Exception");
		} catch (WebDriverException ex3) {
			wait.waitForElementToBeClickable(element);
			holdExecution(4000);
			performClickByActionBuilder(locator);
			logMessage("⚠ Clicked Element " + element
					+ " after catching WebDriver Exception");
		}
		return true;
	}

	public void clickAndHold(WebElement element) {
		Actions act = new Actions(driver);
		act.clickAndHold(element).build().perform();
	}

	public void performClickByActionBuilder(By locator) {
		WebElement element   = finder(locator);
		Actions builder = new Actions(driver);
		builder.moveToElement(element).build().perform();
		builder.moveToElement(element).click().perform();
	}

	public void clickUsingActionScript(WebElement element) {
		Actions actions = new Actions(driver);
		actions.moveToElement(element).click().perform();
	}

	public void clickLinkedLinks(String... links){
		for(String link : links){
			clickElement(By.linkText(link));
			holdExecution(1000);
		}
	}

	/*
	 * Used for focus on element before performing any action.
	 * Will be useful if element is not activated/focused
	 */
	public void performFocusOnElementByActionBuilder(By locator) {
		WebElement element   = finder(locator);
		Actions builder = new Actions(driver);
		builder.moveToElement(element).perform();
	}  
	//___________________________________________________________________________________________
	/*
	 * Wrapper methods for handling iframes and windows switching
	 * 
	 */
	public void switchToNestedFrames(WebElement... frameNames) {
		switchToDefaultContent();
		for (WebElement token : frameNames) {
			wait.hardWait(1);
			wait.waitForElementToBeVisible(token);
			driver.switchTo().frame(token);
		}

	}

	public void switchToFrame(WebElement element) {
		// switchToDefaultContent();
		wait.waitForElementToBeVisible(element);

		driver.switchTo().frame(element);
	}

	public void switchToFrame(int i) {
		driver.switchTo().frame(i);
	}

	public void switchToFrame(String id) {
		driver.switchTo().frame(id);
	}

	public void switchToDefaultContent() {
		driver.switchTo().defaultContent();
	}


	public void switchWindow() {
		holdExecution(4000);
		for (String current : driver.getWindowHandles()) {
			driver.switchTo().window(current);
			System.out.println("Switched to window titled =>" +driver.getTitle());
		}
	}

	public void switchWindow(String name){
		wait.waitForWindowToBeActiveAndSwitchToIt(2);
		System.out.println(driver.getWindowHandles().size());

		for (String current : driver.getWindowHandles()) {
			if(driver.getTitle().equals(name)){
				System.out.println("Switched to window titled =>" +driver.getTitle());
				break;
			}
			holdExecution(3000);
			System.out.println("Switching to window handle:"+ current + "\n title: "+ driver.getTitle());
			driver.switchTo().window(current);
		}
	}






	public void changeWindow(int i) {
		Set<String> windows = driver.getWindowHandles();
		if (i > 0) {
			for (int j = 0; j < 5; j++) {
				if (windows.size() < 2) {
					holdExecution(2000);
				} else {
					break;
				}
			}
			windows = driver.getWindowHandles();
		}
		String wins[] = windows.toArray(new String[windows.size()]);

		driver.switchTo().window(wins[i]);

		while (driver.getTitle().equalsIgnoreCase("about:blank")
				|| driver.getTitle().equalsIgnoreCase("")) {
			holdExecution(2000);
		}
		driver.manage().window().maximize();
	}

	//___________________________________________________________________________________________
	/*
	 * Wrapper methods for verifications and assertions
	 * 
	 */

	public void verifyPageDOMContains(String text){
		String DOM = finder(By.xpath("//*")).getText();
		if(DOM.contains(text)){
			logMessage("✔  The webpage contains the text value :"+text);
		}else{
			logMessage("⚠ The webpage does not contains the text value:"+ text);
		}
	}



	public boolean isElementDisplayed(By locator)
			throws NoSuchElementException {
		WebElement elementName = finder(locator);
		wait.waitForPageToLoadCompletely();
		elementName  = wait.waitForElementToBeVisible(elementName);

		boolean result = elementName.isDisplayed();
		Assert.assertTrue(result);
		logMessage("✔ ASSERT PASSED: element " + elementName.getTagName()+ " is displayed.");
		return result;
	}


	public void isElementNotDisplayed(WebElement elementName) {
		wait.resetImplicitTimeout(5);
		Boolean status = false;
		try {
			status = elementName.isDisplayed();
			Assert.assertFalse(status);
			logMessage("✔ Assertion Passed : Element " + elementName + " is not displayed ");
		} catch (NoSuchElementException e) {
			logMessage("✔ Assertion Passed : Element " + elementName + " is not displayed ");
		} finally {
			wait.resetImplicitTimeout(wait.timeout);
		}
	}

	public boolean isElementEnabled(WebElement elementName, boolean expected) {
		wait.waitForElementToBeVisible(elementName);
		boolean result = expected && elementName.isEnabled();
		Assert.assertTrue(result);
		logMessage("✔ ASSERT PASSED: element " + elementName + " is enabled :- "
				+ expected);
		return result;
	}

	public boolean isElementPresent(By locator){
		wait.resetImplicitTimeout(5);
		Boolean status = false;
		try {
			driver.findElement(locator);
			status = true;
			logSuccessMessage("[ELEMENT PRESENT] :" + locator);              
			return status;             
		} catch (NoSuchElementException e1) {
			logMessage("⚠ [ELEMENT NOT PRESENT] :" + locator);
			status = false;
			return status;
		}
	}

	/*
	 * Wrapper methods for check element checked or not
	 * 
	 */
	public boolean isElementChecked(By locator) throws NoSuchElementException {
		WebElement elementName = finder(locator);
		wait.waitForPageToLoadCompletely();
		elementName = wait.waitForElementToBeVisible(elementName);

		boolean result = elementName.isSelected();
		logMessage("✔ ASSERT Check: element " + elementName.getTagName() + " is checked.");
		return result;
	}


	//___________________________________________________________________________________________
	/*
	 * Wrapper methods for hover actions
	 * 
	 */

	public void hover(By locator) {
		WebElement element = finder(locator);
		Actions hoverOver = new Actions(driver);
		hoverOver.moveToElement(element).build().perform();
	}



	public void hoverClick(By locator) {
		WebElement element = finder(locator);
		Actions hoverClick = new Actions(driver);
		hoverClick.moveToElement(element).click().build().perform();
		logMessage("✔ Clicked Element " + element
				+ " After hovering Successfully!");
	}


	public void hoverusingjavascript(WebElement e) {
		String javascript = "var evObj = document.createEvent('MouseEvents');"
				+ "evObj.initMouseEvent(\"mouseover\",true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);"
				+ "arguments[0].dispatchEvent(evObj)";
		((JavascriptExecutor) driver).executeScript(javascript, e);
	}

	public void hoverOutusingjavascript(WebElement e) {
		String javascript = "var evObj = document.createEvent('MouseEvents');"
				+ "evObj.initMouseEvent(\"mouseout\",true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);"
				+ "arguments[0].dispatchEvent(evObj)";
		((JavascriptExecutor) driver).executeScript(javascript, e);
	}




	public void scrollDown(WebElement element) {
		((JavascriptExecutor) driver).executeScript(
				"arguments[0].scrollIntoView(true);", element);
	}


	public void scrollPageToTheBottom() {
		((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
	}

	//___________________________________________________________________________________________
	/*
	 * Wrapper methods for drop down type selection elements
	 * 
	 */

	//Created By Reetika Maheshwari

	public List<String> returnAllDropDownValues(By locator) {
		List<String> optionValues = null;

		WebElement el;
		try {
			el = finder(locator);
			wait.waitForElementToBeVisible(el);
			Select sel = new Select(el);
			List<WebElement> options = sel.getOptions();
			System.out.println("Total Values in Dropdown are:" +options.size());
			optionValues=new ArrayList<String>(options.size()); 
			// Loop to add values to List
			for (int j = 0; j < options.size(); j++) {
				optionValues.add(options.get(j).getText());
			}

			logMessage(" Total Values in Dropdown are: " + options.size());
		} catch (StaleElementReferenceException ex1) {
			el = finder(locator);
			Select sel = new Select(el);
			List<WebElement> options = sel.getOptions();
			System.out.println("Total Values in Dropdown are:" +options.size());
			optionValues=new ArrayList<String>(options.size()); 
			// Loop to add values to List
			for (int j = 0; j < options.size(); j++) {
				optionValues.add(options.get(j).getText());
			}
		} catch (Exception ex2) {
			logMessage("List is empty "
					+ ex2.getMessage());
		}
		return optionValues;
	}




	public void selectDropDownValue(WebElement el, int index) {

		try {
			wait.waitForElementToBeVisible(el);
			scrollDown(el);
			Select sel = new Select(el);
			sel.selectByIndex(index);
			logMessage("✔ User select " + index + " value from dropdown");
		} catch (StaleElementReferenceException ex1) {
			// wait.waitForElementToBeVisible(el);
			// scrollDown(el);
			Select sel = new Select(el);
			sel.selectByIndex(index);
			logMessage("select Element " + el
					+ " after catching Stale Element Exception");
		} catch (Exception ex2) {
			logMessage("Element " + el + " could not be clicked! "
					+ ex2.getMessage());
		}
	}

	public void selectProvidedTextFromDropDown(By locator, String text) {
		WebElement el;
		if(text.isEmpty()){
			logMessage("(!) Due to empty text , Skipping selection for locator" + locator.toString() );
			return;
		}
		try {
			el = finder(locator);
			wait.waitForElementToBeVisible(el);
			scrollDown(el);
			Select sel = new Select(el);
			sel.selectByVisibleText(text);
			logMessage("✔ User select " + text + " value from dropdown");
		} catch (StaleElementReferenceException ex1) {
			el = finder(locator);
			Select sel = new Select(el);
			sel.selectByVisibleText(text);
			logMessage("select Element " + el
					+ " after catching Stale Element Exception");
		} catch (Exception ex2) {
			logMessage("❌ Element " + locator.toString() + " could not be clicked! "
					+ ex2.getMessage());
		}
	}

	public String getSelectedValueFromDropDown(WebElement el) {
		Select sel = new Select(el);
		return sel.getFirstSelectedOption().getText();
	}

	public void verifySelectedValueInDropDown(By locator,String expectedValue){
		Select sel = new Select(finder(locator));
		String actualValue  = sel.getFirstSelectedOption().getText();
		logMessage("Expected value: "+expectedValue);
		logMessage("Actual value: "+actualValue);
		Assert.assertEquals(actualValue , expectedValue,"❌ ASSERT FAILED: element '" + sel.toString()
		+ "' Text is not as expected: ");
		logMessage("✔ ASSERT PASSED: element " + sel.toString()
		+ " is visible and Text is " + expectedValue);
	}
	//___________________________________________________________________________________________
	/*
	 * Wrapper methods for handling page and URL operations
	 * 
	 */

	public String getCurrentURL() {
		return driver.getCurrentUrl();
	}

	public String getPageTitle() {
		return driver.getTitle();
	}



	public void pageRefresh() {
		driver.navigate().refresh();
	}

	public void navigateToBackPage() {
		driver.navigate().back();
		logMessage("Step : navigate to back page\n");
	}

	public void navigateToUrl(String URL) {
		driver.navigate().to(URL);
		logMessage("STEP : Navigate to URL :- " + URL);
	}

	public void closeWindow() {
		driver.close();
	}

	public void verifyPageTitleExact(String expectedPagetitle) {
		String actualPageTitle;
		actualPageTitle = getCurrentURL();

		try {
			wait.waitForPageTitleToBeExact(expectedPagetitle);
			logMessage("✔ ASSERT PASSED: PageTitle for " + actualPageTitle
					+ " is exactly: '" + expectedPagetitle + "'");
		} catch (TimeoutException ex) {
			logMessage("Could not verify page title exactly");
			Assert.fail("❌ ASSERT FAILED: PageTitle for " + actualPageTitle
					+ " is not exactly: '" + expectedPagetitle
					+ "'!!!\n instead it is :- " + driver.getTitle());
		}
	}

	public void verifyPageTitleContains(String expectedPagetitle) {
		wait.waitForPageTitleToContain(expectedPagetitle);
		String actualPageTitle = getPageTitle().trim();
		logMessage("✔ ASSERT PASSED: PageTitle for " + actualPageTitle
				+ " contains: '" + expectedPagetitle + "'.");
	}

	public WebElement getElementByIndex(List<WebElement> elementlist,
			int index) {
		return elementlist.get(index);
	}

	public void deleteAllCookies() {
		driver.manage().deleteAllCookies();
	}


	public String getDate()
	{
		SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yy");
		SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yy");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());

		System.out.println(sdf1.format(calendar.getTime()));
		calendar.add(Calendar.DATE,6);
		System.out.println(sdf2.format(calendar.getTime()));
		return sdf2.format(calendar.getTime()).toString();
	}

	//Changes done by a2dubey to enter value after clearing the text through Action builder
	public void sendKeysByActionBuilder(By locator, String Keys) {

		WebElement element   = finder(locator);
		Actions builder = new Actions(driver);
		builder.sendKeys(element, Keys).perform();
	}

	//Added as sendKeys not working properly
	public void clearAndTypeActionBuilder(By locator, String Text){
		WebElement element = finder(locator);
		Actions builder = new Actions(driver);
		holdExecution(2000);
		builder.sendKeys(Keys.chord(Keys.CONTROL,"a"),Keys.DELETE).build().perform();
		holdExecution(1000);
		builder.sendKeys(element, Text).build().perform();
	} 

	//Rafal: Use if method based on actions interactions has problems with element recognition
	public void sendKeysByWebElement(By locator, String Keys) {

		System.out.println("Inputted keys: " + Keys);
		WebElement element   = finder(locator);
		element.sendKeys(Keys);
	}

	public void clearTypeTextInEditBox(By locator, String TextToEnter) {
		WebElement element   = finder(locator);
		element.sendKeys(Keys.chord(Keys.CONTROL, "a"),TextToEnter);
		holdExecution(3000);
		/*element.sendKeys(Keys.DELETE);
	 		   holdExecution(5000);
	 		   //element.sendKeys(Keys.DELETE);
	 		   //holdExecution(3000);				//For Jenkins Stabalization
	 		   element.sendKeys(TextToEnter);
	 		   holdExecution(3000);*/
		logMessage("Entered Text " + TextToEnter + "  in the "+element.getAttribute("id")+" element");

	}

	public void clearAndTypeTextInEditBox(By locator, String TextToEnter) {
		WebElement element   = finder(locator);
		element.sendKeys(Keys.chord(Keys.CONTROL, "a"));
		holdExecution(3000);
		element.sendKeys(Keys.DELETE);
		holdExecution(5000);
		//element.sendKeys(Keys.DELETE);
		//holdExecution(3000);				//For Jenkins Stabalization
		element.sendKeys(TextToEnter);
		holdExecution(3000);
		logMessage("Entered Text " + TextToEnter + "  in the "+element.getAttribute("id")+" element");

	}
	//added by sami
	public void OpenParentTab() {

		String oldTab = driver.getWindowHandle();
		ArrayList<String> newTab = new ArrayList<String>(driver.getWindowHandles());
		newTab.remove(oldTab);
		// change focus to new tab
		driver.switchTo().window(newTab.get(0));
		driver.close();
		// change focus back to old tab
		driver.switchTo().window(oldTab);

	}
	//added by sami for Downloading a PDF file from Browser Tab
	public void DownloadPdf() throws AWTException{
		java.awt.Robot robot=new java.awt.Robot();
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_S);
		robot.keyRelease(KeyEvent.VK_S);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		holdExecution(5000);
		robot.keyPress(KeyEvent.VK_ENTER);
		holdExecution(5000); 
		robot.keyPress(KeyEvent.VK_TAB);   // file replace move to yes button
		holdExecution(5000);
		robot.keyPress(KeyEvent.VK_ENTER);
	}

	//Added Haroon
	//Modified to handle TimeoutException on 26/04/2019-Anup
	public void waitForObject( final By locator, int seconds)  {

		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(seconds, TimeUnit.SECONDS)
				.pollingEvery(2, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class);
		System.out.println("Waiting for element");

		try {
			WebElement element = wait.until(new Function<WebDriver, WebElement>(){
				public WebElement apply(WebDriver driver) {
					WebElement element = driver.findElement(locator);
					return element;

				}
			});
		}
		catch(TimeoutException timeout) {
			logMessage("Object not available, skipping because of TimeoutException and object not required..");
		}
	}






}
