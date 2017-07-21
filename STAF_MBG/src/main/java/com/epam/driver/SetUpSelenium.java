package com.epam.driver;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;

import java.io.File;
import java.util.List;


/**
 * Created by spoduri on 28-12-2016.
 Description
 */
public class SetUpSelenium {


    private static final ThreadLocal<WebDriver> WEB_DRIVER_THREAD_LOCAL;
    private static WebDriver driver = null;
    static {
        WEB_DRIVER_THREAD_LOCAL = new ThreadLocal<WebDriver>();
    }

    public static void initializeDriver(String browser) {
        if (browser.equalsIgnoreCase("firefox"))
        {

            driver = new FirefoxDriver();


          //  LOGGER.info("Started Firefox Driver successfully.");
        }
        else if (browser.equalsIgnoreCase("ie")) {

          ClassLoader  classLoader = ClassLoader.getSystemClassLoader();
            String strx= classLoader.getResource("IEDriverServer.exe").getPath().toString();
            System.setProperty("webdriver.ie.driver", classLoader.getResource("IEDriverServer.exe").getPath().toString());
            driver = new InternetExplorerDriver();
            System.out.print("tester");
        }
       // driver = new EventFiringWebDriver(driver).register(new LogEventListener());
        setDriver(driver);
    }

    private static void setDriver(WebDriver driver) {
        WEB_DRIVER_THREAD_LOCAL.set(driver);
    }

    public static WebDriver getDriver() {
        driver = WEB_DRIVER_THREAD_LOCAL.get();

        if (driver == null)
            throw new IllegalStateException("Driver not set...");

        return driver;
    }

    public static void gotoUrl(String url)
    {
        getDriver().navigate().to(url);
        List<WebElement> objElement = getDriver().findElements(By.xpath("//h1[@id='mainTitle']"));
        if(objElement.size() > 0)
        {
            JavascriptExecutor JS = (JavascriptExecutor)getDriver();
            JS.executeScript("document.getElementById('overridelink').click();");
        }
    }

    public void stopEngine() {
        driver.close();
        driver.quit();

    }

}
