package com.automation;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SeleniumWait {
	WebDriver driver;
    WebDriverWait wait;
    
    public int timeout;
    
    public SeleniumWait(WebDriver driver, int timeout) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, timeout);
        this.timeout = timeout;
        PageFactory.initElements(driver, this);
    }

    /**
     * Returns webElement found by the locator if element is visible
     *
     * @param locator
     * @return
     */
    public WebElement getWhenVisible(By locator) {
        WebElement element;
        element = (WebElement) wait.until(ExpectedConditions
                .visibilityOfElementLocated(locator));
        return element;
    }
    
    public WebElement getWhenClickable(By locator) {
        WebElement element;
        element = (WebElement) wait.until(ExpectedConditions.elementToBeClickable(locator));
        return element;
    }
    
    public boolean waitForPageTitleToBeExact(String expectedPagetitle) {
    
        return wait.until(ExpectedConditions.titleIs(expectedPagetitle)) != null;
    }
    
	public boolean waitForElementToContainsText(WebElement element, String text) {
        return wait.until(ExpectedConditions.textToBePresentInElement(element, text));
    }
    
    public boolean waitForPageTitleToContain(String expectedPagetitle) {
        return wait.until(ExpectedConditions.titleContains(expectedPagetitle)) != null;
    }
    
    public void waitforElementToBeRefresehed(By locator){
    	wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOfElementLocated(locator)));
    }
    
    public WebElement waitForElementToBeVisible(WebElement element) {
        return (WebElement) wait.until(ExpectedConditions.visibilityOf(element));
    }
    
    public void waitForFrameToBeAvailableAndSwitchToIt(By locator) {
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(locator));
    }
    
    public List<WebElement> waitForElementsToBeVisible(List<WebElement> elements) {
        return (List<WebElement>) wait.until(ExpectedConditions.visibilityOfAllElements(elements));
    }
    
    public boolean waitForElementToBeInVisible(By locator) {
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator)) != null;
    }
    
    public WebElement waitForElementToBeClickable(WebElement element) {
        return (WebElement) wait.until(ExpectedConditions.elementToBeClickable(element));
    }
    
    public boolean waitForWindowToBeActiveAndSwitchToIt(int number){
    	return wait.until(ExpectedConditions.numberOfWindowsToBe(2));
    }
    
    public void clickWhenReady(By locator) {
        WebElement element = (WebElement) wait.until(ExpectedConditions
                .elementToBeClickable(locator));
        element.click();
    }
    
    public void waitUntilElementValueChanged(By locator, String attributeValue){
    	wait.until(ExpectedConditions.textToBePresentInElementValue(locator, attributeValue));
    }


    public void waitForSpinnerToDisappear() {
        int i = 0;
        resetImplicitTimeout(1);
        try {
            while (driver.findElement(By.cssSelector("#searchlist img[src='./Content/images/loading.gif']")).isDisplayed() && i <= timeout) {
                hardWait(1);                
                i++;
            }
        } catch (Exception e) {
        }
        resetImplicitTimeout(timeout);        
    }
    
    @FindBy(css="#load_enrollLead i")
    WebElement spinner;
    
    public void waitForSpinnerToDisappearInAdmissionGrid(){
    	 int i = 0;
         resetImplicitTimeout(1);
         try {
             while (spinner.isDisplayed() && i <= timeout) {
                 hardWait(1);                
                 i++;
             }
         } catch (Exception e) {
         }
         resetImplicitTimeout(timeout); 
    }
    
    @FindBy(css="#load_enrollMessage")
    WebElement loader;
    
    public void waitforLoadingMessageToDisappaearInAdmissionGrid(){
    	//#load_enrollMessage
    	
    	 int i = 0;
        resetImplicitTimeout(1);
        try {
            while (loader.isDisplayed() && i <= timeout) {
                hardWait(1);                
                i++;
            }
        } catch (Exception e) {
        }
        resetImplicitTimeout(timeout); 
    }
   
    @FindBy(css="#load_notificationMessage i")
    WebElement notificationSpinner;
    public void waitForSpinnerToDisappearInNotificationGrid(){
    	 int i = 0;
         resetImplicitTimeout(1);
         try {
             while (notificationSpinner.isDisplayed() && i <= timeout) {
                 hardWait(1);                
                 i++;
             }
         } catch (Exception e) {
         }
         resetImplicitTimeout(timeout); 
    }
    
    //#lui_evaluator
    
    @FindBy(css="#load_notificationMessage i")
    WebElement tepLoader;
    public void waitforSpinnerToDisappearOnTEP(){
    	 int i = 0;
         resetImplicitTimeout(1);
         try {
             while (tepLoader.isDisplayed() && i <= timeout) {
                 hardWait(1);                
                 i++;
             }
         } catch (Exception e) {
         }
         resetImplicitTimeout(timeout); 
    }
    
    public void waitForElementToDisappear(WebElement element) {
        int i = 0;
        resetImplicitTimeout(2);
        try {
            while (element.isDisplayed() && i <= timeout) {
                hardWait(1);                
                i++;
            }
        } catch (Exception e) {
        }
        resetImplicitTimeout(timeout);        
    }
    
    public void resetImplicitTimeout(int newTimeOut) {
        try {
            driver.manage().timeouts().implicitlyWait(newTimeOut, TimeUnit.SECONDS);
        } catch (Exception e) {	
        }
    }
    
    public void resetExplicitTimeout(int newTimeOut) {
        try {
            this.wait = new WebDriverWait(driver,newTimeOut);
        } catch (Exception e) {	
        }
    }

    // TODO Implement Wait for page load for page synchronizations
    public void waitForPageToLoadCompletely() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By
                .cssSelector("*")));
    }

    public void hardWait(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
    
    public void waitForJavaScriptToLoadCompletely(){
    	int i = 0;
    	System.out.println("In script loading.....function");
    	while(i < 60){
    		String status = (String) ((JavascriptExecutor) driver).executeScript("return document.readyState");
    		//System.out.println("Script satus is " +status);
    		if(status.equalsIgnoreCase("complete")) break;
    		else i++;
    		//System.out.println("WAITING FOR PAGE TO LOAD COMLETLYT "+i);
    	}
    }
    
    public void waitForElementToBeDisabled(WebElement element){
    	 int count = 10;
    	 while (count > 0 && element.isEnabled()){
    		 System.out.println("Waiting for element to get disabled");
    		 hardWait(2);
    		 count--;
    	 }
    }

    // Rafal: This method is used to wait for JavaScript and PrimeFaces to load

    private boolean waitForJStoLoad() {
        WebDriverWait wait = new WebDriverWait(driver, 30);

        // Wait for jQuery to load
        ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
            //@Override
            public Boolean apply(WebDriver driver) {
                try {
                    return ((Long) ((JavascriptExecutor) driver)
                            .executeScript("return jQuery.active") == 0);
                } catch (Exception e) {
                    return true;
                }
            }
        };

        // Wait for JavaScript to load
        ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
            //@Override
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor) driver)
                        .executeScript("return document.readyState")
                        .toString().equals("complete");
            }
        };
        return wait.until(jQueryLoad) && wait.until(jsLoad);
    }

    private void waitForPrimeFaces() {
        final String JS_JQUERY_DEFINED = "return typeof jQuery != 'undefined';";
        final String JS_PRIMEFACES_DEFINED = "return typeof PrimeFaces != 'undefined';";
        final String JS_JQUERY_ACTIVE = "return jQuery.active != 0;";
        final String JS_PRIMEFACES_QUEUE_NOT_EMPTY = "return !PrimeFaces.ajax.Queue.isEmpty();";

        waitForJStoLoad();

        new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofMillis(10))
                .until(new Function<WebDriver, Boolean>() {
                    public Boolean apply(WebDriver input) {
                        boolean ajax = false;
                        boolean jQueryDefined = executeBooleanJavascript(input, JS_JQUERY_DEFINED);
                        boolean primeFacesDefined = executeBooleanJavascript(input, JS_PRIMEFACES_DEFINED);

                        if (jQueryDefined) {
                            // jQuery is still active
                            ajax |= executeBooleanJavascript(input, JS_JQUERY_ACTIVE);
                        }
                        if (primeFacesDefined) {
                            // PrimeFaces queue isn't empty
                            ajax |= executeBooleanJavascript(input, JS_PRIMEFACES_QUEUE_NOT_EMPTY);
                        }

                        // Continue if all ajax request are processed
                        return !ajax;
                    }
                });
    }

    private boolean executeBooleanJavascript(WebDriver input, String javascript) {
        return (Boolean) ((JavascriptExecutor) input).executeScript(javascript);
    }

    public void waitForPrimeFacesAndJavaScript() {
        waitForPrimeFaces();
        waitForJStoLoad();
    }


}
