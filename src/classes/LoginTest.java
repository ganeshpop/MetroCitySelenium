package classes;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class LoginTest {
    static WebDriver webDriver;

    public static void main(String[] args) throws InterruptedException {
        //Chrome Driver
        System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setHeadless(false);
        webDriver = new ChromeDriver(chromeOptions);
        webDriver.manage().window().maximize();

        //Home Page
        webDriver.get("https://citymetro.azurewebsites.net");

        //Login
        webDriver.findElement(By.className("button-primary")).click(); //Login Button
        Thread.sleep(2000);
        WebElement cardIdTextBox = webDriver.findElement(By.id("cardId"));
        cardIdTextBox.clear();
        cardIdTextBox.sendKeys("1010");
        WebElement passwordTextBox = webDriver.findElement(By.id("password"));
        passwordTextBox.clear();
        passwordTextBox.sendKeys("password");
        webDriver.findElement(By.className("button-block")).click(); //Login Button
        Thread.sleep(2000);

        //Logout
        webDriver.findElement(By.cssSelector("body > nav > ul > li:nth-child(8) > a")).click(); //Logout Button
        Thread.sleep(2000);
        webDriver.close();
    }

}
