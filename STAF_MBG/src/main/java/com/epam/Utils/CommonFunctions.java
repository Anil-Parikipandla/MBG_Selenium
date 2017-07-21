package com.epam.Utils;
import com.epam.PageElements.PageObjectWrapper;
import com.epam.Reports.Reporter;
import com.epam.driver.SetUpSelenium;
import com.google.common.base.Function;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by spoduri on 28-12-2016.
 */
public class CommonFunctions {

    public boolean WaitForObjectExistance(final WebElement element, int Timeout) throws FrameworkExceptions {
        boolean blnStatus = false;
        FluentWait<WebDriver> objFluentWait = new FluentWait<WebDriver>(SetUpSelenium.getDriver());
        objFluentWait.pollingEvery(1, TimeUnit.SECONDS);
        objFluentWait.withTimeout(Timeout, TimeUnit.SECONDS);
        try {

            Function<WebDriver, Boolean> function = new Function<WebDriver, Boolean>() {
                public Boolean apply(WebDriver driver) {
                    boolean blnFound = false;
                    try {

                        if (element.isDisplayed() && element.isEnabled()) {

                            blnFound = true;

                        } else {
                            blnFound = false;
                        }

                    } catch (Exception exe) {

                        Reporter.StepInfo("Trying to Find Element  with Locator Value :- " +element.toString().split(":")[element.toString().split(":").length - 1] );

                    }

                    return blnFound;
                }


            };
            blnStatus = objFluentWait.until(function);
            if(blnStatus)
            {
                Reporter.ReportPASSStep( "Element With Locator Value " + element.toString().split(":")[element.toString().split(":").length - 1] + "  found Successfully.",element);
            }

        } catch (TimeoutException ex) {
            Reporter.ReportFailStep("Unable to Find the Element with Locator Value  "+ element.toString().split(":")[element.toString().split(":").length - 1] + "With in the Time " + String.valueOf(Timeout));
            throw new FrameworkExceptions("Exception occurred while trying to find the  WebElement with locator Value : " + element.toString().split(":")[element.toString().split(":").length - 1]);

        }
        return blnStatus;
    }

    public void click(WebElement element) throws FrameworkExceptions {
        boolean blnStatus = false;
        try {

            blnStatus = WaitForObjectExistance(element, 10);
            if (blnStatus)
            {
                Reporter.StepInfo("Executing Click    Method  on WebElement with Locator -" + element.toString().split(":")[element.toString().split(":").length - 1]);
                element.click();
                Reporter.ReportPASSStep("Click Action  is Successful." ,element);

            }

        } catch (Exception ex) {
            throw new FrameworkExceptions(ex.getMessage());
        }

    }

    public void SendKeys(WebElement element, String strValue) throws FrameworkExceptions {
        boolean blnStatus = false;
        try {
            blnStatus = WaitForObjectExistance(element, 10);

            if (blnStatus)
            {
                Reporter.StepInfo("Executing Send Keys   Method To Enter  " + strValue);
                element.clear();
                element.sendKeys(strValue);
                Reporter.ReportPASSStep("Value Entered Successfully :- " + strValue,element);

            }
        } catch (Exception e) {

            throw new FrameworkExceptions(e.getMessage());
        }
    }

    public void ClearText(WebElement element) throws FrameworkExceptions {
        boolean blnStatus = false;
        try {
            blnStatus = WaitForObjectExistance(element, 10);
            if (blnStatus)
            {

                element.clear();
                Reporter.ReportPASSStep("Data from Text filed Cleared Successfully :-" ,element);

            }
        } catch (Exception e) {

            throw new FrameworkExceptions(e.getMessage());
        }
    }


    public void SelectbyVisibleText(WebElement lstElement, String strValue) throws FrameworkExceptions {
        boolean blnStatus = false;
        try {
            blnStatus = WaitForObjectExistance(lstElement, 10);
            if (blnStatus)
            {
                Reporter.StepInfo("Executing SelectByVisibleText   Method To select  " + strValue);
                Select objSelect = new Select(lstElement);
                WaitForDropDownToLoad(lstElement,strValue,10);
                objSelect.selectByVisibleText(strValue);
                WaitTillRender();
                Reporter.ReportPASSStep("Value Selected Successfully :-" + strValue ,lstElement);
            }

        } catch (Exception e) {
            throw new FrameworkExceptions(e.getMessage());
        }
    }


