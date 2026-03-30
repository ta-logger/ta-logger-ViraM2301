package com.softserve.academy.test;

import com.softserve.academy.model.User;
import com.softserve.academy.repository.UserRepository;
import com.softserve.academy.service.RegistrationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

class GreenCityValidRegistrationTest extends BaseTest {

    private RegistrationService registrationService;

    @BeforeEach
    void setup() {
        registrationService = new RegistrationService(driver, wait);
        driver.navigate().to("https://www.greencity.cx.ua/#/greenCity");
    }

    static Stream<User> userProvider() {
        List<User> users = UserRepository.getUsersFromCsv("/registration_data.csv");
        return users.stream();
    }

    @ParameterizedTest(name = "User: {0}")
    @DisplayName("Check registration with layered architecture")
    @MethodSource("userProvider")
    void testRegistration(User user) {
        User testUser = User.builder()
                .email(generateUniqueEmail(user.getEmail()))
                .name(user.getName())
                .password(user.getPassword())
                .build();

        registrationService.openRegistrationModal();
        registrationService.fillRegistrationForm(testUser);
        registrationService.submitRegistration();
        
        String actualMessage = registrationService.getSuccessMessage();
        String expectedSnippet = "successfully registered";
        
        assertTrue(actualMessage.contains(expectedSnippet), 
                "Success message should contain: '" + expectedSnippet + "'. Actual: " + actualMessage);
    }

    private String generateUniqueEmail(String email) {
        return "test" + UUID.randomUUID().toString().substring(0, 8) + email;
    }
}
