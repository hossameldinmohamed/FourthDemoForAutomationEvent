package utils;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.model.Media;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LoggerClass {
    private static final Logger logger = LogManager.getLogger(LoggerClass.class);
    private static final String SCREENSHOT_DIR = System.getProperty("user.dir") + "/test-output/screenshots/";

    static {
        File screenshotFolder = new File(SCREENSHOT_DIR);
        if (!screenshotFolder.exists()) {
            screenshotFolder.mkdirs();
        }
    }

    @Step("{message}")
    public static void logStep(String message) {
        logger.info(message);
        ExtentReport.info(message);
    }

    public static void logMessage(String message) {
        logger.info(message);
        ExtentReport.info(message);
    }

    @Attachment(value = "Full Page Screenshot", type = "image/png")
    public static byte[] attachScreenshotToAllureReport(WebDriver driver) {
        try {
            return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        } catch (Exception e) {
            logger.error("Failed to capture screenshot for Allure: " + e.getMessage());
            return new byte[0];
        }
    }

    public static Media attachScreenshotToExtentReport(WebDriver driver) {
        try {
            return MediaEntityBuilder.createScreenCaptureFromBase64String(
                    ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64), "Full Page Screenshot").build();
        } catch (Exception e) {
            logger.error("Failed to capture screenshot for Extent Reports: " + e.getMessage());
            return null;
        }
    }

    public static String getScreenshot(WebDriver driver, String screenshotName) {
        try {
            String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String filePath = SCREENSHOT_DIR + screenshotName + "_" + timestamp + ".png";
            FileUtils.copyFile(source, new File(filePath));
            return filePath;
        } catch (IOException e) {
            logger.error("Error saving screenshot: " + e.getMessage());
            return "Failed to save screenshot";
        }
    }

    @Attachment(value = "API Request - {type}", type = "text/json")
    public static byte[] attachApiRequestToAllureReport(String type, byte[] requestData) {
        return attachTextJson(requestData);
    }

    @Attachment(value = "API Response", type = "text/json")
    public static byte[] attachApiResponseToAllureReport(byte[] responseData) {
        return attachTextJson(responseData);
    }

    private static byte[] attachTextJson(byte[] data) {
        return (data != null) ? data : new byte[0];
    }
}
