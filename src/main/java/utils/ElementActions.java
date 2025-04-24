package utils;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

public class ElementActions {
    private WebDriver driver;

    public enum SelectType {
        TEXT, VALUE
    }

    public ElementActions(WebDriver driver) {
        this.driver = driver;
    }

    private WebElement getElement(By elementLocator) {
        return Helper.getExplicitWait(driver).until(ExpectedConditions.visibilityOfElementLocated(elementLocator));
    }

    public static void click(WebDriver driver, By elementLocator) {
        try {
            WebElement element = Helper.getExplicitWait(driver).until(ExpectedConditions.elementToBeClickable(elementLocator));
            new Actions(driver).moveToElement(element).click().perform();
            LoggerClass.logStep("Clicked on element: " + elementLocator);
        } catch (Exception e) {
            LoggerClass.logStep("Failed to click element: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public ElementActions click(By elementLocator) {
        click(driver, elementLocator);
        return this;
    }

    public static void type(WebDriver driver, By elementLocator, String text) {
        type(driver, elementLocator, text, true);
    }

    public static void type(WebDriver driver, By elementLocator, String text, boolean clearBeforeTyping) {
        try {
            WebElement element = Helper.getExplicitWait(driver).until(ExpectedConditions.elementToBeClickable(elementLocator));

            if (clearBeforeTyping) {
                element.clear();
            }
            element.sendKeys(text);
            LoggerClass.logStep("Typed [" + text + "] into element: " + elementLocator);

        } catch (Exception e) {
            LoggerClass.logStep("Failed to type into element: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public ElementActions type(By elementLocator, String text) {
        type(driver, elementLocator, text, true);
        return this;
    }

    public ElementActions type(By elementLocator, String text, boolean clearBeforeTyping) {
        type(driver, elementLocator, text, clearBeforeTyping);
        return this;
    }

    public static void select(WebDriver driver, By elementLocator, SelectType selectType, String option) {
        try {
            Select select = new Select(Helper.getExplicitWait(driver).until(ExpectedConditions.visibilityOfElementLocated(elementLocator)));
            if (select.isMultiple()) {
                LoggerClass.logMessage("Dropdown is multi-select. Ensure you handle selections accordingly.");
            }

            switch (selectType) {
                case TEXT -> select.selectByVisibleText(option);
                case VALUE -> select.selectByValue(option);
                default -> LoggerClass.logMessage("Unexpected select type: " + selectType);
            }

            LoggerClass.logStep("Selected [" + option + "] from dropdown: " + elementLocator);
        } catch (Exception e) {
            LoggerClass.logStep("Failed to select option: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public ElementActions select(By elementLocator, SelectType selectType, String option) {
        select(driver, elementLocator, selectType, option);
        return this;
    }

    public static void mouseHover(WebDriver driver, By elementLocator) {
        try {
            WebElement element = Helper.getExplicitWait(driver).until(ExpectedConditions.visibilityOfElementLocated(elementLocator));
            new Actions(driver).moveToElement(element).perform();
            LoggerClass.logStep("Hovered over element: " + elementLocator);
        } catch (Exception e) {
            LoggerClass.logStep("Failed to hover over element: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public ElementActions mouseHover(By elementLocator) {
        mouseHover(driver, elementLocator);
        return this;
    }

    public static void doubleClick(WebDriver driver, By elementLocator) {
        try {
            WebElement element = Helper.getExplicitWait(driver).until(ExpectedConditions.elementToBeClickable(elementLocator));
            new Actions(driver).doubleClick(element).perform();
            LoggerClass.logStep("Double-clicked on element: " + elementLocator);
        } catch (Exception e) {
            LoggerClass.logStep("Failed to double-click element: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public ElementActions doubleClick(By elementLocator) {
        doubleClick(driver, elementLocator);
        return this;
    }

    public static void clickKeyboardKey(WebDriver driver, By elementLocator, Keys key) {
        try {
            WebElement element = Helper.getExplicitWait(driver).until(ExpectedConditions.elementToBeClickable(elementLocator));
            element.sendKeys(key);
            LoggerClass.logStep("Sent keyboard key [" + key.name() + "] to element: " + elementLocator);
        } catch (Exception e) {
            LoggerClass.logStep("Failed to send keyboard key: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public ElementActions clickKeyboardKey(By elementLocator, Keys key) {
        clickKeyboardKey(driver, elementLocator, key);
        return this;
    }

    public static String getText(WebDriver driver, By elementLocator) {
        try {
            String text = Helper.getExplicitWait(driver).until(ExpectedConditions.visibilityOfElementLocated(elementLocator)).getText();
            LoggerClass.logStep("Retrieved text [" + text + "] from element: " + elementLocator);
            return text;
        } catch (Exception e) {
            LoggerClass.logStep("Failed to retrieve text: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static String getAttributeValue(WebDriver driver, By elementLocator, String attributeName) {
        try {
            String attributeValue = Helper.getExplicitWait(driver).until(ExpectedConditions.visibilityOfElementLocated(elementLocator)).getAttribute(attributeName);
            LoggerClass.logStep("Retrieved attribute [" + attributeName + "] value [" + attributeValue + "] from element: " + elementLocator);
            return attributeValue;
        } catch (Exception e) {
            LoggerClass.logStep("Failed to retrieve attribute value: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static void typeCharacterByCharacter(WebDriver driver, By elementLocator, String text) {
        try {
            WebElement element = Helper.getExplicitWait(driver).until(ExpectedConditions.elementToBeClickable(elementLocator));
            LoggerClass.logStep("Typing [" + text + "] character by character into: " + elementLocator);
            for (char c : text.toCharArray()) {
                element.sendKeys(Character.toString(c));
            }
        } catch (Exception e) {
            LoggerClass.logStep("Failed to type character by character: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public ElementActions typeCharacterByCharacter(By elementLocator, String text) {
        typeCharacterByCharacter(driver, elementLocator, text);
        return this;
    }

    public static void switchToFrame(WebDriver driver, int frameIndex) {
        try {
            driver.switchTo().frame(frameIndex);
            LoggerClass.logStep("Switched to frame with index: " + frameIndex);
        } catch (NoSuchFrameException e) {
            LoggerClass.logStep("Frame with index " + frameIndex + " not found.");
            throw new RuntimeException(e);
        }
    }

    public ElementActions switchToFrame(int frameIndex) {
        switchToFrame(driver, frameIndex);
        return this;
    }

    public static void switchToDefaultContent(WebDriver driver) {
        driver.switchTo().defaultContent();
        LoggerClass.logStep("Switched back to default content.");
    }

    public ElementActions switchToDefaultContent() {
        switchToDefaultContent(driver);
        return this;
    }
}
