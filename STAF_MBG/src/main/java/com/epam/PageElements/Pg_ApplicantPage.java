package com.epam.PageElements;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Srinivasa_Poduri on 1/5/2017.
 */
public class Pg_ApplicantPage {

    @FindBy(xpath="//div[contains(text(),'Applicant')]")
    private WebElement titleApplicant;

    public WebElement getTitleApplicant() {
        return titleApplicant;
    }


}
