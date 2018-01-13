package com.intuit.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.intuit.api", "com.intuit.base", "com.intuit.engine", "com.intuit.web"})
@EnableJpaRepositories({"com.intuit.api", "com.intuit.base", "com.intuit.repo"})
@EntityScan(basePackages = {"com.intuit.base"})
public class AppInit {
	
	public static void main(String[] args) throws Exception {
		SpringApplication.run(AppInit.class, args);
	}

}
