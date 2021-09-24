package com.metro.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage {

    public WebElement cardIdTextBox;
    public WebElement passwordTextBox;
    public WebElement loginButton;
    public WebDriver webDriver;

    public LoginPage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public void setCardId(String cardId) {
        WebElement cardIdTextBox = webDriver.findElement(By.id("cardId"));
        cardIdTextBox.clear();
        cardIdTextBox.sendKeys(cardId);
    }

    public void setPassword(String password) {
        WebElement passwordTextBox = webDriver.findElement(By.id("password"));
        passwordTextBox.clear();
        passwordTextBox.sendKeys(password);
    }

    public void loginButtonClick() {
        loginButton = webDriver.findElement(By.className("button-block")); //Login Button
        loginButton.click();
    }

}
