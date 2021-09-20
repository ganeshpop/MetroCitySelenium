package launch;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RechargeLoopTest {
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
        for (int i = 0; i < 3; i++){
            WebElement headerRechargeButton = webDriver.findElement(By.cssSelector("body > nav > ul > li:nth-child(4) > a"));
            headerRechargeButton.click();
            Thread.sleep(500);
            WebElement amountTextBox = webDriver.findElement(By.xpath("/html/body/div/div/main/section/div/div/div[2]/div/div/div[1]/ul/form/li/span/input"));
            amountTextBox.clear();
            amountTextBox.sendKeys("1000");
            WebElement rechargeButton = webDriver.findElement(By.cssSelector("body > div > div > main > section > div > div > div.pricing-tables-wrap > div > div > div.pricing-table-cta.mb-8 > input"));
            rechargeButton.click();
        }
        Thread.sleep(5000);
        webDriver.close();
    }

}
