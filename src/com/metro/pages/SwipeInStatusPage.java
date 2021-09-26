package com.metro.pages;

import com.metro.objectmap.ObjectRepos;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class SwipeInStatusPage {

    @FindBy(xpath = ObjectRepos.SwipeInStatusPage.message_container_xpath)
    public WebElement message;
    public WebDriver webDriver;

    public SwipeInStatusPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public String getMessage() {
        new WebDriverWait(webDriver, 10)
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath(ObjectRepos.SwipeInStatusPage.message_container_xpath)));
        return message.getText();
    }


}
