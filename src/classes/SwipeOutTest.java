package classes;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class SwipeOutTest {
    static WebDriver webDriver;

    public static void main(String[] args) throws InterruptedException {
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
        Thread.sleep(1000);
        webDriver.findElement(By.cssSelector("body > nav > ul > li:nth-child(6) > a")).click(); //SwipeOut Button
        Thread.sleep(1000);

        //SwipeOut
        try {
            int randomNumber = ThreadLocalRandom.current().nextInt(0, 4 + 1);
            List<WebElement> stationList = webDriver.findElements(By.className("rad-label")); //Get Stations
            System.out.println("Selected Swipe Out Station " + stationList.get(randomNumber).getText());
            ((JavascriptExecutor)webDriver).executeScript("arguments[0].click();", stationList.get(randomNumber)); //Select a Random Station
            ((JavascriptExecutor)webDriver).executeScript("arguments[0].click();", webDriver.findElement(By.className("button-block"))); // SwipeOut Button
        } catch (Exception exception) {
            Thread.sleep(3000);
            exception.printStackTrace();
        }
        Thread.sleep(10000);

        //Logout
        webDriver.findElement(By.cssSelector("body > nav > ul > li:nth-child(8) > a")).click(); //Logout Button
        Thread.sleep(2000);
        webDriver.close();
    }

}
