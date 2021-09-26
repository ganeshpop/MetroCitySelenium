package com.metro.testcases;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.metro.pages.*;
import com.metro.utilities.ExcelReader;
import com.metro.utilities.PropertyReader;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class SwipeOutTestCase {
    public ExtentHtmlReporter htmlReporter;
    public ExtentReports extent;
    public ExtentTest logger;
    public WebDriver webDriver;

    @BeforeTest
    public void startReport() {
        htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/test-output/SwipeOutReport.html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        extent.setSystemInfo("Host Name", "SoftwareTesting");
        extent.setSystemInfo("Environment", "Web Automation Testing");
        extent.setSystemInfo("User Name", "Sai Ganesh");
        htmlReporter.config().setDocumentTitle("City Metro Web Application Swipe Out Test Report");
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

    @DataProvider(name = "validSwipeOutDetails")
    public static Object[][] validSwipeOutDetails() {
        String filePath = System.getProperty("user.dir") + "/src/com/metro/testdata";
        String fileName = "MetroSwipeOutTestData.xlsx";
        String sheetName = "ValidSwipeOutDetails";
        return ExcelReader.readCredentials(filePath, fileName, sheetName);
    }


    @Test(dataProvider = "validSwipeOutDetails", priority = 1, testName = "Valid Swipe Out")
    public void validSwipeOut(String cardId, String password) throws InterruptedException {
        logger = extent.createTest("Verify Valid Swipe Out Attempt");
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
        String fileName = "MetroSwipeOutTestData.xlsx";
        String sheetName = "InvalidSwipeOutDetails";
        return ExcelReader.readCredentials(filePath, fileName, sheetName);
    }

    @Test(dataProvider = "invalidSwipeOutDetails", priority = 5, testName = "Invalid Swipe Out")
    public void InvalidSwipeOut(String cardId, String password) throws InterruptedException {
        logger = extent.createTest("Verify Invalid Swipe Out Attempt");
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
