package com.epam.BusinessLib;

import com.epam.PageElements.PageObjectWrapper;
import com.epam.Utils.CommonFunctions;
import com.epam.Utils.FrameworkExceptions;
import org.testng.asserts.Assertion;


import java.util.LinkedHashMap;
import java.util.TreeMap;

public class LoginPage {

    CommonFunctions objCommonFunctions = new CommonFunctions();
    public Assertion objAssertion = new Assertion();

    public void user_Enters_UserName_and_Password(LinkedHashMap<String, String> dataMap)  throws FrameworkExceptions{
        boolean blnStatus = false;
        try {



            objCommonFunctions.SendKeys(PageObjectWrapper.GetLoginPage().getTxtUsername(), dataMap.get("USERNAME"));
            objCommonFunctions.SendKeys(PageObjectWrapper.GetLoginPage().getTxtPassword(), dataMap.get("PASSWORD"));
            objCommonFunctions.click(PageObjectWrapper.GetLoginPage().getBtnSubmit());

        } catch (Exception e) {

             throw new FrameworkExceptions(e.getMessage());
        }
    }
}


