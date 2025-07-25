package stepdefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import utilities.ConfigurationReader;
import utilities.DriverManager;
import utilities.LoggerUtility;

public class Hooks {
    @Before
    public void setUp() {
        LoggerUtility.info("Setting up test environment");
        DriverManager.getDriver().get(ConfigurationReader.get("app.url"));
        LoggerUtility.info("Navigated to: " + ConfigurationReader.get("app.url"));
    }

    @After
    public void tearDown() {
        LoggerUtility.info("Tearing down test environment");
        DriverManager.quitDriver();
    }
}