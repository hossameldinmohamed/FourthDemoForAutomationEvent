package pages;

import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.*;

import static utils.ElementActions.click;
import static utils.ElementActions.getText;

public class HomePage {
    private WebDriver driver;

    private By firstProduct = By.id("add-to-cart-sauce-labs-backpack");
    private By secondProduct = By.id("add-to-cart-sauce-labs-bike-light");
    private By shoppingCart = By.id("shopping_cart_container");

    // Constructor
    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    //////////////////////////////////////////////////////////////////////////
    //////////////////////////////// Actions ////////////////////////////////




    @Step("Add Items to Cart ")
    public HomePage addProductsToCart() {

        click(driver, firstProduct);
        click(driver, secondProduct);
        click(driver, shoppingCart);
        return this;
    }




}