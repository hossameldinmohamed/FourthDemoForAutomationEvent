import io.qameta.allure.*;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import pages.CartPage;
import pages.HomePage;

import pages.LoginPage;
import utils.*;


@Listeners({io.qameta.allure.testng.AllureTestNg.class})
public class SauceDemoEndToEndTest {
    private WebDriver driver;

    private JsonFileManager testData;
    private HomePage homePage;
    private LoginPage loginPage;
    private CartPage cartPage;



    //////////////////////////////////////////////////////
    /////////////////// Test Cases //////////////////////

    @Test
    @Description("Open SauceDemo website and login with standard user, Add products to cart and checkout")
    @Story("Select Products")
    @Severity(SeverityLevel.CRITICAL)
    @TmsLink("Test_case")
    @Issue("Software_bug")
    @Epic("Automation Event ")
    @Feature("Selenium")
    public void selectProductsThenCheckout() {

        loginPage.navigateToLandingPage().login(testData.getTestData("username"), testData.getTestData("password"));

        homePage.addProductsToCart();



        cartPage.checkout(testData.getTestData("firstName"), testData.getTestData("lastName"), testData.getTestData("postalCode"));

    }


    //////////////////////////////////////////////////////
    ///////////////// Configurations ////////////////////
    @BeforeClass
    public void classSetup() {
        testData = new JsonFileManager("src/test/resources/TestData/SauceDemoTestData.json");
    }

    @BeforeMethod
    public void methodSetup() {
        driver = BrowserFactory.getBrowser();
        homePage = new HomePage(driver);
        loginPage = new LoginPage(driver);
        cartPage = new CartPage(driver);
    }

    @AfterMethod
    public void methodTearDown() {
        if (driver != null) {
            BrowserActions browserActions = new BrowserActions(driver);
            browserActions.closeAllOpenedBrowserWindows();
        }
    }
}
