package com.epam.PageElements;
import com.epam.driver.SetUpSelenium;
import org.openqa.selenium.support.PageFactory;

public class PageObjectWrapper {

	
	public static Pg_LoginPage GetLoginPage()
	{
		return PageFactory.initElements(SetUpSelenium.getDriver(), Pg_LoginPage.class);
	}
	
	public static Pg_NewQuotePage GetNewQuotePage()
	{
		return PageFactory.initElements(SetUpSelenium.getDriver(), Pg_NewQuotePage.class);
	}
	public static Pg_ApplicantPage GetApplicantPage()
	{
		return PageFactory.initElements(SetUpSelenium.getDriver(), Pg_ApplicantPage.class);
	}

	public static Pg_CommonObjects GetCommonObject()
	{
		return PageFactory.initElements(SetUpSelenium.getDriver(),Pg_CommonObjects.class);
	}
}
