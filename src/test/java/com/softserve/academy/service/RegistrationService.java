package com.softserve.academy.service;

import com.softserve.academy.model.User;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.openqa.selenium.support.ui.ExpectedConditions.*;

@RequiredArgsConstructor
public class RegistrationService {
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
        wait.until(invisibilityOfElementLocated(SUCCESS_MESSAGE));
        wait.until(elementToBeClickable(SIGN_UP_BUTTON)).click();
        wait.until(visibilityOfElementLocated(EMAIL_FIELD));
    }

    public void fillRegistrationForm(User user) {
        enterText(EMAIL_FIELD, user.getEmail());
        enterText(FIRST_NAME_FIELD, user.getName());
        enterText(PASSWORD_FIELD, user.getPassword());
        enterText(REPEAT_PASSWORD_FIELD, user.getPassword());
    }

    private void enterText(By locator, String text) {
        WebElement element = wait.until(visibilityOfElementLocated(locator));
        element.clear();
        element.sendKeys(text);
    }

    public void submitRegistration() {
        WebElement btnSubmit = wait.until(elementToBeClickable(SUBMIT_BUTTON));
        if (!btnSubmit.isEnabled()) {
            throw new AssertionError("Submit button is not enabled.");
        }
        btnSubmit.click();
    }

    public String getSuccessMessage() {
        WebElement successMsg = wait.until(visibilityOfElementLocated(SUCCESS_MESSAGE));
        return successMsg.getText();
    }
}
