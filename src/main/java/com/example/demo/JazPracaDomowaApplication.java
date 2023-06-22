package com.example.demo;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Exchange rate Service REST API",
				version = "1.0",
				description = "This is application for exchange rate.",
				contact = @Contact(
						name = "s24260",
						email = "s24260@pjwstk.edu.pl"
				)
		)
)
public class JazPracaDomowaApplication {

	public static void main(String[] args) {
		SpringApplication.run(JazPracaDomowaApplication.class, args);
	}

}
