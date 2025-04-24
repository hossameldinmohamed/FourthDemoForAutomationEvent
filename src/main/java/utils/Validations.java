package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.asserts.SoftAssert;
import org.testng.Assert;

public class Validations {


    private static final SoftAssert softAssert = new SoftAssert();

    /**
     * Hard assertion - Immediately stops execution if the assertion fails.
     */
    public static void assertEquals(Object actual, Object expected, String failMessage) {
        try {
            org.testng.Assert.assertEquals(actual, expected, failMessage);
            LoggerClass.logMessage("[VALIDATION PASS] " + " Expected: " + expected + ", Actual: " + actual);
        } catch (AssertionError e) {
            LoggerClass.logMessage("[VALIDATION FAIL] " + " Expected: " + expected + ", Actual: " + actual + "Fail Message: " + failMessage);
            throw e; // Stop execution
        }
    }

    /**
     * Soft assertion - Continues execution even if the assertion fails.
     */
    public static void softAssertEquals(Object actual, Object expected, String failMessage) {
        softAssert.assertEquals(actual, expected, failMessage);
        LoggerClass.logMessage("[SOFT VALIDATION] " + " Expected: " + expected + ", Actual: " + actual);
    }

    /**
     * Hard assertion - Checks if a condition is true.
     */
    public static void assertTrue(boolean condition, String message) {
        try {
            org.testng.Assert.assertTrue(condition, message);
            LoggerClass.logMessage("[VALIDATION PASS] " + message);
        } catch (AssertionError e) {
            LoggerClass.logMessage("[VALIDATION FAIL] " + message);
            throw e;
        }
    }

    /**
     * Soft assertion - Checks if a condition is true but doesn't stop execution.
     */
    public static void softAssertTrue(boolean condition, String message) {
        softAssert.assertTrue(condition, message);
        LoggerClass.logMessage("[SOFT VALIDATION] " + message);
    }

    /**
     * Validates if a web element is displayed.
     */
    public static void assertElementDisplayed(WebDriver driver, By elementLocator, String message) {
        try {
            boolean isDisplayed = driver.findElement(elementLocator).isDisplayed();
            org.testng.Assert.assertTrue(isDisplayed, message);
            LoggerClass.logMessage("[VALIDATION PASS] Element is displayed: " + message);
        } catch (Exception e) {
            LoggerClass.logMessage("[VALIDATION FAIL] Element not displayed: " + message);
            throw new AssertionError(message);
        }
    }

    /**
     * Soft assertion - Validates if a web element is displayed.
     */
    public static void softAssertElementDisplayed(WebDriver driver, By elementLocator, String message) {
        try {
            boolean isDisplayed = driver.findElement(elementLocator).isDisplayed();
            softAssert.assertTrue(isDisplayed, message);
            LoggerClass.logMessage("[SOFT VALIDATION] Element is displayed: " + message);
        } catch (Exception e) {
            LoggerClass.logMessage("[SOFT VALIDATION FAIL] Element not displayed: " + message);
        }
    }

    /**
     * Validates if a web element contains expected text.
     */
    public static void assertElementText(WebDriver driver, By elementLocator, String expectedText, String message) {
        try {
            String actualText = driver.findElement(elementLocator).getText();
            org.testng.Assert.assertEquals(actualText, expectedText, message);
            LoggerClass.logMessage("[VALIDATION PASS] " + message + " | Expected: " + expectedText + ", Actual: " + actualText);
        } catch (Exception e) {
            LoggerClass.logMessage("[VALIDATION FAIL] " + message + " | Expected: " + expectedText);
            throw new AssertionError(message);
        }
    }

    /**
     * Soft assertion - Validates if a web element contains expected text.
     */
    public static void softAssertElementText(WebDriver driver, By elementLocator, String expectedText, String message) {
        try {
            String actualText = driver.findElement(elementLocator).getText();
            softAssert.assertEquals(actualText, expectedText, message);
            LoggerClass.logMessage("[SOFT VALIDATION] " + message + " | Expected: " + expectedText + ", Actual: " + actualText);
        } catch (Exception e) {
            LoggerClass.logMessage("[SOFT VALIDATION FAIL] " + message + " | Expected: " + expectedText);
        }
    }

    /**
     * This method ensures all soft assertions are reported at the end of the test.
     * Call this in `@AfterMethod` or at the end of a test case.
     */
    public static void assertAll() {
        softAssert.assertAll();
    }
}
