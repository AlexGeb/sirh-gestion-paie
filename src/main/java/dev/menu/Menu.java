package dev.menu;

import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class Menu {
	private static final Logger LOGGER = LoggerFactory.getLogger(Menu.class);

	@Autowired
	private Scanner scanner;

	public void afficher() {
		LOGGER.info("** Gestion des cotisations ***");
		LOGGER.info("1. Lister des cotisations");
		LOGGER.info("2. Créer une cotisation");
		LOGGER.info("3. Supprimer une cotisation");
		String choix = scanner.nextLine();
	}

}
