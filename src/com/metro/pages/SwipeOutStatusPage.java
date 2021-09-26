package com.metro.pages;

import com.metro.objectmap.ObjectRepos;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SwipeOutStatusPage {

    @FindBy(css = ObjectRepos.SwipeOutStatusPage.message_container_css_selector)
    public WebElement message;

    public WebDriver webDriver;

    public SwipeOutStatusPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public String getMessage() {
        new WebDriverWait(webDriver, 10)
                .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(ObjectRepos.SwipeOutStatusPage.message_container_css_selector)));
        return message.getText();
    }


}
