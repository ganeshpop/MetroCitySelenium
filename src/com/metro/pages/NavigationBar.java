package com.metro.pages;

import com.metro.objectmap.ObjectRepos;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class NavigationBar {

    @FindBy(css = ObjectRepos.NavBar.logout_button_css_selector)
    public WebElement logoutButton;
    @FindBy(css = ObjectRepos.NavBar.recharge_button_css_selector)
    public WebElement rechargeButton;
    @FindBy(css = ObjectRepos.NavBar.swipe_in_button_css_selector)
    public WebElement swipeInButton;
    @FindBy(css = ObjectRepos.NavBar.swipe_out_button_css_selector)
    public WebElement swipeOutButton;
    public WebDriver webDriver;

    public NavigationBar(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public void clickLogoutButton() {
        logoutButton.click();
    }

    public void clickRechargeButton() {
        rechargeButton.click();
    }

    public void clickSwipeInButton() {
        swipeInButton.click();
    }

    public void clickSwipeOutButton() {
        swipeOutButton.click();
    }


}
