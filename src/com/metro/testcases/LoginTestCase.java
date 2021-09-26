package com.metro.testcases;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.metro.pages.HomePage;
import com.metro.pages.LoginPage;
import com.metro.pages.MenuPage;
import com.metro.pages.NavigationBar;
import com.metro.utilities.ExcelReader;
import com.metro.utilities.PropertyReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.IOException;

public class LoginTestCase {
    public ExtentHtmlReporter htmlReporter;
    public ExtentReports extent;
    public WebDriver webDriver;

    @BeforeTest
    public void startReport(){
        htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") +"/test-output/STMExtentReport.html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        extent.setSystemInfo("Host Name", "SoftwareTesting");
        extent.setSystemInfo("Environment", "Automation Testing");
        extent.setSystemInfo("User Name", "TestEngineer");
        htmlReporter.config().setDocumentTitle("City Metro Web Application Test Report");
        htmlReporter.config().setReportName("Selenium Regression Test Suite");
        htmlReporter.config().setTheme(Theme.STANDARD);
    }

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

    @DataProvider(name = "validLoginDetails")
    public Object[][] validLoginDetails() {
        String filePath = System.getProperty("user.dir") + "/src/com/metro/testdata";
        String fileName  = "MetroLoginTestData.xlsx";
        String sheetName = "ValidLoginDetails";
        return ExcelReader.readCredentials(filePath, fileName, sheetName);
    }

    @Test(dataProvider = "validLoginDetails", priority = 1)
    public void loginValidUser(String cardId, String password) throws InterruptedException {
        LoginPage loginPage = new LoginPage(webDriver);
        MenuPage menuPage = new MenuPage(webDriver);
        NavigationBar navigationBar = new NavigationBar(webDriver);
        Thread.sleep(1000);
        loginPage.setCardId(cardId);
        loginPage.setPassword(password);
        loginPage.clickLoginButton();
        Thread.sleep(1000);
        String message = menuPage.getMessage();
        Assert.assertEquals(message, "Welcome To City Metro User " + cardId, "Invalid Credentials");
        navigationBar.clickLogoutButton();
    }

    @DataProvider(name = "invalidLoginDetails")
    public Object[][] invalidLoginDetails() {
        String filePath = System.getProperty("user.dir") + "/src/com/metro/testdata";
        String fileName  = "MetroLoginTestData.xlsx";
        String sheetName = "InvalidLoginDetails";
        return ExcelReader.readCredentials(filePath, fileName, sheetName);
    }


    @Test(dataProvider = "invalidLoginDetails", priority = 5)
    public void loginInvalidUser(String cardId, String password) throws InterruptedException {
        LoginPage loginPage = new LoginPage(webDriver);
        MenuPage menuPage = new MenuPage(webDriver);
        Thread.sleep(1000);
        loginPage.setCardId(cardId);
        loginPage.setPassword(password);
        loginPage.clickLoginButton();
        Thread.sleep(1000);
        String message = menuPage.getMessage();
        Assert.assertTrue(message.equalsIgnoreCase("Invalid Card") || message.equalsIgnoreCase("Invalid Password, Try Again"));
    }

    @AfterMethod
    public void closeBrowser() throws InterruptedException {
        if (webDriver != null) {
            Thread.sleep(1000);
            webDriver.close();
        }
    }

    @AfterTest
    public void stopTest(){
        extent.flush();
    }
}
