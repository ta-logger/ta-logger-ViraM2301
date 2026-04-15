package com.softserve.academy.service;

import com.softserve.academy.model.User;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

@RequiredArgsConstructor
public class RegistrationService {
    public static final Logger logger = LoggerFactory.getLogger(RegistrationService.class);

    private static final By SIGN_UP_BUTTON = By.cssSelector(".header_sign-up-btn > span");
    private static final By EMAIL_FIELD = By.id("email");
    private static final By FIRST_NAME_FIELD = By.id("firstName");
    private static final By PASSWORD_FIELD = By.id("password");
    private static final By REPEAT_PASSWORD_FIELD = By.id("repeatPassword");
    private static final By SUBMIT_BUTTON = By.cssSelector(".greenStyle");
    private static final By SUCCESS_MESSAGE = By.cssSelector(".mdc-snackbar__label");

    private final WebDriver driver;
    private final WebDriverWait wait;

    public void openRegistrationModal() {
        logger.info("Opening registration modal");

        wait.until(invisibilityOfElementLocated(SUCCESS_MESSAGE));
        logger.debug("Waiting for success message to disappear");

        wait.until(elementToBeClickable(SIGN_UP_BUTTON)).click();
        logger.info("Clicked on Sign Up button");

        wait.until(visibilityOfElementLocated(EMAIL_FIELD));
        logger.debug("Registration form is visible");
    }

    public void fillRegistrationForm(User user) {
        logger.info("Filling registration form");

        enterText(EMAIL_FIELD, user.getEmail());
        enterText(FIRST_NAME_FIELD, user.getName());
        enterText(PASSWORD_FIELD, user.getPassword());
        enterText(REPEAT_PASSWORD_FIELD, user.getPassword());
        logger.info("Registration form filled successfully");
    }

    private void enterText(By locator, String text) {
        logger.debug("Entering text '{}' into field {}", text, locator);
        WebElement element = wait.until(visibilityOfElementLocated(locator));
        logger.debug("Element {} is visible", locator);
        element.clear();
        element.sendKeys(text);
    }

    public void submitRegistration() {
        logger.info("Submitting registration form");
        WebElement btnSubmit = wait.until(elementToBeClickable(SUBMIT_BUTTON));
        logger.debug("Submit button is clickable");
        if (!btnSubmit.isEnabled()) {
            logger.error("Submit button is disabled");
            throw new AssertionError("Submit button is not enabled.");
        }
        btnSubmit.click();
        logger.info("Clicked submit button");
    }

    public String getSuccessMessage() {
        logger.debug("Waiting for success message");
        WebElement successMsg = wait.until(visibilityOfElementLocated(SUCCESS_MESSAGE));
        logger.info("Success message received: {}", successMsg.getText());
        return successMsg.getText();
    }
}
