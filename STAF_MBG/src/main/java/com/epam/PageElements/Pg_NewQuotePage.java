package com.epam.PageElements;

import com.epam.Utils.CommonFunctions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public class Pg_NewQuotePage {

	public WebElement getTxtSearch() {
		return txtSearch;
	}

	@FindBy(id="quickSearchText")
	private WebElement txtSearch;

	@FindBy(xpath ="//a[contains(text(),'New Quote')]")
	private WebElement btnNewQuote;
	
	@FindBy(xpath ="//select[@fieldref='data.AgencyID']")
	private WebElement lstAgency;
	
	@FindBy(xpath ="//select[@fieldref='data.Producer']")
	private WebElement lstproducer;
	
	@FindBy(xpath ="//input[@dctactionid='NewQuoteSelectionPortfolio_Extended_Extended']")
	private 	WebElement txteffdate;
	
	@FindBy(xpath ="//select[@fielditems='Line_string_req_']")
	private WebElement lstquoteline;
	
	@FindBy(xpath ="//select[@fielditems='State_string_req_']")
	private WebElement lstState;
	
	@FindBy(xpath ="//select[@fielditems='Use Transact?_string_req_']")
	private WebElement lsttransact;
	
	@FindBy(xpath ="//select[@fieldref='data.NewRatePlan_OnScreen']")
	private WebElement lstpression;
	
	@FindBy(xpath ="//select[@fielditems='Quote Type_string_opt_']")
	private WebElement lstquotetype;
	
	@FindBy(xpath ="//a[contains(text(),'Start Quote')]")
	private WebElement btnStartQuote;
	
	public WebElement getLstAgency() {
		
		return lstAgency;
	}

	public WebElement getLstproducer() {
		
		return lstproducer;
	}

	public WebElement getTxteffdate() {
		return txteffdate;
	}

	public WebElement getLstquoteline() {
		
		return lstquoteline;
	}

	public WebElement getLstState() {
		
		return lstState;
	}

	public WebElement getLsttransact() {
		
		return lsttransact;
	}

	public WebElement getLstpression() {

		return lstpression;
	}

	public WebElement getLstquotetype() {
		
			return lstquotetype;
	}

	public WebElement getBtnStartQuote() {
		// TODO Auto-generated method stub
		return btnStartQuote;
	}

	public WebElement getBtnNewQuote() {
		// TODO Auto-generated method stub
		return btnNewQuote;
	}


}
