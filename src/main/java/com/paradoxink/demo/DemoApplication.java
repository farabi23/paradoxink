package com.paradoxink.demo;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@SpringBootApplication
public class DemoApplication {

	@Bean
	public ApplicationRunner waitForDatabase(DataSource dataSource) {
		return args -> {
			System.out.println("Waiting for database to be ready...");
			final int MAX_ATTEMPTS = 30;
			final int RETRY_DELAY = 15000; //15 sec

			for (int i = 1; i <= MAX_ATTEMPTS; i++) {
				try (Connection connection = dataSource.getConnection()) {
					if (connection.isValid(2)) {
						System.out.println("Database connection successful!");
						return;
					}
				} catch (SQLException e) {
					System.out.printf("Database connection attempt %d/%d failed: %s%n",
							i, MAX_ATTEMPTS, e.getMessage());
					if (i == MAX_ATTEMPTS) {
						throw new RuntimeException("Failed to connect to database after " +
								MAX_ATTEMPTS + " attempts", e);
					}
				}
				Thread.sleep(RETRY_DELAY);
			}
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
