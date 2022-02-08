package com.automation;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ElementProxy implements InvocationHandler {
	private final WebElement element;
	private final WebDriver driver;
    public ElementProxy(WebDriver driver,WebElement element) {
        this.element = element;
        this.driver = driver;
    }
    
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //before invoking actual method check for the popup
//        this.checkForPopupAndKill();
        //at this point, popup would have been closed if it had appeared. element action can be called safely now.
        Object result = method.invoke(element, args);
        return result;
    }
    
    private void checkForPopupAndKill() {
//        if (popup.isDisplayed()) {
//            System.out.println("You damn popup, you appearded again!!?? I am gonna kill you now!!");
//            popup.close();
//        }
    
    }
}
