package config;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;

public class DriverFactory {
    public static WebDriver getDriver(String browser) {
        WebDriver driver = null;
        if (browser.equalsIgnoreCase("chrome")) {
            driver = new ChromeDriver(); // Selenium 4 ya gestiona los drivers automáticamente
        } else if (browser.equalsIgnoreCase("edge")) {
            driver = new EdgeDriver();
        }
        if(driver != null){
            driver.manage().window().maximize();
        }
        return driver;
    }
}