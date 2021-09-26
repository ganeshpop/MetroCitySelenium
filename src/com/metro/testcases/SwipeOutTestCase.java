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

public class SwipeOutTestCase {
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

    @DataProvider(name = "validSwipeOutDetails")
    public static Object[][] validSwipeOutDetails() {
        String filePath = System.getProperty("user.dir") + "/src/com/metro/testdata";
        String fileName  = "MetroSwipeOutTestData.xlsx";
        String sheetName = "ValidSwipeOutDetails";
        return ExcelReader.readCredentials(filePath, fileName, sheetName);
    }


    @Test(dataProvider = "validSwipeOutDetails", priority = 1, testName = "Valid Swipe Out")
    public void validSwipeOut(String cardId, String password) throws InterruptedException {
        LoginPage loginPage = new LoginPage(webDriver);
        NavigationBar navigationBar = new NavigationBar(webDriver);
        SwipeOutPage swipeOutPage = new SwipeOutPage(webDriver);
        SwipeOutStatusPage swipeOutStatusPage = new SwipeOutStatusPage(webDriver);
        Thread.sleep(1000);
        loginPage.setCardId(cardId);
        loginPage.setPassword(password);
        loginPage.clickLoginButton();
        Thread.sleep(1000);
        navigationBar.clickSwipeOutButton(); //SwipeOut Button
        Thread.sleep(1000);
        int randomNumber = ThreadLocalRandom.current().nextInt(0, 4 + 1);
        List<WebElement> stationList = swipeOutPage.getStations(); //Get Stations
        swipeOutPage.selectStation(stationList.get(randomNumber)); //Select a Random Station
        swipeOutPage.clickSwipeOutButton(); // SwipeOut Button
        String message = swipeOutStatusPage.getMessage();
        Assert.assertEquals(message, "Swipe Out Successful", "Swipe Out Not Possible");
        Thread.sleep(1000);
        navigationBar.clickLogoutButton();
    }

    @DataProvider(name = "invalidSwipeOutDetails")
    public static Object[][] invalidSwipeOutDetails() {
        String filePath = System.getProperty("user.dir") + "/src/com/metro/testdata";
        String fileName  = "MetroSwipeOutTestData.xlsx";
        String sheetName = "InvalidSwipeOutDetails";
        return ExcelReader.readCredentials(filePath, fileName, sheetName);
    }

    @Test(dataProvider = "invalidSwipeOutDetails", priority = 5, testName = "Invalid Swipe Out")
    public void InvalidSwipeOut(String cardId, String password) throws InterruptedException {
        LoginPage loginPage = new LoginPage(webDriver);
        NavigationBar navigationBar = new NavigationBar(webDriver);
        SwipeOutPage swipeOutPage = new SwipeOutPage(webDriver);
        SwipeOutStatusPage swipeOutStatusPage = new SwipeOutStatusPage(webDriver);
        Thread.sleep(1000);
        loginPage.setCardId(cardId);
        loginPage.setPassword(password);
        loginPage.clickLoginButton();
        Thread.sleep(1000);
        navigationBar.clickSwipeOutButton(); //SwipeOut Button
        Thread.sleep(1000);
        int randomNumber = ThreadLocalRandom.current().nextInt(0, 4 + 1);
        List<WebElement> stationList = swipeOutPage.getStations(); //Get Stations
        swipeOutPage.selectStation(stationList.get(randomNumber)); //Select a Random Station
        swipeOutPage.clickSwipeOutButton(); // SwipeOut Button
        String message = swipeOutStatusPage.getMessage();
        Assert.assertEquals(message, "You Have not Swiped In at any Station!", "Swipe Out Possible");
        Thread.sleep(1000);
        navigationBar.clickLogoutButton(); //Logout Button
    }


    @AfterMethod
    public void closeBrowser() throws InterruptedException {
        if (webDriver != null) {
            Thread.sleep(1000);
            webDriver.close();
        }
    }
}
