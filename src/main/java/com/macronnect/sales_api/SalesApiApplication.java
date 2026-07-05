package com.macronnect.sales_api;

import com.macronnect.sales_api.model.entity.User;
import com.macronnect.sales_api.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class SalesApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SalesApiApplication.class, args);
	}

	@Bean
	public CommandLineRunner initUsers(UserRepository userRepository,
									   PasswordEncoder passwordEncoder) {

		return args -> {

			User user = userRepository.findByUsername("admin")
					.orElse(new User());

			user.setUsername("admin");
			user.setPassword(passwordEncoder.encode("123456"));
			user.setEnabled(true);

			userRepository.save(user);

			System.out.println("Password generado:");
			System.out.println(user.getPassword());

			System.out.println(
					passwordEncoder.matches("123456", user.getPassword())
			);
		};
	}
}
