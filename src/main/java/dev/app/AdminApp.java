package dev.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import dev.menu.MenuPrincipal;

public class AdminApp {
	private static final Logger LOGGER = LoggerFactory.getLogger(AdminApp.class);

	public static void main(String[] args) {
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class)) {
			LOGGER.info("Application launched");
			MenuPrincipal menu = ctx.getBean(MenuPrincipal.class);
			menu.execute();
		}
	}

}
