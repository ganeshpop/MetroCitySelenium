package com.metro.testcases;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.metro.pages.HomePage;
import com.metro.pages.LoginPage;
import com.metro.pages.MenuPage;
import com.metro.pages.NavigationBar;
import com.metro.utilities.ExcelReader;
import com.metro.utilities.PropertyReader;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LoginTestCase {
    public ExtentHtmlReporter htmlReporter;
    public ExtentReports extent;
    public ExtentTest logger;
    public WebDriver webDriver;


    @BeforeTest
    public void startReport() {
        htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/test-output/LoginReport.html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        extent.setSystemInfo("Host Name", "SoftwareTesting");
        extent.setSystemInfo("Environment", "Web Automation Testing");
        extent.setSystemInfo("User Name", "Sai Ganesh");
        htmlReporter.config().setDocumentTitle("City Metro Web Application Login Test Report");
        htmlReporter.config().setReportName("Selenium Regression Test Suite");
        htmlReporter.config().setTheme(Theme.STANDARD);
    }

    @BeforeMethod
    public void openBrowser() throws IOException {
        System.setProperty("webdriver.chrome.driver", PropertyReader.ReadProperty("chrome_driver_path"));
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setHeadless(true);
        webDriver = new ChromeDriver(chromeOptions);
        webDriver.manage().window().maximize();
        webDriver.get(PropertyReader.ReadProperty("app_url"));
        HomePage homePage = new HomePage(webDriver);
        homePage.clickLoginButton();
    }

    @DataProvider(name = "validLoginDetails")
    public Object[][] validLoginDetails() {
        String filePath = System.getProperty("user.dir") + "/src/com/metro/testdata";
        String fileName = "MetroLoginTestData.xlsx";
        String sheetName = "ValidLoginDetails";
        return ExcelReader.readCredentials(filePath, fileName, sheetName);
    }

    @Test(dataProvider = "validLoginDetails", priority = 1)
    public void loginValidUser(String cardId, String password) throws InterruptedException {
        logger = extent.createTest("Verify Valid Login Attempt");
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
        String fileName = "MetroLoginTestData.xlsx";
        String sheetName = "InvalidLoginDetails";
        return ExcelReader.readCredentials(filePath, fileName, sheetName);
    }


    @Test(dataProvider = "invalidLoginDetails", priority = 5)
    public void loginInvalidUser(String cardId, String password) throws InterruptedException {
        logger = extent.createTest("Verify Invalid Login Attempt");
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

    public String getScreenshot(WebDriver driver, String ScreenshotName) throws IOException {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        String destination = System.getProperty("user.dir") + "/Screenshots/" + ScreenshotName + dateName + ".png";
        FileUtils.copyFile(source, new File(destination));
        return destination;
    }

    @AfterMethod
    public void CloseBrowser(ITestResult result) throws IOException {
        if (result.getStatus() == ITestResult.FAILURE) {
            logger.log(Status.FAIL, MarkupHelper.createLabel(result.getName() + " - Test Case Failed ", ExtentColor.RED));
            logger.log(Status.FAIL, MarkupHelper.createLabel(result.getThrowable() + " - Test Case Failed ", ExtentColor.RED));
            logger.fail("Test Case Failed Snapshot is below - " + logger.addScreenCaptureFromPath(getScreenshot(webDriver, result.getName())));
        } else if (result.getStatus() == ITestResult.SKIP) {
            logger.log(Status.SKIP, MarkupHelper.createLabel(result.getName() + " - Test Case Skipped ", ExtentColor.ORANGE));
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            logger.log(Status.PASS, MarkupHelper.createLabel(result.getName() + " - Test Case Passed ", ExtentColor.GREEN));
        }
        webDriver.quit();

    }

    @AfterTest
    public void stopTest() {
        extent.flush();
    }

}