    public void SelectbyValue(WebElement lstElement, String strValue) throws FrameworkExceptions {
        boolean blnStatus = false;
        try {
            blnStatus = WaitForObjectExistance(lstElement, 10);
            if (blnStatus)
            {
                Reporter.StepInfo("Executing SelectbyValue   Method To select  " + strValue);
                Select objSelect = new Select(lstElement);
                objSelect.selectByValue(strValue);
                WaitTillRender();
                Reporter.ReportPASSStep("Value Selected Successfully :-" + strValue ,lstElement);
            }
        } catch (Exception e) {
            throw new FrameworkExceptions(e.getMessage());
        }
    }

    public void SelectbyIndex(WebElement lstElement, int Index) throws FrameworkExceptions {
        boolean blnStatus = false;
        try {
            blnStatus = WaitForObjectExistance(lstElement, 10);
            if (blnStatus)
            {
                Reporter.StepInfo("Executing SelectbyIndex  Method To select List element with Index " + Index);
                Select objSelect = new Select(lstElement);
                objSelect.selectByIndex(Index);
                WaitTillRender();
                Reporter.ReportPASSStep("List Item Selected Successfully with Index :-" + Index ,lstElement);
            }
        } catch (Exception e) {
            throw new FrameworkExceptions(e.getMessage());
        }
    }

    public void WaitTillRender() throws FrameworkExceptions {

        boolean blnStatus = false;

        try {
            //To handle the Retriving Request spinner['Express is Retriving your Request....'] which is displayed at the middle/upper part of the screen.
            do {
                blnStatus = PageObjectWrapper.GetCommonObject().getLoaderRetrivingRequest().isDisplayed();

            } while (blnStatus);

            //to handle the 'Processing MS...' spinner which is displayed at the top left of the screen.
            do {

                String strVisiblility = PageObjectWrapper.GetCommonObject().getLoaderProcessingRequest().getCssValue("visibility");
                blnStatus = strVisiblility.trim().equalsIgnoreCase("visible");
                //System.out.println(PageObjectWrapper.GetCommonObject().getLoaderProcessingRequest().getText());


            } while (blnStatus);
        } catch (Exception exe) {
            //  throw new FrameworkExceptions(exe.getMessage());
        }
    }

    public void JSExecutor(WebElement element) throws FrameworkExceptions {
        try {
            JavascriptExecutor JS = (JavascriptExecutor) SetUpSelenium.getDriver();
            JS.executeScript("arguments[0].click();", element);

        } catch (Exception ex) {

            throw new FrameworkExceptions(ex.getMessage());

        }
    }

