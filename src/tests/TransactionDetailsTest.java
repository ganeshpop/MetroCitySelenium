package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

public class TransactionDetailsTest {
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
        cardIdTextBox.sendKeys("1009");
        WebElement passwordTextBox = webDriver.findElement(By.id("password"));
        passwordTextBox.clear();
        passwordTextBox.sendKeys("password");
        webDriver.findElement(By.className("button-block")).click(); //Login Button
        Thread.sleep(2000);
        webDriver.findElement(By.cssSelector("body > nav > ul > li:nth-child(2) > a")).click(); //Transactions Button
        Thread.sleep(1000);
        try {
            //Transaction Details
            List<WebElement> transactions = webDriver.findElements(By.xpath("/html/body/div/div/main/div/div/div/div/div/div[2]/table/tbody/tr"));
            System.out.println(webDriver.findElement(By.xpath("/html/body/div/div/main/div/div/div/div/div/div[1]/table/thead/tr")).getText());
            for (WebElement transaction : transactions) {
                System.out.println("=====================================================================================================================");
                System.out.println(transaction.getText());
                System.out.println("=====================================================================================================================");
            }
            Thread.sleep(2000);
            webDriver.findElement(By.cssSelector("body > nav > ul > li:nth-child(1) > a")).click(); //Home Button
        } catch (Exception exception) {
            Thread.sleep(3000);
            exception.printStackTrace();
        }

        //Logout
        Thread.sleep(1000);
        webDriver.findElement(By.cssSelector("body > nav > ul > li:nth-child(8) > a")).click(); //Logout Button
        Thread.sleep(2000);
        webDriver.close();
    }

}
