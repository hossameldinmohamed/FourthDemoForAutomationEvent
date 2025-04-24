package utils;

import io.restassured.path.json.JsonPath;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class JsonFileManager {
    private final String jsonFilePath;
    private JsonPath jsonPath;

    public JsonFileManager(String jsonFilePath) {
        this.jsonFilePath = jsonFilePath;
        initializeJson();
    }

    private void initializeJson() {
        try (FileReader reader = new FileReader(new File(jsonFilePath))) {
            jsonPath = JsonPath.from(reader);
            LoggerClass.logStep("Loaded JSON file: " + jsonFilePath);
        } catch (FileNotFoundException e) {
            LoggerClass.logStep("JSON file not found: " + jsonFilePath);
            throw new RuntimeException("JSON file not found: " + jsonFilePath, e);
        } catch (IOException e) {
            LoggerClass.logStep("Error reading JSON file: " + jsonFilePath);
            throw new RuntimeException("Error reading JSON file: " + jsonFilePath, e);
        }
    }

    public String getTestData(String path) {
        try {
            // Reinitialize reader to avoid stale file reference
            try (FileReader reader = new FileReader(jsonFilePath)) {
                JsonPath jsonPath = JsonPath.from(reader);
                String value = jsonPath.getString(path);

                if (value == null) {
                    throw new IllegalArgumentException("JSON key '" + path + "' not found in file: " + jsonFilePath);
                }

                return value;
            }
        } catch (IOException e) {
            LoggerClass.logStep("Error reading JSON file: " + jsonFilePath);
            throw new RuntimeException("Failed to read JSON file: " + jsonFilePath, e);
        } catch (Exception e) {
            LoggerClass.logStep("Error retrieving test data from JSON path: " + path);
            throw new RuntimeException("Invalid JSON path: " + path, e);
        }
    }

}