package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Random;

public class Helper {

    private static final int DEFAULT_TIMEOUT = 10; // Default wait time in seconds

    private static int getTimeout() {
        try {
            String timeoutProperty = System.getProperty("webdriver.wait");
            return (timeoutProperty != null) ? Integer.parseInt(timeoutProperty) : DEFAULT_TIMEOUT;
        } catch (NumberFormatException e) {
            LoggerClass.logStep("Invalid wait time format, using default: " + DEFAULT_TIMEOUT + " seconds.");
            return DEFAULT_TIMEOUT;
        }
    }

    public static WebDriverWait getExplicitWait(WebDriver driver) {
        return new WebDriverWait(driver, Duration.ofSeconds(getTimeout()));
    }

    public static void implicitWait(WebDriver driver) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(getTimeout()));
    }

    public static String getCurrentTime(String dateFormat) {
        try {
            return new SimpleDateFormat(dateFormat).format(new Date());
        } catch (IllegalArgumentException e) {
            LoggerClass.logStep("Invalid date format: " + e.getMessage());
            throw new RuntimeException("Invalid date format: " + dateFormat, e);
        }
    }

    public static String getCurrentTime() {
        return getCurrentTime("ddMMyyyyHHmmssSSS");
    }

    public static int getRandomNumberBetweenTwoValues(int lowValue, int highValue) {
        if (lowValue >= highValue) {
            throw new IllegalArgumentException("Low value must be less than high value.");
        }
        return new Random().nextInt(highValue - lowValue) + lowValue;
    }

    public static String getRandomNumberBetweenTwoValuesAsString(int lowValue, int highValue) {
        return String.valueOf(getRandomNumberBetweenTwoValues(lowValue, highValue));
    }
}
