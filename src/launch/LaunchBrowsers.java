package launch;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class LaunchBrowsers {
    static String browser = "chrome";
    static WebDriver webDriver;

    public static void main(String[] args) {
        switch (browser) {
            case "chrome":
                System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
                ChromeOptions chromeOptions = new ChromeOptions();
//                chromeOptions.setHeadless(true);
                chromeOptions.setHeadless(false);
                webDriver = new ChromeDriver(chromeOptions);
                break;
            case "firefox":
                System.setProperty("webdriver.gecko.driver", "./drivers/geckodriver.exe");
                webDriver = new FirefoxDriver();
                break;
            case "ie":
                System.setProperty("webdriver.ie.driver", "./drivers/IEDriverServer.exe");
                webDriver = new InternetExplorerDriver();
                break;
            default:
                System.out.println("Invalid Browser");
                break;
        }
        webDriver.get("https://citymetro.azurewebsites.net");
        System.out.println("Web Driver Title: " + webDriver.getTitle());
        webDriver.close();


    }
}

