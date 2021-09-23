package testng;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LoginTestCase {
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

    @DataProvider(name = "validLoginDetails")
    public static Object[][] validLoginDetails() {
        return new Object[][]{{"1009", "password"}, {"1010", "password"} /*, {"1011", "1011"}*/};
    }


    @Test(dataProvider = "validLoginDetails", priority = 1)
    public void loginValidUser(String cardId, String password) throws InterruptedException {
        webDriver.findElement(By.className("button-primary")).click(); //Login Button
        Thread.sleep(2000);
        WebElement cardIdTextBox = webDriver.findElement(By.id("cardId"));
        cardIdTextBox.clear();
        cardIdTextBox.sendKeys(cardId);
        WebElement passwordTextBox = webDriver.findElement(By.id("password"));
        passwordTextBox.clear();
        passwordTextBox.sendKeys(password);
        webDriver.findElement(By.className("button-block")).click(); //Login Button
        Thread.sleep(2000);
        String message = webDriver.findElement(By.cssSelector("body > div > div > main > section > div > div > div.pricing-header.text-center > h2")).getText();
        Assert.assertEquals(message,"Welcome To City Metro User " + cardId, "Invalid Credentials");
        webDriver.findElement(By.cssSelector("body > nav > ul > li:nth-child(8) > a")).click(); //Logout Button
    }

    @DataProvider(name = "invalidLoginDetails")
    public static Object[][] invalidLoginDetails() {
        return new Object[][]{{"1109", "password"}, {"1010", "pass"} /*, {"1011", "password"}*/};
    }


    @Test(dataProvider = "invalidLoginDetails", priority = 5)
    public void loginInvalidUser(String cardId, String password) throws InterruptedException {
        webDriver.findElement(By.className("button-primary")).click(); //Login Button
        Thread.sleep(2000);
        WebElement cardIdTextBox = webDriver.findElement(By.id("cardId"));
        cardIdTextBox.clear();
        cardIdTextBox.sendKeys(cardId);
        WebElement passwordTextBox = webDriver.findElement(By.id("password"));
        passwordTextBox.clear();
        passwordTextBox.sendKeys(password);
        webDriver.findElement(By.className("button-block")).click(); //Login Button
        Thread.sleep(2000);
        String message = webDriver.findElement(By.cssSelector("body > div > div > main > section > div > div > div.pricing-header.text-center > h2")).getText();
        Assert.assertTrue(message.equalsIgnoreCase("Invalid Card") || message.equalsIgnoreCase("Invalid Password, Try Again"));
    }

    @AfterMethod
    public void closeBrowser() throws InterruptedException {
        if (webDriver != null) {
            Thread.sleep(2000);
            webDriver.close();
        }
    }
}
