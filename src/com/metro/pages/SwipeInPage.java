package com.metro.pages;

import com.metro.objectmap.ObjectRepos;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class SwipeInPage {

    @FindBy(className = ObjectRepos.SwipeInPage.stations_classname)
    List<WebElement> stations;
    @FindBy(className = ObjectRepos.SwipeInPage.swipe_in_button_classname)
    WebElement swipeInButton;
    public WebDriver webDriver;

    public SwipeInPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public List<WebElement> getStations() {
        return stations; //Get Stations
    }

    public void selectStation(WebElement station) {
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", station); //Select a Random Station
    }

    public void clickSwipeInButton() {
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", swipeInButton); //Swipe In Button
    }


}
