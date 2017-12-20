package dev.paie.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import dev.paie.spring.DataSourceConfig;
import dev.paie.spring.JpaConfig;

@Configuration
@ComponentScan({ "dev.paie.service", "dev.paie.util" })
@Import({ JpaConfig.class, DataSourceConfig.class })
@EnableJpaRepositories("dev.paie.repository")
public class ServicesConfig {
}
