package com.softserve.academy.test;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

import static com.softserve.academy.service.RegistrationService.logger;


public abstract class BaseTest {

    private static final Logger logger = LoggerFactory.getLogger(BaseTest.class);

    protected WebDriver driver;
    protected WebDriverWait wait;

    @BeforeEach
    public void setUp() {
        logger.info("===== Test setup started =====");

        ChromeOptions options = new ChromeOptions();
        logger.debug("Configuring Chrome options");

        options.addArguments("--lang=en");
        options.addArguments("--headless=new");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--window-size=1920,1080");

        driver = WebDriverManager.chromedriver().capabilities(options).create();
        logger.info("WebDriver initialized");

        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        logger.debug("WebDriverWait initialized with timeout: 10 seconds");

        logger.info("===== Test setup completed =====");
    }

    @AfterEach
    public void tearDown() {
        logger.info("===== Test teardown started =====");
        if (driver != null) {
            driver.quit();
            logger.info("WebDriver closed successfully");
        } else {
            logger.warn("WebDriver was null during teardown");
        }

        logger.info("===== Test teardown completed =====");
    }
}
