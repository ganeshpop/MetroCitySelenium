package com.metro.testcases;

import com.metro.pages.*;
import com.metro.utilities.ExcelReader;
import com.metro.utilities.PropertyReader;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class SwipeInTestCase {
    WebDriver webDriver;

    @BeforeMethod
    public void openBrowser() throws IOException {
        System.setProperty("webdriver.chrome.driver", PropertyReader.ReadProperty("chrome_driver_path"));
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setHeadless(false);
        webDriver = new ChromeDriver(chromeOptions);
        webDriver.manage().window().maximize();
        webDriver.get(PropertyReader.ReadProperty("app_url"));
        HomePage homePage = new HomePage(webDriver);
        homePage.clickLoginButton();
    }

    @DataProvider(name = "validSwipeInDetails")
    public static Object[][] validSwipeInDetails() {
        String filePath = System.getProperty("user.dir") + "/src/com/metro/testdata";
        String fileName  = "MetroSwipeInTestData.xlsx";
        String sheetName = "ValidSwipeInDetails";
        return ExcelReader.readCredentials(filePath, fileName, sheetName);
    }


    @Test(dataProvider = "validSwipeInDetails", priority = 1, testName = "Valid Swipe In")
    public void validSwipeIn(String cardId, String password) throws InterruptedException {
        LoginPage loginPage = new LoginPage(webDriver);
        NavigationBar navigationBar = new NavigationBar(webDriver);
        SwipeInPage swipeInPage = new SwipeInPage(webDriver);
        SwipeInStatusPage swipeInStatusPage = new SwipeInStatusPage(webDriver);
        Thread.sleep(1000);
        loginPage.setCardId(cardId);
        loginPage.setPassword(password);
        loginPage.clickLoginButton();
        Thread.sleep(1000);
        navigationBar.clickSwipeInButton(); //SwipeIn Button
        Thread.sleep(1000);
        int randomNumber = ThreadLocalRandom.current().nextInt(0, 4 + 1);
        List<WebElement> stationList = swipeInPage.getStations(); //Get Stations
        swipeInPage.selectStation(stationList.get(randomNumber)); //Select a Random Station
        swipeInPage.clickSwipeInButton(); // SwipeIn Button
        String message = swipeInStatusPage.getMessage();
        Assert.assertEquals(message, "Swipe In Successful At Station Station " + randomNumber, "Swipe In Not Possible");
        Thread.sleep(1000);
        navigationBar.clickLogoutButton();
    }

    @DataProvider(name = "invalidSwipeInDetails")
    public static Object[][] invalidSwipeInDetails() {
        String filePath = System.getProperty("user.dir") + "/src/com/metro/testdata";
        String fileName  = "MetroSwipeInTestData.xlsx";
        String sheetName = "InvalidSwipeInDetails";
        return ExcelReader.readCredentials(filePath, fileName, sheetName);
    }

    @Test(dataProvider = "invalidSwipeInDetails", priority = 5, testName = "Invalid Swipe In")
    public void InvalidSwipeIn(String cardId, String password) throws InterruptedException {
        LoginPage loginPage = new LoginPage(webDriver);
        NavigationBar navigationBar = new NavigationBar(webDriver);
        SwipeInPage swipeInPage = new SwipeInPage(webDriver);
        SwipeInStatusPage swipeInStatusPage = new SwipeInStatusPage(webDriver);
        Thread.sleep(1000);
        loginPage.setCardId(cardId);
        loginPage.setPassword(password);
        loginPage.clickLoginButton();
        Thread.sleep(1000);
        navigationBar.clickSwipeInButton(); //SwipeIn Button
        Thread.sleep(1000);
        int randomNumber = ThreadLocalRandom.current().nextInt(0, 4 + 1);
        List<WebElement> stationList = swipeInPage.getStations(); //Get Stations
        swipeInPage.selectStation(stationList.get(randomNumber)); //Select a Random Station
        swipeInPage.clickSwipeInButton(); // SwipeIn Button
        String message = swipeInStatusPage.getMessage();
        Assert.assertEquals(message, "You have not Swiped Out from Previous Trip!", "Swipe In Possible");
        Thread.sleep(1000);
        navigationBar.clickLogoutButton();
    }


    @AfterMethod
    public void closeBrowser() throws InterruptedException {
        if (webDriver != null) {
            Thread.sleep(1000);
            webDriver.close();
        }
    }
}
