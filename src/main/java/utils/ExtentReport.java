package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.model.Media;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ExtentReport {
    private static ExtentReports report;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    private static final String REPORTS_PATH = "reports/ExtentReports.html";

    public static synchronized void initReports() {
        try {
            if (report == null) {
                Path reportDir = Paths.get("reports");
                if (!Files.exists(reportDir)) {
                    Files.createDirectories(reportDir);
                }

                ExtentSparkReporter spark = new ExtentSparkReporter(REPORTS_PATH);
                spark.config().setTheme(Theme.STANDARD);
                spark.config().setDocumentTitle("Automation Test Report");
                spark.config().setReportName("Automation Execution Report");

                report = new ExtentReports();
                report.attachReporter(spark);
            }
        } catch (Exception e) {
            System.err.println("Error initializing ExtentReports: " + e.getMessage());
        }
    }

    public static synchronized void createTest(String testcasename) {
        if (report == null) {
            throw new IllegalStateException("ExtentReports is not initialized. Call initReports() first.");
        }
        test.set(report.createTest(testcasename));
    }

    public static void removeTest(String testcasename) {
        if (report != null) {
            report.removeTest(testcasename);
        }
    }

    public static void info(String message) {
        if (test.get() != null) {
            test.get().info(message);
        }
    }

    public static void info(Markup m) {
        if (test.get() != null) {
            test.get().info(m);
        }
    }

    public static void pass(String message) {
        if (test.get() != null) {
            test.get().pass(message);
        }
    }

    public static void pass(Markup m) {
        if (test.get() != null) {
            test.get().pass(m);
        }
    }

    public static void fail(String message) {
        if (test.get() != null) {
            test.get().fail(message);
        }
    }

    public static void fail(Markup m) {
        if (test.get() != null) {
            test.get().fail(m);
        }
    }

    public static void fail(Throwable t) {
        if (test.get() != null) {
            test.get().fail(t);
        }
    }

    public static void fail(Media media) {
        if (test.get() != null) {
            test.get().fail(media);
        }
    }

    public static void skip(String message) {
        if (test.get() != null) {
            test.get().skip(message);
        }
    }

    public static void skip(Markup m) {
        if (test.get() != null) {
            test.get().skip(m);
        }
    }

    public static void skip(Throwable t) {
        if (test.get() != null) {
            test.get().skip(t);
        }
    }

    public static synchronized void flushReports() {
        if (report != null) {
            report.flush();
        }
    }
}
