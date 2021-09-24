package com.metro.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class HomePage {

    public WebElement loginButton;
    public WebDriver webDriver;

    public HomePage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public void loginButtonClick() {
        loginButton = webDriver.findElement(By.className("button-primary")); //Login Button
        loginButton.click();
    }

}
