package utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BrowserActions {
    private WebDriver driver;
    private WebDriverWait wait;


    public BrowserActions(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void navigateToUrl(String url) {
        try {
            LoggerClass.logStep("[Browser Action] Navigating to URL: " + url);
            driver.get(url);
            wait.until(webDriver -> ((JavascriptExecutor) webDriver)
                    .executeScript("return document.readyState").equals("complete"));
        } catch (TimeoutException e) {
            LoggerClass.logStep("Page load timed out: " + e.getMessage());
        } catch (Exception e) {
            LoggerClass.logStep("Failed to navigate: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void closeAllOpenedBrowserWindows() {
        LoggerClass.logStep("[Browser Action] Closing all browser windows");
        if (driver != null) {
            try {
                driver.quit();
            } catch (WebDriverException e) {
                LoggerClass.logMessage("Error while quitting WebDriver: " + e.getMessage());
            }
        } else {
            LoggerClass.logMessage("No browser windows to close. WebDriver is already null.");
        }
    }

    public void maximizeWindow() {
        try {
            LoggerClass.logStep("[Browser Action] Maximizing browser window");
            driver.manage().window().maximize();
        } catch (Exception e) {
            LoggerClass.logMessage("Error maximizing window: " + e.getMessage());
        }
    }

    public void setWindowResolution(int width, int height) {
        try {
            LoggerClass.logStep("[Browser Action] Setting window resolution: Width = " + width + ", Height = " + height);
            driver.manage().window().setSize(new Dimension(width, height));
        } catch (Exception e) {
            LoggerClass.logMessage("Error setting window resolution: " + e.getMessage());
        }
    }


}
