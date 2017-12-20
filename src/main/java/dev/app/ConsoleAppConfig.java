package dev.app;

import java.util.Scanner;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import dev.paie.config.JeuxDeDonneesConfig;
import dev.paie.config.ServicesConfig;

@Configuration
@Import({ ServicesConfig.class, JeuxDeDonneesConfig.class })
@ComponentScan({ "dev.menu" })
public class ConsoleAppConfig {

	@Bean
	public Scanner scanner() {
		return new Scanner(System.in);
	}

}
