package com.metro.pages;

import com.metro.objectmap.ObjectRepos;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class SwipeOutPage {

    @FindBy(className = ObjectRepos.SwipeOutPage.stations_classname)
    List<WebElement> stations;
    @FindBy(className = ObjectRepos.SwipeOutPage.swipe_out_button_classname)
    WebElement swipeOutButton;
    public WebDriver webDriver;

    public SwipeOutPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public List<WebElement> getStations() {
        return stations; //Get Stations
    }

    public void selectStation(WebElement station) {
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", station); //Select a Random Station
    }

    public void clickSwipeOutButton() {
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", swipeOutButton);
    }


}
