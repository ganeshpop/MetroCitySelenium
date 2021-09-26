package com.metro.testcases;

import com.metro.pages.*;
import com.metro.utilities.ExcelReader;
import com.metro.utilities.PropertyReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;

public class RechargeTestCase {
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

    @DataProvider(name = "validRechargeDetails")
    public static Object[][] validRechargeDetails() {
        String filePath = System.getProperty("user.dir") + "/src/com/metro/testdata";
        String fileName  = "MetroRechargeTestData.xlsx";
        String sheetName = "ValidRechargeDetails";
        return ExcelReader.readRechargeDetails(filePath, fileName, sheetName);
    }


    @Test(dataProvider = "validRechargeDetails", priority = 1)
    public void rechargeCardWithValidAmount(String cardId, String password, String amount) throws InterruptedException {
        LoginPage loginPage = new LoginPage(webDriver);
        NavigationBar navigationBar = new NavigationBar(webDriver);
        RechargePage rechargePage = new RechargePage(webDriver);
        RechargeStatusPage rechargeStatusPage = new RechargeStatusPage(webDriver);
        Thread.sleep(1000);
        loginPage.setCardId(cardId);
        loginPage.setPassword(password);
        loginPage.clickLoginButton();
        navigationBar.clickRechargeButton(); //Recharge Button
        Thread.sleep(1000);
        int updatedBalance = rechargePage.getCurrentBalance() + Integer.parseInt(amount);
        rechargePage.setAmount(amount);
        Thread.sleep(1000);
        rechargePage.clickRechargeButton();
        Assert.assertEquals(updatedBalance, rechargeStatusPage.getUpdatedBalance());
        Thread.sleep(1000);
        navigationBar.clickLogoutButton();
    }


    @DataProvider(name = "invalidRechargeDetails")
    public static Object[][] invalidRechargeDetails() {
        String filePath = System.getProperty("user.dir") + "/src/com/metro/testdata";
        String fileName  = "MetroRechargeTestData.xlsx";
        String sheetName = "InvalidRechargeDetails";
        return ExcelReader.readRechargeDetails(filePath, fileName, sheetName);
    }


    @Test(dataProvider = "invalidRechargeDetails", priority = 5)
    public void rechargeCardWithInvalidAmount(String cardId, String password, String amount) throws InterruptedException {
        LoginPage loginPage = new LoginPage(webDriver);
        NavigationBar navigationBar = new NavigationBar(webDriver);
        RechargePage rechargePage = new RechargePage(webDriver);
        RechargeStatusPage rechargeStatusPage = new RechargeStatusPage(webDriver);
        Thread.sleep(1000);
        loginPage.setCardId(cardId);
        loginPage.setPassword(password);
        loginPage.clickLoginButton();
        navigationBar.clickRechargeButton(); //Recharge Button
        Thread.sleep(1000);
        rechargePage.setAmount(amount);
        Thread.sleep(1000);
        rechargePage.clickRechargeButton();
        String message = rechargeStatusPage.getErrorMessage();
        System.out.println(message);
        Assert.assertTrue(message.equalsIgnoreCase("Must Be Equal or Greater than 1")
                || message.equalsIgnoreCase("Must Be Equal or Less Than 1000"));
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
