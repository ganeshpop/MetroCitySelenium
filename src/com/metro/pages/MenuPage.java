package com.metro.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class MenuPage {

    public WebElement logoutButton;
    public WebDriver webDriver;

    public MenuPage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public void logoutButtonClick() {
        logoutButton = webDriver.findElement(By.cssSelector("body > nav > ul > li:nth-child(8) > a")); //Logout Button
        logoutButton.click();
    }

}
