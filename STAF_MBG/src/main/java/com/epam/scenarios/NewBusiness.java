package com.epam.scenarios;
import com.epam.BusinessLib.LoginPage;
import com.epam.BusinessLib.NewQuotePage;
import com.epam.PageElements.PageObjectWrapper;
import com.epam.Reports.Reporter;
import com.epam.Utils.*;
import com.epam.driver.CoreDriverScript;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.testng.annotations.Test;

import java.util.*;

/**
 * Created by spoduri on 28-12-2016.
 */
public class NewBusiness extends CoreDriverScript{

    public static TestDataUtility dataReader = new TestDataUtility();
    LinkedHashMap<String, String> dataMap = new LinkedHashMap<String, String>();


    //Business Classes Object Declarations
    LoginPage objLoginPage = new LoginPage();
    NewQuotePage objNewQuotePage = new NewQuotePage();

    //CommonFunctions object Declarations
    CommonFunctions objCommonFunctions = new CommonFunctions();

    @Test
    public void loginToMBGroup() throws Exception
    {
        boolean blnStatus = false;
        try
        {

            dataMap = dataReader.getEntityData(this.getClass().getSimpleName(),scenario, CoreDriverScript.currentiterationrow);
            Reporter.StartTestReporting("Login to MBG " +dataMap.get("QUOTATION_TYPE") );


            Reporter.StartChildTestReporting("Login to MBG");

            objLoginPage.user_Enters_UserName_and_Password(dataMap);

            if(dataMap.get("QUOTATION_TYPE").trim().equalsIgnoreCase("NEW"))
            {

                blnStatus = objCommonFunctions.WaitForObjectExistance(PageObjectWrapper.GetNewQuotePage().getBtnNewQuote(),10);
                if(blnStatus)
                {
                    objCommonFunctions.click(PageObjectWrapper.GetNewQuotePage().getBtnNewQuote());
                    objNewQuotePage.FillNewQuoteDetails();
                }
            }
            else if(dataMap.get("QUOTATION_TYPE").trim().equalsIgnoreCase("Search"))
            {
                blnStatus = objCommonFunctions.WaitForObjectExistance(PageObjectWrapper.GetNewQuotePage().getTxtSearch(),10);
                if(blnStatus)
                {
                    objCommonFunctions.SendKeys(PageObjectWrapper.GetNewQuotePage().getTxtSearch(),"Q29221");
                }

            }

        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw e;
        }
    }

    @Test(dependsOnMethods = "loginToMBGroup")
    public void ApplicationPage() throws Exception
    {

        boolean blnStatus = false;
        try
        {

             Reporter.StartChildTestReporting("Fill Applicant Page");


            blnStatus = objCommonFunctions.WaitForObjectExistance(PageObjectWrapper.GetApplicantPage().getTitleApplicant(),10);
            if(blnStatus)
            {
                System.out.print("Navigated to Applicant page successfully...!");
            }
            else
            {
                Reporter.objChildTest.log(LogStatus.PASS,"Unable to Navigate to Applicant Page.");
            }

        }
        catch(Exception e)
        {
            throw e;
        }
    }
}
