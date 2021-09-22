package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class RechargeTest {
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
        webDriver.findElement(By.cssSelector("body > nav > ul > li:nth-child(4) > a")).click(); //Recharge Button
        Thread.sleep(1000);
        try {
            //Amount
            WebElement amountTextBox = webDriver.findElement(By.xpath("/html/body/div/div/main/section/div/div/div[2]/div/div/div[1]/ul/form/li/span/input"));
            amountTextBox.clear();
            amountTextBox.sendKeys("100");
            webDriver.findElement(By.cssSelector("body > div > div > main > section > div > div > div.pricing-tables-wrap > div > div > div.pricing-table-cta.mb-8 > input")).click(); //Recharge Button
            Thread.sleep(2000);
            webDriver.findElement(By.cssSelector("body > div > div > main > section > div > div > div.pricing-tables-wrap > div > div > div.pricing-table-cta.mb-8 > a")).click(); //GoHome Button
            System.out.println("Recharge Successful");

        } catch (Exception exception) {
            Thread.sleep(3000);
            exception.printStackTrace();
        }
        Thread.sleep(2000);

        //Logout
        webDriver.findElement(By.cssSelector("body > nav > ul > li:nth-child(8) > a")).click(); //Logout Button
        Thread.sleep(2000);
        webDriver.close();
    }

}
