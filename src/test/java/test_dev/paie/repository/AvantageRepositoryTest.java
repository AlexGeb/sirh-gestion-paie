package test_dev.paie.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import dev.paie.config.ServicesConfig;
import dev.paie.entite.Avantage;
import dev.paie.repository.AvantageRepository;

@ContextConfiguration(classes = { ServicesConfig.class })
@RunWith(SpringRunner.class)
@Transactional
public class AvantageRepositoryTest {

	@Autowired
	private AvantageRepository avantageRepository;

	@Test
	public void test_sauvegarder_lister_mettre_a_jour() {
		Avantage avantage = new Avantage();
		avantage.setCode("av_test");
		avantage.setMontant(new BigDecimal("14.25"));
		avantage.setNom("avantage_de_test");

		avantageRepository.save(avantage);
		Avantage monAv = avantageRepository.findAll().stream().filter(av -> av.getCode().equals("av_test")).findFirst()
				.orElse(null);
		assertThat(monAv.getCode()).isEqualTo("av_test");
		assertThat(monAv.getMontant()).isEqualTo(new BigDecimal("14.25"));
		assertThat(monAv.getNom()).isEqualTo("avantage_de_test");

		monAv.setCode("av_test2");
		monAv.setMontant(new BigDecimal("12.3"));
		monAv.setNom("avantage_de_test2");
		int id = monAv.getId();
		avantageRepository.save(avantage);
		
		Avantage monAv2 = avantageRepository.findOne(id);
		assertThat(monAv2.getCode()).isEqualTo("av_test2");
		assertThat(monAv2.getMontant()).isEqualTo(new BigDecimal("12.3"));
		assertThat(monAv2.getNom()).isEqualTo("avantage_de_test2");
	}
}
