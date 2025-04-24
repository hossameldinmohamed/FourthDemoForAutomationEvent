package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Properties;

import org.apache.commons.io.FileUtils;

public class PropertiesReader {
    private static String propRoot = "src/main/resources/";
    private static Properties properties = new Properties();

    static {
        loadProperties(); // 🔥 Auto-load properties when the class is accessed
    }

    public static void loadProperties() {
        try {
            Collection<File> propertiesFilesList = FileUtils.listFiles(new File(propRoot), new String[]{"properties"}, true);

            if (propertiesFilesList.isEmpty()) {
                LoggerClass.logMessage("⚠️ [PropertiesReader] No properties files found in " + propRoot);
                return;
            }

            for (File propertyFile : propertiesFilesList) {
                LoggerClass.logMessage("✅ [PropertiesReader] Loading properties from: " + propertyFile.getAbsolutePath());
                FileInputStream fileInputStream = new FileInputStream(propertyFile);
                properties.load(fileInputStream);
                fileInputStream.close();
            }
            LoggerClass.logMessage("✅ [PropertiesReader] Properties loaded successfully.");

            // 🔥 Merge loaded properties into System.getProperties() (Restoring old behavior)
            properties.putAll(System.getProperties()); // Preserve existing system properties
            System.getProperties().putAll(properties); // Store all loaded properties in system

            properties.forEach((key, value) -> {
                LoggerClass.logMessage("🔹 [PropertiesReader] Setting System Property: " + key + " = " + value);
            });

        } catch (IOException e) {
            LoggerClass.logMessage("❌ [PropertiesReader] Failed to load properties from " + propRoot);
            e.printStackTrace();
        }
    }

    public static String getProperty(String key) {
        return System.getProperty(key);
    }

    public static String getProperty(String key, String defaultValue) {
        return System.getProperty(key, defaultValue);
    }
}