package dev.app;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import dev.menu.MenuPrincipal;
import dev.paie.entite.Cotisation;
import dev.paie.service.CotisationService;

public class AdminApp {
	private static final Logger LOGGER = LoggerFactory.getLogger(AdminApp.class);

	public static void main(String[] args) {
		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(ConsoleAppConfig.class)) {
			LOGGER.info("Application launched");
			populateDatabase(ctx);
			MenuPrincipal menu = ctx.getBean(MenuPrincipal.class);
			menu.execute();
		}
	}

	private static void populateDatabase(AnnotationConfigApplicationContext ctx) {
		Map<String, Cotisation> beansOfType = ctx.getBeansOfType(Cotisation.class);
		CotisationService cotisationSvc = ctx.getBean(CotisationService.class);
		beansOfType.forEach((id, cotiz) -> {
			cotisationSvc.mettreAJour(cotiz);
		});
	}
}
