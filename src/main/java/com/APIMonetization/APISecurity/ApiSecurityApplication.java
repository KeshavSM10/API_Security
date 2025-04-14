package com.APIMonetization.APISecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"controllers", "authentication","ratelimitter","com.APIMonetization.APISecurity","monetization","payments","repository","user"})
@EntityScan(basePackages = {"monetization","payments","user"})
@EnableJpaRepositories(basePackages = "repository")
public class ApiSecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiSecurityApplication.class, args);
	}

}
