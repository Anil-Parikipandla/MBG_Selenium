package com.epam.BusinessLib;


import com.epam.PageElements.PageObjectWrapper;
import com.epam.Utils.CommonFunctions;
import com.epam.Utils.FrameworkExceptions;
import com.relevantcodes.extentreports.ExtentTest;

public class NewQuotePage {

	CommonFunctions objCommonFunctions = new CommonFunctions();

	public void FillNewQuoteDetails() throws Exception {
		try {
			objCommonFunctions.WaitTillRender();
			objCommonFunctions.SendKeys(PageObjectWrapper.GetNewQuotePage().getTxteffdate(), "12/28/2016");
			objCommonFunctions.WaitTillRender();
			objCommonFunctions.SelectbyVisibleText(PageObjectWrapper.GetNewQuotePage().getLstquoteline(), "Homeowners");
			objCommonFunctions.WaitTillRender();
			objCommonFunctions.SelectbyVisibleText(PageObjectWrapper.GetNewQuotePage().getLstState(), "MD");
			objCommonFunctions.WaitTillRender();
			objCommonFunctions.SelectbyVisibleText(PageObjectWrapper.GetNewQuotePage().getLsttransact(), "Yes");
			objCommonFunctions.WaitTillRender();
			objCommonFunctions.SelectbyVisibleText(PageObjectWrapper.GetNewQuotePage().getLstquotetype(), "Full Quote");
			objCommonFunctions.WaitTillRender();

			objCommonFunctions.click(PageObjectWrapper.GetNewQuotePage().getBtnStartQuote());
			objCommonFunctions.JSExecutor(PageObjectWrapper.GetNewQuotePage().getBtnStartQuote());

		} catch(Exception ex) {
			throw new FrameworkExceptions(ex.getMessage());

		}
	}
	public void ClickonNewQuote()
	{
		PageObjectWrapper.GetNewQuotePage().getBtnNewQuote().click();
	}
}
