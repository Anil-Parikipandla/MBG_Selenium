package com.epam.PageElements;

import com.epam.Utils.*;
import java.util.List;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class Pg_LoginPage {

	@FindBy(xpath="//input[@id='username']")
	private WebElement txtUsername;

	@FindBy(xpath="//input[@id='password']")
	private WebElement txtPassword;

	@FindBy(xpath ="//select[@name='serverSelection']")

	private WebElement lstServerSelection;

	@FindBy(xpath="//a[@name='home']")

	private WebElement btnSubmit;

	public WebElement getTxtUsername() {


		return txtUsername;
	}

	public WebElement getTxtPassword() {
		return txtPassword;
	}

	public WebElement getBtnSubmit() {
		return btnSubmit;
	}




}
