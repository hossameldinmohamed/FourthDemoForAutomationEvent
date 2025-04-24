package utils;

import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import io.qameta.allure.testng.AllureTestNg;
import org.openqa.selenium.WebDriver;
import org.testng.*;

public class TestngListener extends AllureTestNg implements ISuiteListener, ITestListener {

    ////////////////////////////////////////////////////
    ///////////////// ISuiteListener //////////////////
    //////////////////////////////////////////////////
    @Override
    public void onStart(ISuite suite) {
        LoggerClass.logMessage("Starting Test Suite: " + suite.getName());
        PropertiesReader.loadProperties();
        ExtentReport.initReports();
    }

    @Override
    public void onFinish(ISuite suite) {
        LoggerClass.logMessage("Finishing Test Suite: " + suite.getName());
        ExtentReport.flushReports();
    }

    ///////////////////////////////////////////////////
    ///////////////// ITestListener //////////////////
    /////////////////////////////////////////////////
    @Override
    public void onStart(ITestContext context) {
        LoggerClass.logMessage("********************** Test Started: [" + context.getName() + "] **********************");
    }

    @Override
    public void onFinish(ITestContext context) {
        LoggerClass.logMessage("********************** Test Finished: [" + context.getName() + "] **********************");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentReport.pass(MarkupHelper.createLabel(result.getMethod().getMethodName() + " Passed!", ExtentColor.GREEN));
    }

    @Override
    public void onTestFailure(ITestResult result) {
        WebDriver driver = (WebDriver) result.getTestContext().getAttribute("driver");

        if (driver != null) {
            try {
                ExtentReport.fail(LoggerClass.attachScreenshotToExtentReport(driver));
                LoggerClass.attachScreenshotToAllureReport(driver);
            } catch (Exception e) {
                LoggerClass.logMessage("Failed to capture screenshot: " + e.getMessage());
            }
        }

        ExtentReport.fail(MarkupHelper.createLabel(result.getMethod().getMethodName() + " Failed!", ExtentColor.RED));
        if (result.getThrowable() != null) {
            ExtentReport.fail(result.getThrowable());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentReport.skip(MarkupHelper.createLabel(result.getMethod().getMethodName() + " Skipped!", ExtentColor.YELLOW));
        if (result.getThrowable() != null) {
            ExtentReport.skip(result.getThrowable());
        }
    }

    ////////////////////////////////////////////////////////////
    ///////////////// IInvokedMethodListener //////////////////
    //////////////////////////////////////////////////////////
    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        ITestNGMethod testMethod = method.getTestMethod();
        String testName = (testMethod.getDescription() != null && !testMethod.getDescription().isEmpty())
                ? testMethod.getDescription()
                : testResult.getName();

        ExtentReport.createTest(testName);
        LoggerClass.logMessage("Starting: [" + testName + "]");

        if (method.isConfigurationMethod()) {
            LoggerClass.logMessage("Executing Configuration Method (Setup/Teardown): [" + testName + "]");
            ExtentReport.removeTest(testName);
        }
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        WebDriver driver = (WebDriver) testResult.getTestContext().getAttribute("driver");

        if (ITestResult.FAILURE == testResult.getStatus() && driver != null) {
            LoggerClass.attachScreenshotToAllureReport(driver);
        }

        String testName = testResult.getName();
        if (method.isConfigurationMethod()) {
            LoggerClass.logMessage("Finished Configuration Method (Setup/Teardown): [" + testName + "]");
        } else {
            LoggerClass.logMessage("Finished Test Case: [" + testName + "]");
        }
    }
}
