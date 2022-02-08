package com.automation;

import java.lang.reflect.Proxy;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ElementGuard {
	public static  WebElement guard(WebDriver driver,WebElement element) {
        ElementProxy proxy = new ElementProxy(driver,element);
        WebElement wrappdElement = (WebElement) Proxy.newProxyInstance(ElementProxy.class.getClassLoader(),
                                                                       new Class[] { WebElement.class },
                                                                       proxy);
        return wrappdElement;
    }
}
