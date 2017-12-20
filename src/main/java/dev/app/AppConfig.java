package dev.app;

import java.util.Scanner;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

import dev.paie.config.ServicesConfig;

@Configuration
@Import({ ServicesConfig.class })
@ComponentScan({ "dev.menu" })
public class AppConfig {

	@Bean
	public Scanner scanner() {
		return new Scanner(System.in);
	}

}
