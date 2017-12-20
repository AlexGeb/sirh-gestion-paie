package dev.menu;

import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class MenuPrincipal extends OptionMenu {

	public MenuPrincipal() {
		super("** Gestion des cotisations **");
		// TODO Auto-generated constructor stub
	}

	private static final Logger LOGGER = LoggerFactory.getLogger(MenuPrincipal.class);

	@Autowired
	private Scanner scanner;

	@Autowired
	private ListerCotisationsOptionMenu listerCotisations;
	@Autowired
	private CreerCotisationOptionMenu creerCotisations;
	@Autowired
	private SupprimerCotisationOptionMenu supprimerCotisations;

	public void execute() {
		LOGGER.info(libelle);
		options.put("1", listerCotisations);
		options.put("2", creerCotisations);
		options.put("3", supprimerCotisations);
		boolean encore = true;
		while (encore) {
			options.forEach((key, opt) -> {
				LOGGER.info(key + ". " + opt.getLibelle());
			});
			LOGGER.info("Une entrée différente fermera l'application");
			String choix = scanner.nextLine();
			if (options.containsKey(choix)) {
				try {
					options.get(choix).execute();
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				encore = false;
				LOGGER.info("Bye !");
			}
		}

	}
}
