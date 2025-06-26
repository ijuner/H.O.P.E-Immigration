package ca.conestoga.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * program entrance
 */
@SpringBootApplication
public class HopeApplication {
    public static void main(String[] args) {
        SpringApplication.run(HopeApplication.class, args);
        System.out.println("server http://localhost:8877/ started");
    }
}
