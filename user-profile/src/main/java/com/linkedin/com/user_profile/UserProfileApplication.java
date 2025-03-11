package com.linkedin.com.user_profile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class UserProfileApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserProfileApplication.class, args);
	}

}
