package com.metro.pages;

import com.metro.objectmap.ObjectRepos;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    @FindBy(id = ObjectRepos.LoginPage.card_id_text_box_id)
    public WebElement cardIdTextBox;
    @FindBy(id = ObjectRepos.LoginPage.password_text_box_id)
    public WebElement passwordTextBox;
    @FindBy(className = ObjectRepos.LoginPage.login_button_classname)
    public WebElement loginButton;
    public WebDriver webDriver;

    public LoginPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public void setCardId(String cardId) {
        cardIdTextBox.clear();
        cardIdTextBox.sendKeys(cardId);
    }

    public void setPassword(String password) {
        passwordTextBox.clear();
        passwordTextBox.sendKeys(password);
    }

    public void clickLoginButton() {
        loginButton.click();
    }

}
