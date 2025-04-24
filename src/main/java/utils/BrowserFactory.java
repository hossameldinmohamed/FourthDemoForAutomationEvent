package utils;

import io.qameta.allure.Step;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class BrowserFactory {
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static String browserTypeProperty = PropertiesReader.getProperty("browser.type", "chrome").toLowerCase();
    private static String executionTypeProperty = PropertiesReader.getProperty("execution.type", "local").toLowerCase();
    private static String remoteHost = PropertiesReader.getProperty("remote.execution.host", "localhost");
    private static String remotePort = PropertiesReader.getProperty("remote.execution.port", "4444");

    public enum BrowserType {
        FIREFOX("firefox", "mozilla firefox", "mozilla", "ff"),
        CHROME("chrome", "google chrome", "chromium");

        private final String[] aliases;

        BrowserType(String... aliases) {
            this.aliases = aliases;
        }

        public static BrowserType fromString(String value) {
            if (value == null) {
                throw new IllegalArgumentException("Browser type cannot be null.");
            }
            String normalizedValue = value.trim().toLowerCase();

            for (BrowserType type : BrowserType.values()) {
                for (String alias : type.aliases) {
                    if (alias.equalsIgnoreCase(normalizedValue)) {
                        return type;
                    }
                }
            }
            throw new IllegalArgumentException("Invalid browser type: " + value);
        }
    }


    public enum ExecutionType {
        LOCAL("local"), REMOTE("remote"), HEADLESS("headless");

        private final String value;

        ExecutionType(String type) {
            this.value = type;
        }

        protected String getValue() {
            return value;
        }
    }

    public static WebDriver getBrowser() {
        return getBrowser(BrowserType.fromString(browserTypeProperty),
                ExecutionType.valueOf(executionTypeProperty.toUpperCase()));

    }

    @Step("Initializing a new Web Browser")
    public static synchronized WebDriver getBrowser(BrowserType browserType, ExecutionType executionType) {
        LoggerClass.logStep("Initializing [" + browserType.name() + "] with execution type [" + executionType.getValue() + "]");

        ITestResult result = Reporter.getCurrentTestResult();
        ITestContext context = result.getTestContext();

        try {
            if (executionType == ExecutionType.REMOTE) {
                initializeRemoteWebDriver(browserType, context);
            } else {
                initializeLocalWebDriver(browserType, executionType, context);
            }
        } catch (Exception e) {
            LoggerClass.logMessage("Error initializing WebDriver: " + e.getMessage());
            throw new RuntimeException(e);
        }

        return driver.get();
    }

    private static void initializeRemoteWebDriver(BrowserType browserType, ITestContext context) throws MalformedURLException {
        URL remoteUrl = new URL("http://" + remoteHost + ":" + remotePort + "/wd/hub");

        if (browserType == BrowserType.CHROME) {
            driver.set(new RemoteWebDriver(remoteUrl, getChromeOptions()));
        } else if (browserType == BrowserType.FIREFOX) {
            driver.set(new RemoteWebDriver(remoteUrl, getFirefoxOptions()));
        } else {
            throw new IllegalArgumentException("Unsupported browser type for remote execution.");
        }

        context.setAttribute("driver", driver.get());
        Helper.implicitWait(driver.get());
    }

    private static void initializeLocalWebDriver(BrowserType browserType, ExecutionType executionType, ITestContext context) {
        if (browserType == BrowserType.CHROME) {
            ChromeOptions options = getChromeOptions();
            if (executionType == ExecutionType.HEADLESS) {
                options.addArguments("--headless");
            }
            driver.set(new ChromeDriver(options));
        } else if (browserType == BrowserType.FIREFOX) {
            FirefoxOptions options = getFirefoxOptions();
            if (executionType == ExecutionType.HEADLESS) {
                options.addArguments("--headless");
            }
            driver.set(new FirefoxDriver(options));
        } else {
            throw new IllegalArgumentException("Unsupported browser type for local execution.");
        }

        context.setAttribute("driver", driver.get());
        Helper.implicitWait(driver.get());
        BrowserActions browserActions = new BrowserActions(driver.get());

        if ("true".equalsIgnoreCase(PropertiesReader.getProperty("maximize"))) {
            browserActions.maximizeWindow();
        } else {
            int width = Integer.parseInt(PropertiesReader.getProperty("width", "1920"));
            int height = Integer.parseInt(PropertiesReader.getProperty("height", "1080"));
            browserActions.setWindowResolution(width, height);
        }
    }

    private static ChromeOptions getChromeOptions() {
        ChromeOptions options = new ChromeOptions();

        // Disable automation info bars and extensions
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        options.addArguments("--disable-blink-features=AutomationControlled");

        // Disable password saving and autofill - this should help with the password change popups
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        prefs.put("autofill.profile_enabled", false);
        prefs.put("profile.default_content_setting_values.notifications", 2);
        options.setExperimentalOption("prefs", prefs);

        // Standard Chrome arguments for stability and performance
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--disable-infobars");
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-application-cache");
        options.addArguments("--remote-allow-origins=*");

        return options;
    }

    private static FirefoxOptions getFirefoxOptions() {
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--window-size=1920,1080");
        return options;
    }
}