    public String SpecificWebElementScreenshot(WebElement element) throws Exception {

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

    public boolean WaitForDropDownToLoad(final WebElement lstElement, final String strValue, int Timeout) throws FrameworkExceptions
    {
        boolean blnStatus = false;
        boolean blnWaitForDropdownToload= false;
        WebDriverWait wait = new WebDriverWait(SetUpSelenium.getDriver(), Timeout);
        try
        {
            blnStatus = WaitForObjectExistance(lstElement,Timeout);
            if(blnStatus)
            {
                blnWaitForDropdownToload = wait.until(new ExpectedCondition<Boolean>() {
                    public Boolean apply(WebDriver webDriver) {
                        boolean bln =false;

                        bln = lstElement.getText().contains(strValue);
                        return bln;
                    }
                });
            }

            if(blnWaitForDropdownToload)
            {
                Reporter.ReportPASSStep("List option " + strValue  + " Loaded Successfully ",lstElement);
            }
        }
        catch(Exception e)
        {
            throw new FrameworkExceptions(e.getMessage());
        }
        return blnWaitForDropdownToload;
    }

    public List<String> getListOptions(WebElement lstElement) throws  FrameworkExceptions
    {
        List<String> lstOptions = null;
        boolean blnStatus = false;
        try
        {
            blnStatus = WaitForObjectExistance(lstElement,10);
            if(blnStatus){
                Select objSelect = new Select(lstElement);
                for(WebElement ele : objSelect.getOptions())
                {
                    lstOptions.add(ele.getText());
                }

            }

        }
        catch(Exception e)
        {
            throw new FrameworkExceptions(e.getMessage());
        }
        return lstOptions;
    }

    public void SwitchToFrame(String strFrameName) throws FrameworkExceptions {
        try
        {
            Reporter.StepInfo("Swithing to Frame with Name :-" + strFrameName);
            SetUpSelenium.getDriver().switchTo().frame(strFrameName);
            Reporter.ReportPASSStep("Switched to Frame " + strFrameName + "Successfully");
        }
        catch(Exception e)
        {
            Reporter.ReportFailStep("Unable to Switch to Frame "+ strFrameName);
            throw new FrameworkExceptions(e.getMessage());
        }
    }

    public void SwitchToFrame(int strFrameNumber) throws FrameworkExceptions {
        try
        {
            Reporter.StepInfo("Swithing to Frame with Frame Number :-" + strFrameNumber);
            SetUpSelenium.getDriver().switchTo().frame(strFrameNumber);

            Reporter.ReportPASSStep("Switched to Frame with frame Number" + strFrameNumber + "Successfully");
        }
        catch(Exception e)
        {
            Reporter.ReportFailStep("Unable to Switch to Frame with Number"+ strFrameNumber);
            throw new FrameworkExceptions(e.getMessage());
        }
    }

    public void Check(WebElement eleCheckbox) throws FrameworkExceptions {
        boolean blnStatus = false;
        boolean blnIsSelected = false;
        try
        {
            blnStatus = WaitForObjectExistance(eleCheckbox,10);
            if(blnStatus)
            {
                blnIsSelected = eleCheckbox.isSelected();
                if(!blnIsSelected)
                {
                    eleCheckbox.click();
                    //Reporter.
                }
                else
                {
                    // Reporter.StepInfo("Checkbox Already Selected.");
                }
            }

        }
        catch(Exception e)
        {
            throw new FrameworkExceptions(e.getMessage());
        }
    }

    public void UnCheck(WebElement eleCheckbox) throws FrameworkExceptions {
        boolean blnStatus = false;
        boolean blnIsSelected = false;
        try
        {
            blnStatus = WaitForObjectExistance(eleCheckbox,10);
            if(blnStatus)
            {
                blnIsSelected = eleCheckbox.isSelected();
                if(blnIsSelected)
                {
                    eleCheckbox.click();
                    //Reporter.
                }
                else
                {
                    // Reporter.StepInfo("Checkbox Already Selected.");
                }
            }

        }
        catch(Exception e)
        {
            throw new FrameworkExceptions(e.getMessage());
        }
    }

    public void Refresh() throws FrameworkExceptions {
        try{

            try {
                SetUpSelenium.getDriver().navigate().refresh();
                Alert objAlert = SetUpSelenium.getDriver().switchTo().alert();
                objAlert.dismiss();
            }
            catch(NoAlertPresentException NAPE)
            {
                Reporter.StepInfo("No Alert dialog displayed while Refreshing the Page");
            }

        }
        catch(Exception e)
        {
            throw new FrameworkExceptions(e.getMessage());
        }
    }

    public String GetText(WebElement element) throws FrameworkExceptions {
        boolean blnStatus = false;
        String strText= "";
        try
        {
            blnStatus = WaitForObjectExistance(element,10);
            if(blnStatus)
            {
                strText = element.getText();
            }
        }
        catch(Exception e)
        {
            throw new FrameworkExceptions(e.getMessage());
        }

        return strText;
    }

    public String getAttribute(WebElement element,String strAttributeName) throws FrameworkExceptions {
        boolean blnStatus = false;
        String strAttribute = "";

        try
        {
            blnStatus = WaitForObjectExistance(element,10);
            if(blnStatus)
            {
                strAttribute= element.getAttribute(strAttributeName);
            }
        }
        catch (Exception e)
        {
            throw new FrameworkExceptions(e.getMessage());
        }
        return  strAttribute;

    }

    public boolean isAlertPresent() throws FrameworkExceptions {
        boolean blnStatus = false;
        try
        {
            Alert objAlert = SetUpSelenium.getDriver().switchTo().alert();
            blnStatus = true;
        }
        catch(NoAlertPresentException NAPE) {

            blnStatus = false;
        }
        catch(Exception e)
        {
            throw new FrameworkExceptions(e.getMessage());
        }

        return blnStatus;

    }

    public void HandleAlertDialog(String strAction) throws FrameworkExceptions {
        boolean blnStatus = false;

        try
        {
            blnStatus = isAlertPresent();
            if(blnStatus)
            {
                Alert objAlert = SetUpSelenium.getDriver().switchTo().alert();
                if(strAction.equalsIgnoreCase("OK"))
                {
                    objAlert.accept();
                    SetUpSelenium.getDriver().switchTo().defaultContent();
                }
                else if (strAction.equalsIgnoreCase("Cancle"))
                {
                    objAlert.dismiss();
                    SetUpSelenium.getDriver().switchTo().defaultContent();
                }
                else
                {
                    objAlert.sendKeys(strAction);
                }
            }
        }
        catch(Exception e)
        {
            throw new FrameworkExceptions(e.getMessage());
        }
    }

    public enum MouseClick
    {
        Doubleclick,Left,Right

    }

    public void MouseClick(WebElement element,MouseClick enumMouseClick ) throws FrameworkExceptions {
        boolean blnStatus = false;
        try
        {
            blnStatus = WaitForObjectExistance(element,10);
            if(blnStatus)
            {
                Actions objActions = new Actions(SetUpSelenium.getDriver());

                switch(enumMouseClick)
                {
                    case Left:

                        objActions.click(element);
                        break;
                    case Right:
                        objActions.contextClick(element);
                        break;
                    case Doubleclick:
                        objActions.doubleClick(element);
                        break;
                    default:
                        objActions.click();
                }
                //logger
                //pass step
            }
        }
        catch(Exception e)
        {
            throw new FrameworkExceptions(e.getMessage());

            //fail step
        }
    }

    public void DragAndDrop(WebElement eleSource,WebElement eleDestination) throws FrameworkExceptions {
        boolean blnStatus = false;
        try
        {
            blnStatus = WaitForObjectExistance(eleSource,10) && WaitForObjectExistance(eleDestination,10);

            if(blnStatus)
            {
                Actions objActions = new Actions(SetUpSelenium.getDriver());
                Action DragAndDrop = objActions.clickAndHold(eleSource).moveToElement(eleDestination).release(eleSource).build();
                DragAndDrop.perform();
//Logger and PASS STEP

            }
        }
        catch(Exception e)
        {

            throw new FrameworkExceptions("Unable to exeucte drag and drop action on object: " + eleSource + "to target location" + eleDestination);

            //fail step
        }

    }

    public void ActionSendKeys(WebElement element, String... keys) throws FrameworkExceptions {
        boolean blnStatus = false;

        String[] arrKeys;


        try {

            blnStatus = WaitForObjectExistance(element, 10);
            if (blnStatus) {
                arrKeys = keys;
                Actions objAction = new Actions(SetUpSelenium.getDriver());
                for (int i = 0; i < arrKeys.length; i++) {

                    objAction.sendKeys(Keys.CONTROL,Keys.END);
                    objAction.sendKeys(arrKeys[i]);
                }
//logger and pass step
            }
        }
        catch(Exception e)
        {
            throw new FrameworkExceptions(e.getMessage());


        }
    }

    public String getDate(String strValue) throws FrameworkExceptions {
     String strDate = "";

     try
     {
         SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
         Calendar objCal = Calendar.getInstance();
         objCal.setTime(sdf.parse(String.valueOf(objCal.getTime())));
         if(strValue.equalsIgnoreCase("CDATE_TODAY"))
         {
             strDate = objCal.getTime().toString();
         }
         else if(strValue.contains("CDATE_TODAY#"))
         {
             String[] strFutureDate = strValue.split("DAY#");
             objCal.add(Calendar.DATE,Integer.parseInt(strFutureDate[1]));
             strDate = sdf.format(objCal.getTime().toString());
         }
         else if(strValue.contains("CATE_TODAY_"))
         {
             String[] strPastDate = strDate.split("DAY_");
             objCal.add(Calendar.DATE,-Integer.parseInt(strPastDate[1]));
             strDate = sdf.format(objCal.getTime().toString());
         }
     }
     catch(Exception e)
     {
         throw new FrameworkExceptions(e.getMessage());
     }

     return strDate;
    }


}