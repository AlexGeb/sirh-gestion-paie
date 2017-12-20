package dev.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import dev.menu.Menu;

public class AdminApp {
	private static final Logger LOGGER = LoggerFactory.getLogger(AdminApp.class);

	public static void main(String[] args) {
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class)) {
			LOGGER.info("Application launched");
			Menu menu = ctx.getBean(Menu.class);
			menu.afficher();
		}

	}

}
