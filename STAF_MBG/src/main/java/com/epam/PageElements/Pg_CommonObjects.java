package com.epam.PageElements;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Created by Srinivasa_Poduri on 1/5/2017.
 */
public class Pg_CommonObjects {

    public WebElement getLoaderRetrivingRequest() {
        return LoaderRetrivingRequest;
    }

    @FindBy(xpath="//span[@id='loadingText']")

    private WebElement LoaderRetrivingRequest;

    @FindBy(xpath = "//div[contains(@class,'x-panel-noborder')]")
    private WebElement LoaderProcessingRequest;

    public WebElement getLoaderProcessingRequest() {
        return LoaderProcessingRequest;
    }
}
