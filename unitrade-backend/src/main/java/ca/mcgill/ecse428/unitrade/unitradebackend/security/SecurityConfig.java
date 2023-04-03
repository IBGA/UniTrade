package ca.mcgill.ecse428.unitrade.unitradebackend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import ca.mcgill.ecse428.unitrade.unitradebackend.service.UserDetailsService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    // All roles are listed in the UserDetailsService
    @Autowired private UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .cors()
                .and()
                .formLogin().disable()
                // .authorizeHttpRequests(
                //     auth -> {
                        // auth.requestMatchers("/").permitAll();
                        // auth.requestMatchers("/swagger-ui/**").permitAll();
                        // ... other rules
                //     })
                .userDetailsService(userDetailsService)
                .httpBasic(Customizer.withDefaults()) // HTTP Basic Authentication
                .securityContext(context -> context
                    .securityContextRepository(new HttpSessionSecurityContextRepository())
                )
                .sessionManagement(sessionManagement -> sessionManagement
                    .sessionCreationPolicy(SessionCreationPolicy.NEVER)
                    .sessionConcurrency((sessionConcurrency) -> sessionConcurrency
                             .maximumSessions(1)
                     )
                );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new CredentialsEncoder();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
                // registry.addMapping("/**").allowedOrigins("http://localhost:5173");
                registry.addMapping("/**").allowedOrigins("*");
			}
		};
	}
    
}
