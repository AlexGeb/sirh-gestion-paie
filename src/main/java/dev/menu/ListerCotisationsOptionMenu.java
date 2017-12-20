package dev.menu;

import java.util.List;

import org.springframework.stereotype.Controller;

import dev.paie.entite.Cotisation;

@Controller
public class ListerCotisationsOptionMenu extends OptionMenu {

	public ListerCotisationsOptionMenu() {
		super("Lister les cotisations");
	}
	
	@Override
	public void execute() throws Exception {
		List<Cotisation> cotizz = cotisationService.lister();
		cotizz.forEach((c) -> {
			StringBuilder builder = new StringBuilder("");
			builder.append("code : ").append(c.getCode()).append("; ");
			builder.append("libelle : ").append(c.getLibelle()).append("; ");
			builder.append("taux patronal : ").append(c.getTauxPatronal()).append("; ");
			builder.append("taux salarial : ").append(c.getTauxSalarial()).append("; ");
			OptionMenu.LOGGER.info(builder.toString());
		});
	}

}
