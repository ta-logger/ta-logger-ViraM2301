package com.softserve.academy.test;

import com.softserve.academy.model.User;
import com.softserve.academy.repository.UserRepository;
import com.softserve.academy.service.RegistrationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

class GreenCityValidRegistrationTest extends BaseTest {

    private static final Logger logger = LoggerFactory.getLogger(GreenCityValidRegistrationTest.class);

    private RegistrationService registrationService;

    @BeforeEach
    void setup() {
        logger.info("Opening GreenCity website");

        registrationService = new RegistrationService(driver, wait);

        driver.navigate().to("https://www.greencity.cx.ua/#/greenCity");
        logger.info("Website opened successfully");

    }

    static Stream<User> userProvider() {
        List<User> users = UserRepository.getUsersFromCsv("/registration_data.csv");
        logger.debug("Loaded {} users from CSV", users.size());

        return users.stream();
    }

    @ParameterizedTest(name = "User: {0}")
    @DisplayName("Check registration with layered architecture")
    @MethodSource("userProvider")
    void testRegistration(User user) {

        logger.info("Test started for user: {}", user.getEmail());
        
        User testUser = User.builder()
                .email(generateUniqueEmail(user.getEmail()))
                .name(user.getName())
                .password(user.getPassword())
                .build();

        logger.debug("Generated test user with email: {}", testUser.getEmail());

        registrationService.openRegistrationModal();
        logger.info("Registration modal opened");

        registrationService.fillRegistrationForm(testUser);
        logger.debug("Registration form filled");

        registrationService.submitRegistration();
        logger.debug("Registration form submitted");
        
        String actualMessage = registrationService.getSuccessMessage();
        String expectedSnippet = "successfully registered";
        logger.debug("Actual success message: {}", actualMessage);
        
        assertTrue(actualMessage.contains(expectedSnippet), 
                "Success message should contain: '" + expectedSnippet + "'. Actual: " + actualMessage);

        logger.info("Assertion passed. Registration successful");
    }

    private String generateUniqueEmail(String email) {
        String uniqueEmail = "test" + UUID.randomUUID().toString().substring(0, 8) + email;

        logger.trace("Generated unique email: {}", uniqueEmail);

        return uniqueEmail;
    }
}
