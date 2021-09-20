package launch;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class RechargeTest {
    static WebDriver webDriver;

    public static void main(String[] args) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setHeadless(false);
        webDriver = new ChromeDriver(chromeOptions);
        webDriver.manage().window().maximize();
        webDriver.get("https://citymetro.azurewebsites.net");
        WebElement loginButton = webDriver.findElement(By.className("button-primary"));
        loginButton.click();
        Thread.sleep(2000);
        WebElement cardIdTextBox = webDriver.findElement(By.id("cardId"));
        cardIdTextBox.clear();
        cardIdTextBox.sendKeys("1011");
        WebElement passwordTextBox = webDriver.findElement(By.id("password"));
        passwordTextBox.clear();
        passwordTextBox.sendKeys("1011");
        WebElement loginButtonInLoginPage = webDriver.findElement(By.className("button-block"));
        loginButtonInLoginPage.click();
        Thread.sleep(2000);
        WebElement headerRechargeButton = webDriver.findElement(By.cssSelector("body > nav > ul > li:nth-child(4) > a"));
        headerRechargeButton.click();
        Thread.sleep(1000);
        try {
            WebElement amountTextBox = webDriver.findElement(By.xpath("/html/body/div/div/main/section/div/div/div[2]/div/div/div[1]/ul/form/li/span/input"));
            amountTextBox.clear();
            amountTextBox.sendKeys("100");
            WebElement rechargeButton = webDriver.findElement(By.cssSelector("body > div > div > main > section > div > div > div.pricing-tables-wrap > div > div > div.pricing-table-cta.mb-8 > input"));
            rechargeButton.click();
        } catch (Exception exception) {
            Thread.sleep(3000);
            exception.printStackTrace();
        }
        Thread.sleep(2000);
        WebElement logoutButton = webDriver.findElement(By.cssSelector("body > nav > ul > li:nth-child(8) > a"));
        logoutButton.click();
        Thread.sleep(2000);
        webDriver.close();
    }

}
