package testng;

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

public class SwipeOutTestCase {
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

    @DataProvider(name = "validSwipeOutDetails")
    public static Object[][] validSwipeOutDetails() {
        return new Object[][]{{"1009", "password"}, {"1010", "password"}, /*{"1011", "1011"}*/};
    }


    @Test(dataProvider = "validSwipeOutDetails", priority = 1, testName = "Valid Swipe Out")
    public void validSwipeOut(String cardId, String password) throws InterruptedException {
        webDriver.findElement(By.className("button-primary")).click(); //Login Button
        Thread.sleep(2000);
        WebElement cardIdTextBox = webDriver.findElement(By.id("cardId"));
        cardIdTextBox.clear();
        cardIdTextBox.sendKeys(cardId);
        WebElement passwordTextBox = webDriver.findElement(By.id("password"));
        passwordTextBox.clear();
        passwordTextBox.sendKeys(password);
        webDriver.findElement(By.className("button-block")).click(); //Login Button
        Thread.sleep(1000);
        webDriver.findElement(By.cssSelector("body > nav > ul > li:nth-child(6) > a")).click(); //SwipeOut Button
        Thread.sleep(1000);
        int randomNumber = ThreadLocalRandom.current().nextInt(0, 4 + 1);
        List<WebElement> stationList = webDriver.findElements(By.className("rad-label")); //Get Stations
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", stationList.get(randomNumber)); //Select a Random Station
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", webDriver.findElement(By.className("button-block"))); // SwipeOut Button
        new WebDriverWait(webDriver, 10)
                .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("body > div > div > main > section > div > div > div.pricing-tables-wrap > div > div > div.pricing-table-main > div > h4:nth-child(1)")));
        String message = webDriver.findElement(By.cssSelector("body > div > div > main > section > div > div > div.pricing-tables-wrap > div > div > div.pricing-table-main > div > h4:nth-child(1)")).getText();
        Assert.assertEquals(message, "Swipe Out Successful", "Swipe Out Not Possible");
        Thread.sleep(2000);
        webDriver.findElement(By.cssSelector("body > nav > ul > li:nth-child(8) > a")).click(); //Logout Button
    }

    @DataProvider(name = "invalidSwipeOutDetails")
    public static Object[][] invalidSwipeOutDetails() {
        return new Object[][]{{"1006", "password"}, {"1007", "password"}, /*{"1008", "password"}*/};
    }

    @Test(dataProvider = "invalidSwipeOutDetails", priority = 5, testName = "Invalid Swipe Out")
    public void InvalidSwipeOut(String cardId, String password) throws InterruptedException {
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
        webDriver.findElement(By.cssSelector("body > nav > ul > li:nth-child(6) > a")).click(); //SwipeOut Button
        Thread.sleep(1000);
        int randomNumber = ThreadLocalRandom.current().nextInt(0, 4 + 1);
        List<WebElement> stationList = webDriver.findElements(By.className("rad-label")); //Get Stations
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", stationList.get(randomNumber)); //Select a Random Station
        ((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", webDriver.findElement(By.className("button-block"))); // SwipeOut Button
        new WebDriverWait(webDriver, 10)
                .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("body > div > div > main > section > div > div > div.pricing-tables-wrap > div > div > div > div > h4")));
        String message = webDriver.findElement(By.cssSelector("body > div > div > main > section > div > div > div.pricing-tables-wrap > div > div > div > div > h4")).getText();
        Assert.assertEquals(message, "You Have not Swiped In at any Station!", "Swipe Out Possible");
        Thread.sleep(2000);
        webDriver.findElement(By.cssSelector("body > nav > ul > li:nth-child(8) > a")).click(); //Logout Button
    }


    @AfterMethod
    public void closeBrowser() throws InterruptedException {
        if (webDriver != null) {
            Thread.sleep(2000);
            webDriver.close();
        }
    }
}
