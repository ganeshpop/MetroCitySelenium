package com.metro.testcases;

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

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class SwipeInTestCase {
    WebDriver webDriver;

    @BeforeMethod
    public void openBrowser() {
        System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setHeadless(false);
        webDriver = new ChromeDriver(chromeOptions);
        webDriver.manage().window().maximize();
        webDriver.get("https://citymetro.azurewebsites.net");
    }

    @DataProvider(name = "validSwipeInDetails")
    public static Object[][] validSwipeInDetails() {
        return new Object[][]{{"1009", "password"}, {"1010", "password"} /*, {"1011", "1011"}*/};
    }


    @Test(dataProvider = "validSwipeInDetails", priority = 1, testName = "Valid Swipe In")
    public void validSwipeIn(String cardId, String password) throws InterruptedException {
        webDriver.findElement(By.className("button-primary")).click(); //Login Button
        Thread.sleep(1000);
        WebElement cardIdTextBox = webDriver.findElement(By.id("cardId"));
        cardIdTextBox.clear();
        cardIdTextBox.sendKeys(cardId);
        WebElement passwordTextBox = webDriver.findElement(By.id("password"));
        passwordTextBox.clear();
        passwordTextBox.sendKeys(password);
        webDriver.findElement(By.className("button-block")).click(); //Login Button
        Thread.sleep(1000);
        webDriver.findElement(By.cssSelector("body > nav > ul > li:nth-child(5) > a")).click(); //SwipeIn Button
        Thread.sleep(1000);
        int randomNumber = ThreadLocalRandom.current().nextInt(0, 4 + 1);
        List<WebElement> stationList = webDriver.findElements(By.className("rad-label")); //Get Stations
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", stationList.get(randomNumber)); //Select a Random Station
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", webDriver.findElement(By.className("button-block"))); // SwipeIn Button
        new WebDriverWait(webDriver, 10)
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/div/div/main/section/div/div/div[2]/div/div/div/div[1]/h4")));
        String message = webDriver.findElement(By.xpath("/html/body/div/div/main/section/div/div/div[2]/div/div/div/div[1]/h4")).getText();
        Assert.assertEquals(message, "Swipe In Successful At Station Station " + randomNumber, "Swipe In Not Possible");
        Thread.sleep(1000);
        webDriver.findElement(By.cssSelector("body > nav > ul > li:nth-child(8) > a")).click(); //Logout Button
    }

    @DataProvider(name = "invalidSwipeInDetails")
    public static Object[][] invalidSwipeInDetails() {
        return new Object[][]{{"1003", "password"}, {"1004", "password"}  /*, {"1005", "password"}*/};
    }

    @Test(dataProvider = "invalidSwipeInDetails", priority = 5, testName = "Invalid Swipe In")
    public void InvalidSwipeIn(String cardId, String password) throws InterruptedException {
        webDriver.findElement(By.className("button-primary")).click(); //Login Button
        Thread.sleep(1000);
        WebElement cardIdTextBox = webDriver.findElement(By.id("cardId"));
        cardIdTextBox.clear();
        cardIdTextBox.sendKeys(cardId);
        WebElement passwordTextBox = webDriver.findElement(By.id("password"));
        passwordTextBox.clear();
        passwordTextBox.sendKeys(password);
        webDriver.findElement(By.className("button-block")).click(); //Login Button
        Thread.sleep(1000);
        webDriver.findElement(By.cssSelector("body > nav > ul > li:nth-child(5) > a")).click(); //SwipeIn Button
        Thread.sleep(1000);
        int randomNumber = ThreadLocalRandom.current().nextInt(0, 4 + 1);
        List<WebElement> stationList = webDriver.findElements(By.className("rad-label")); //Get Stations
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", stationList.get(randomNumber)); //Select a Random Station
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", webDriver.findElement(By.className("button-block"))); // SwipeIn Button
        new WebDriverWait(webDriver, 10)
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/div/div/main/section/div/div/div[2]/div/div/div/div[1]/h4")));
        String message = webDriver.findElement(By.xpath("/html/body/div/div/main/section/div/div/div[2]/div/div/div/div[1]/h4")).getText();
        Assert.assertEquals(message, "You have not Swiped Out from Previous Trip!", "Swipe In Possible");
        Thread.sleep(1000);
        webDriver.findElement(By.cssSelector("body > nav > ul > li:nth-child(8) > a")).click(); //Logout Button
    }


    @AfterMethod
    public void closeBrowser() throws InterruptedException {
        if (webDriver != null) {
            Thread.sleep(1000);
            webDriver.close();
        }
    }
}
