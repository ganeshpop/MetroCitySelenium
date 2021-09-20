package launch;

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
        webDriver.findElement(By.className("button-block")).click();
        Thread.sleep(1000);
        webDriver.findElement(By.cssSelector("body > nav > ul > li:nth-child(6) > a")).click();
        Thread.sleep(1000);
        try {
            int randomNumber = ThreadLocalRandom.current().nextInt(0, 4 + 1);
            List<WebElement> stationList = webDriver.findElements(By.className("rad-label"));
            System.out.println("Selected Station " + stationList.get(randomNumber).getText());
            ((JavascriptExecutor)webDriver).executeScript("arguments[0].click();", stationList.get(randomNumber));
            ((JavascriptExecutor)webDriver).executeScript("arguments[0].click();", webDriver.findElement(By.className("button-block")));
        } catch (Exception exception) {
            Thread.sleep(3000);
            exception.printStackTrace();
        }
        Thread.sleep(20000);
        webDriver.findElement(By.cssSelector("body > nav > ul > li:nth-child(8) > a")).click();
        Thread.sleep(2000);
        webDriver.close();
    }

}
