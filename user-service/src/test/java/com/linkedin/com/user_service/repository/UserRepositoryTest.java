package com.linkedin.com.user_service.repository;

import com.linkedin.com.user_service.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.InvalidDataAccessApiUsageException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    private User user;

    private static final String EXISTING_EMAIL = "anshul@gmail.com";
    private static final Long EXISTING_USER_ID = 1L;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(EXISTING_USER_ID)
                .email(EXISTING_EMAIL)
                .name("Anshul Bharadwaj")
                .password("$2a$12$secureHashedPassword") // Always use hashed passwords in tests
                .build();
    }

    @Test
    @DisplayName("Find by existing email returns user")
    void findByEmail_whenEmailExists_returnsUser() {

        userRepository.save(user);
        Optional<User> user = userRepository.findByEmail(EXISTING_EMAIL);

        assertThat(user)
                .isPresent()
                .get()
                .satisfies(u -> {
                    assertThat(u.getId()).isEqualTo(EXISTING_USER_ID);
                    assertThat(u.getEmail()).isEqualTo(EXISTING_EMAIL);
                });
    }

    @Test
    @DisplayName("Find by non-existent email returns empty")
    void findByEmail_whenEmailDoesNotExist_returnsEmpty() {
        Optional<User> user = userRepository.findByEmail("nonexistent@email.com");

        assertThat(user).isEmpty();
    }


    @Test
    @DisplayName("Email search is case sensitive")
    void findByEmail_isCaseSensitive() {
        Optional<User> user = userRepository.findByEmail(EXISTING_EMAIL.toUpperCase());

        assertThat(user).isEmpty(); // or isPresent() based on your requirements
    }

}