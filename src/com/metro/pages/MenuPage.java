package com.metro.pages;

import com.metro.objectmap.ObjectRepos;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MenuPage {

    @FindBy(css = ObjectRepos.MenuPage.message_container_css_selector)
    public WebElement message;
    public WebDriver webDriver;

    public MenuPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public String getMessage(){
        return message.getText();
    }

}
