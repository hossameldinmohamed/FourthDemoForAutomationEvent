package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.LoggerClass;
import utils.Validations;


import static utils.ElementActions.*;
public class CartPage {


    private WebDriver driver;


    private By checkout = By.id("checkout");
    private By firstName = By.id("first-name");
    private By lastName = By.id("last-name");
    private By postalCode = By.id("postal-code");
    private By continueButton = By.id("continue");
    private By finishButton = By.id("finish");


    public CartPage(WebDriver driver) {
        this.driver = driver;
    }
    public CartPage checkout(String firstName, String lastName, String postalCode) {

        click(driver, checkout);
        type(driver, this.firstName, firstName);
        type(driver, this.lastName, lastName);
        type(driver, this.postalCode, postalCode);
        click(driver, continueButton);
        click(driver, finishButton);
        return  this;
    }
}
