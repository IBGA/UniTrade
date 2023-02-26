package ca.mcgill.ecse428.unitrade.unitradebackend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import ca.mcgill.ecse428.unitrade.unitradebackend.service.UserDetailsService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    // All roles are listed in the UserDetailsService
    @Autowired private UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.csrf(csrf -> csrf.disable())
                .cors()
                .and()
                // .authorizeHttpRequests(
                //     auth -> {
                        // auth.requestMatchers("/").permitAll();
                        // auth.requestMatchers("/swagger-ui/**").permitAll();
                        // ... other rules
                //     })
                .userDetailsService(userDetailsService)
                .httpBasic(Customizer.withDefaults()) // HTTP Basic Authentication
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new CredentialsEncoder();
    }
    
}
