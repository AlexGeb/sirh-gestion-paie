package dev.paie.util;

import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import dev.paie.entite.BulletinSalaire;
import dev.paie.entite.Cotisation;

public class JeuxDeDonneesTest {

	private ClassPathXmlApplicationContext context;
	private BulletinSalaire bulletin1;

	@Before
	public void onSetup() {
		context = new ClassPathXmlApplicationContext("jdd-config.xml");
		bulletin1 = context.getBean("bulletin1", BulletinSalaire.class);
	}

	@Test
	public void test_primeExceptionnelle() {
		assertThat(bulletin1.getPrimeExceptionnelle()).isEqualTo(new BigDecimal("1000"));
	}

	@Test
	public void test_employe() {
		assertThat(bulletin1.getRemunerationEmploye().getMatricule()).isEqualTo("M01");
	}

	@Test
	public void test_entreprise() {
		assertThat(bulletin1.getRemunerationEmploye().getEntreprise().getSiret()).isEqualTo("80966785000022");
		assertThat(bulletin1.getRemunerationEmploye().getEntreprise().getDenomination()).isEqualTo("Dev Entreprise");
		assertThat(bulletin1.getRemunerationEmploye().getEntreprise().getCodeNaf()).isEqualTo("6202A");
	}

	@Test
	public void test_cotisationsNonImposables() {
		List<Cotisation> cotisationsNonImposables = bulletin1.getRemunerationEmploye().getProfilRemuneration()
				.getCotisationsNonImposables();

		Stream.of("EP01", "EP02", "EP03", "EP04", "EP05", "EP06", "EP07", "EP12", "EP19", "EP20", "EPR1", "E900",
				"EP28", "EP37")
				.forEach(code -> assertThat(
						cotisationsNonImposables.stream().filter(c -> c.getCode().equals(code)).findAny().isPresent())
								.as("verification code " + code).isTrue());
	}

	@Test
	public void test_cotisationImposables() {
		List<Cotisation> cotisationsImposables = bulletin1.getRemunerationEmploye().getProfilRemuneration()
				.getCotisationsImposables();
		Stream.of("SP01", "SP02")
				.forEach(code -> assertThat(
						cotisationsImposables.stream().filter(c -> c.getCode().equals(code)).findAny().isPresent())
								.as("verification code " + code).isTrue());
	}

	@Test
	public void test_grade() {
		assertThat(bulletin1.getRemunerationEmploye().getGrade().getNbHeuresBase()).isEqualTo(new BigDecimal("151.67"));
		assertThat(bulletin1.getRemunerationEmploye().getGrade().getTauxBase()).isEqualTo(new BigDecimal("11.0984"));
	}

	@After
	public void onExit() {
		context.close();
	}
}
