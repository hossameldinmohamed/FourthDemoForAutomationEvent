package pages;

import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.BrowserActions;
import utils.ExtentReport;
import utils.LoggerClass;
import utils.PropertiesReader;

import static utils.ElementActions.click;
import static utils.ElementActions.type;

public class LoginPage {
    private WebDriver driver;

    private String landingPageUrl = PropertiesReader.getProperty("websitename.baseurl");

    private By userName = By.id( "user-name");

    private By passwordField = By.id( "password");

    private By loginButton = By.id( "login-button");


    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }


    public LoginPage navigateToLandingPage() {
        BrowserActions browserActions = new BrowserActions(driver);
        browserActions.navigateToUrl(landingPageUrl);
        return this;

    }

    @Step("Login with Valid credentials ")
    public LoginPage login(String username, String password) {

        ExtentReport.info(MarkupHelper.createLabel("User Login", ExtentColor.BLUE));

        LoggerClass.logMessage("Login with username: " + username + " and password: " + password);
        type(driver,userName, username);
        type(driver,passwordField, password);
        click(driver,loginButton);
        return this;
    }


}
