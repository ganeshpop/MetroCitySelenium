package testng;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class RechargeTestCase {
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

    @DataProvider(name = "rechargeDetails")
    public static Object[][] rechargeDetails() {
        return new Object[][]{{"1009", "password", 100}, {"1010", "password", 50}, {"1011", "1011", 10}};
    }


    @Test(dataProvider = "rechargeDetails", priority = 1)
    public void rechargeCard(String cardId, String password, int amount)  {
        webDriver.findElement(By.className("button-primary")).click(); //Login Button
//        Thread.sleep(2000);
        WebElement cardIdTextBox = webDriver.findElement(By.id("cardId"));
        cardIdTextBox.clear();
        cardIdTextBox.sendKeys(cardId);
        WebElement passwordTextBox = webDriver.findElement(By.id("password"));
        passwordTextBox.clear();
        passwordTextBox.sendKeys(password);
        webDriver.findElement(By.className("button-block")).click(); //Login Button
        webDriver.findElement(By.cssSelector("body > nav > ul > li:nth-child(4) > a")).click(); //Recharge Button
//        Thread.sleep(2000);
        WebDriverWait webDriverWait = new WebDriverWait(webDriver, 10);
        try {
            webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("/html/body/div/div/main/section/div/div/div[2]/div/div/div[1]/div/div/span[2]")));
            int updatedBalance = Integer.parseInt(webDriver.findElement(By.xpath("/html/body/div/div/main/section/div/div/div[2]/div/div/div[1]/div/div/span[2]")).getText()) + amount;
            WebElement amountTextBox = webDriver.findElement(By.xpath("/html/body/div/div/main/section/div/div/div[2]/div/div/div[1]/ul/form/li/span/input"));
            amountTextBox.clear();
            amountTextBox.sendKeys(String.valueOf(amount));
            System.out.println("Updated balance " + updatedBalance);
//            Thread.sleep(2000);
            webDriver.findElement(By.cssSelector("body > div > div > main > section > div > div > div.pricing-tables-wrap > div > div > div.pricing-table-cta.mb-8 > input")).click();
            Assert.assertEquals(updatedBalance, Integer.parseInt(webDriver.findElement(By.xpath("/html/body/div/div/main/section/div/div/div[2]/div/div/div[1]/div/div/span[2]")).getText()));
        } catch (Exception e) {
            e.printStackTrace();
        }
//        Thread.sleep(2000);
        webDriver.findElement(By.cssSelector("body > nav > ul > li:nth-child(8) > a")).click(); //Logout Button
    }

    @AfterMethod
    public void closeBrowser()  {
        if (webDriver != null) {
//            Thread.sleep(2000);
            webDriver.close();
        }
    }
}
