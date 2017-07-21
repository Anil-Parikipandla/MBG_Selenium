package com.epam.Reports;

import com.epam.driver.CoreDriverScript;
import com.epam.driver.SetUpSelenium;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.epam.Utils.*;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;


/**
 * Created by Srinivasa_Poduri on 1/6/2017.
 */
public class Reporter {

    public static ExtentReports objExtentReports;
    public static ExtentTest objTest;
    public static ExtentTest objChildTest;
    private static List<ExtentTest> objChild;

    public static void StartReporting() throws ParseException{
        try {
            objExtentReports = new ExtentReports(Config.GetConfigProperty("ReportsLocation"), Boolean.parseBoolean(Config.GetConfigProperty("ReportReplaceExisting")));
        }
        catch (Exception e) {
            System.out.println("Exception Caugh here");
        }
    }

    public static void  StopReporting(List<ExtentTest> objChild)
    {

        StopChildTestReporting(objChild);
        objExtentReports.flush();
    }

    public static ExtentTest StartTestReporting(String TestName)
    {

        objTest = objExtentReports.startTest(TestName);
        objTest.log(LogStatus.INFO, TestName);
        return objTest;


    }

    public static void StartChildTestReporting(String TestName)
    {

        objChildTest = objExtentReports.startTest(TestName);
        CoreDriverScript.objChildren.add(objChildTest);

    }
    public static void StopChildTestReporting(List<ExtentTest> objChildren)
    {
        for(ExtentTest e : objChildren)
            objTest.appendChild(e);

    }


    public static void StepInfo(String StrInfo)
    {
        objChildTest.log(LogStatus.INFO,StrInfo);
    }
    public static void StepError(String StrError)
    {
        objChildTest.log(LogStatus.ERROR,StrError);
    }

    public static void ReportPASSStep(String SuccessMessage,WebElement element) throws FrameworkExceptions
    {

        try {
            if (Config.GetConfigProperty("StepLevelScreenShot").equalsIgnoreCase("yes")) {
                objChildTest.log(LogStatus.PASS, SuccessMessage + objChildTest.addScreenCapture(SpecificWebElementScreenshot(element)));
            } else {

                objChildTest.log(LogStatus.PASS, SuccessMessage);
            }
        }
        catch (Exception e)
        {
            throw new FrameworkExceptions();
        }

    }

    public static void ReportPASSStep(String SuccessMessage) throws FrameworkExceptions
    {

        try {

                objChildTest.log(LogStatus.PASS, SuccessMessage);
            }

        catch (Exception e)
        {
            throw new FrameworkExceptions();
        }

    }

    //Have to use in method level
    public static void ReportFailStep(String strFailureMessage)  throws  FrameworkExceptions
    {

        try {

            objChildTest.log(LogStatus.FAIL, strFailureMessage +  objChildTest.addScreenCapture(TakeScreenshot()));

            throw new FrameworkExceptions(strFailureMessage);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String TakeScreenshot() throws IOException {
        File screenshot = ((TakesScreenshot) SetUpSelenium.getDriver()).getScreenshotAs(OutputType.FILE);
        File objfile = new File(Config.GetConfigProperty("SceenShotLocation")+System.nanoTime()+".png");
        FileUtils.copyFile(screenshot,objfile);
        return objfile.getPath();

    }
    public static String SpecificWebElementScreenshot(WebElement element) throws Exception {

        File screenshotLocation = null;
        String screenshotPath= "";
        try {
            File screenshot = ((TakesScreenshot) SetUpSelenium.getDriver()).getScreenshotAs(OutputType.FILE);
            BufferedImage fullImg = ImageIO.read(screenshot);

// Get the location of element on the page
            Point point = element.getLocation();

// Get width and height of the element
            int eleWidth = element.getSize().getWidth();
            int eleHeight = element.getSize().getHeight();

// Crop the entire page screenshot to get only element screenshot
            BufferedImage eleScreenshot = fullImg.getSubimage(point.getX(), point.getY(), eleWidth, eleHeight);
            ImageIO.write(eleScreenshot, "png", screenshot);

// Copy the element screenshot to disk
            screenshotLocation = new File(Config.GetConfigProperty("SceenShotLocation")+"MBGElement_screenshot" + System.nanoTime() + ".png");
            screenshotPath = screenshotLocation.getPath();

            FileUtils.copyFile(screenshot, screenshotLocation);

        } catch (NoSuchElementException nse) {
            screenshotPath = "";

        }


        return screenshotPath;
    }
}
