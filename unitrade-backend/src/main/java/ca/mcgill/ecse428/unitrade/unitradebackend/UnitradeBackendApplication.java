package ca.mcgill.ecse428.unitrade.unitradebackend;

import java.util.TimeZone;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.PostConstruct;

@RestController
@SpringBootApplication
@CrossOrigin(origins = "*")
public class UnitradeBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(UnitradeBackendApplication.class, args);
    }

    @RequestMapping("/")
    public String greeting() {
        return "Hello world!";
    }

    @Bean
    CommandLineRunner commandLineRunner() {
        return args -> {
        };
    }

    @PostConstruct
    public void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        // CredentialsEncoder encoder = new CredentialsEncoder();
        // System.out.println("Encoded password: " + encoder.encode("admin"));
    }
}
