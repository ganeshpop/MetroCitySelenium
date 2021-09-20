package launch;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginTest {
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
        cardIdTextBox.sendKeys("1010");
        WebElement passwordTextBox = webDriver.findElement(By.id("password"));
        passwordTextBox.clear();
        passwordTextBox.sendKeys("password");
        WebElement loginButtonInLoginPage = webDriver.findElement(By.className("button-block"));
        loginButtonInLoginPage.click();
        Thread.sleep(2000);
        WebDriverWait webDriverWait = new WebDriverWait(webDriver, 10);
        try {
            webDriverWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("body > nav > ul > li:nth-child(8) > a")));
            WebElement logoutButton = webDriver.findElement(By.cssSelector("body > nav > ul > li:nth-child(8) > a"));
            logoutButton.click();
        } catch (Exception exception){
            exception.printStackTrace();
        }
        Thread.sleep(2000);
        webDriver.close();
    }

}
