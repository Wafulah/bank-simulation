package com.bank.bank_simulation.config;

import com.bank.bank_simulation.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * This class is responsible for configuring and providing the necessary components 
 * required by the application. It manages the following functionalities:
 * 
 * 1. **Authentication**: Configures authentication settings, including how user details are retrieved
 *    from the database and how user passwords are securely encoded and verified.
 * 
 * 2. **Password Encoding**: Creates a password encoder instance to securely encode user passwords using
 *    the BCrypt hashing algorithm, ensuring that passwords are stored and compared in a secure way.
 * 
 * 3. **User Details Service**: Configures a custom service that fetches user details from the database, 
 *    enabling the authentication system to look up users by their username in a case-insensitive manner.
 * 
 * 4. **Authentication Provider**: Sets up an authentication provider that uses the user details service 
 *    and the password encoder to perform user authentication and verify their credentials.
 * 
 * 5. **Authentication Manager**: Configures an authentication manager to manage the authentication process, 
 *    allowing the system to authenticate users with the provided configuration.
 * 
 * 6. **Rest Template**: Provides an HTTP client (RestTemplate) that allows the application to make HTTP requests
 *    to external services or APIs, enabling communication with other systems.
 * 
 * 7. **Scheduled Executor Service**: Configures a scheduled executor service to handle tasks that need to be 
 *    executed at specified intervals or in the future, ensuring the system can schedule and manage background tasks.
 * 
 * This class helps in setting up the core infrastructure required for user authentication, secure password handling,
 * external communication, and task scheduling, ensuring that the application works efficiently and securely.
 *
 * @author Wafulah
 */
@Configuration
@RequiredArgsConstructor
public class AppConfig {
    private final UserRepository userRepository;

    /**
     * Creates a custom UserDetailsService that retrieves user details from the database using the provided
     * UserRepository. The username is case-insensitive.
     *
     * @return a UserDetailsService instance
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return userRepository::findByUsernameIgnoreCase;
    }

    /**
     * Creates a BCryptPasswordEncoder instance for encoding passwords.
     *
     * @return a BCryptPasswordEncoder instance
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Creates a DaoAuthenticationProvider instance that uses the custom UserDetailsService and the
     * BCryptPasswordEncoder for authentication.
     *
     * @return a DaoAuthenticationProvider instance
     */
    @Bean
    public AuthenticationProvider authenticationProvider () {
        var daoProvider = new DaoAuthenticationProvider(passwordEncoder());
        daoProvider.setUserDetailsService(userDetailsService());
        return daoProvider;
    }

    /**
     * Creates an AuthenticationManager instance using the provided AuthenticationConfiguration.
     *
     * @param config the AuthenticationConfiguration instance
     * @return an AuthenticationManager instance
     * @throws Exception if an error occurs during the creation of the AuthenticationManager
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Creates a RestTemplate instance for making HTTP requests.
     *
     * @return a RestTemplate instance
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    /**
     * Creates a ScheduledExecutorService instance with a single thread for scheduling tasks.
     *
     * @return a ScheduledExecutorService instance
     */
    @Bean
    public ScheduledExecutorService scheduledExecutorService() {
        return Executors.newScheduledThreadPool(1);
    }
}
