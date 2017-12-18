package dev.paie.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import dev.paie.config.JeuxDeDonneesConfig;
import dev.paie.config.ServicesConfig;
import dev.paie.entite.BulletinSalaire;
import dev.paie.entite.ResultatCalculRemuneration;

//Sélection des classes de configuration Spring à utiliser lors du test
@ContextConfiguration(classes = { ServicesConfig.class, JeuxDeDonneesConfig.class })
// Configuration JUnit pour que Spring prenne la main sur le cycle de vie du
// test
@RunWith(SpringRunner.class)
public class CalculerRemunerationServiceSimpleTest {
	@Autowired
	private CalculerRemunerationService remunerationService;

	@Autowired
	@Qualifier("bulletin1")
	private BulletinSalaire bulletinSalaire;

	@Test
	public void test_injection_CalculerRemunerationService() {
		assertThat(remunerationService).isInstanceOf(CalculerRemunerationService.class);
	}

	@Test
	public void test_injection_BulletinSalaire() {
		assertThat(bulletinSalaire).isInstanceOf(BulletinSalaire.class);
	}

	@Test
	public void test_calculer() {
		ResultatCalculRemuneration resultat = remunerationService.calculer(bulletinSalaire);
		assertThat(resultat.getSalaireBrut()).isEqualTo("2683.30");
		assertThat(resultat.getTotalRetenueSalarial()).isEqualTo("517.08");
		assertThat(resultat.getTotalCotisationsPatronales()).isEqualTo("1096.13");
		assertThat(resultat.getNetImposable()).isEqualTo("2166.22");
		assertThat(resultat.getNetAPayer()).isEqualTo("2088.41");
	}

}
