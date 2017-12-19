package test_dev.paie.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import dev.paie.config.ServicesConfig;
import dev.paie.entite.Cotisation;
import dev.paie.service.CotisationService;

@ContextConfiguration(classes = { ServicesConfig.class })
@RunWith(SpringRunner.class)
@Transactional
public class CotisationServiceJpaTest {

	@Autowired
	private CotisationService cotisationService;

	@Test
	public void test_GradeService_injection() {
		assertThat(cotisationService).isNotNull();
	}

	@Test
	public void test_sauvegarder_lister_mettre_a_jour() {
		Cotisation cotisation = new Cotisation();
		cotisation.setCode("cotisation_test");
		cotisation.setLibelle("cotis_libelle_test");
		cotisation.setTauxPatronal(new BigDecimal("12.26"));
		cotisation.setTauxSalarial(new BigDecimal("10.23"));

		cotisationService.sauvegarder(cotisation);

		List<Cotisation> cotizz = cotisationService.lister();
		int numRows = cotizz.size();
		Cotisation maCotiz = cotizz.stream().filter(c -> c.getCode().equals("cotisation_test")).findFirst()
				.orElse(null);
		assertThat(maCotiz).isNotNull();
		assertThat(maCotiz.getLibelle()).isEqualTo("cotis_libelle_test");
		assertThat(maCotiz.getTauxPatronal()).isEqualTo(new BigDecimal("12.26"));
		assertThat(maCotiz.getTauxSalarial()).isEqualTo(new BigDecimal("10.23"));

		maCotiz.setCode("cotisation_test2");
		maCotiz.setLibelle("cotis_libelle_test2");
		maCotiz.setTauxPatronal(new BigDecimal("45.23"));
		maCotiz.setTauxSalarial(new BigDecimal("1.2"));

		cotisationService.mettreAJour(maCotiz);

		List<Cotisation> cotizz2 = cotisationService.lister();
		assertThat(cotizz2.size()).isEqualTo(numRows);

		Cotisation maCotiz2 = cotizz2.stream().filter(c -> c.getCode().equals("cotisation_test2")).findFirst()
				.orElse(null);

		assertThat(maCotiz2).isNotNull();
		assertThat(maCotiz2.getLibelle()).isEqualTo("cotis_libelle_test2");
		assertThat(maCotiz2.getTauxPatronal()).isEqualTo(new BigDecimal("45.23"));
		assertThat(maCotiz2.getTauxSalarial()).isEqualTo(new BigDecimal("1.2"));
	}
}
