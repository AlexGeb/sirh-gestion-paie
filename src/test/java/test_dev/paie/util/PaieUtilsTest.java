package test_dev.paie.util;

import static org.assertj.core.api.Assertions.*;


import java.math.BigDecimal;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import dev.paie.util.PaieUtils;

public class PaieUtilsTest {
	private ClassPathXmlApplicationContext context;
	private PaieUtils paieUtils;

	@Before
	public void onSetup() {
		// code exécuté avant chaque test
		context = new ClassPathXmlApplicationContext("app-config.xml");
		paieUtils = context.getBean(PaieUtils.class);
	}

	@Test
	public void test_formaterBigDecimal_entier_positif() {
		String resultat = paieUtils.formaterBigDecimal(new BigDecimal("2"));
		assertThat(resultat).isEqualTo("2.00");
	}

	@Test
	public void test_formaterBigDecimal_trois_chiffres_apres_la_virgule() {
		String resultat = paieUtils.formaterBigDecimal(new BigDecimal("2.199"));
		assertThat(resultat).isEqualTo("2.20");
	}
	
	@Test
	public void test_formaterBigDecimal_null() {
		String resultat = paieUtils.formaterBigDecimal(null);
		assertThat(resultat).isEqualTo("0.00");
	}

	@After
	public void onExit() {
		// code exécuté après chaque test
		context.close();
	}

}
