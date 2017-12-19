package test_dev.paie.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import dev.paie.config.ServicesConfig;
import dev.paie.entite.Grade;
import dev.paie.service.GradeService;

@ContextConfiguration(classes = { ServicesConfig.class })
@RunWith(SpringRunner.class)
public class GradeServiceJdbcTemplateTest {

	@Autowired
	private GradeService gradeService;

	@Autowired
	private DataSource datasource;

	@Test
	public void test_GradeService_injection() {
		assertThat(gradeService).isNotNull();
		assertThat(datasource).isNotNull();
	}

	/**
	 * Tests pour sauvegarder, lister, mettreAJour 1. creation d'un grade 2.
	 * sauvegarde du grade 3. récupération des grades 4. TEST : le grade doit être
	 * présent dans la liste 5. modification du grade récupéré 6. mise à jour dans
	 * la db et récupération des grades 7. TEST : le nombre de grades doit être
	 * inchangé 8. TESTs : le grade modifié doit être présent dasn la liste et
	 * chacun de ses champs doivent correspondre avec le grade mis à jour
	 */
	@Test
	public void test_sauvegarder_lister_mettre_a_jour() {
		// Creation d'un nouveau grade
		Grade nouveauGrade = new Grade();
		nouveauGrade.setNbHeuresBase(new BigDecimal("150.20"));
		nouveauGrade.setTauxBase(new BigDecimal("11.64"));
		String monCode = "grade_test";
		nouveauGrade.setCode(monCode);

		// sauvegarde
		gradeService.sauvegarder(nouveauGrade);
		// lister
		List<Grade> grades = gradeService.lister();
		int numOfRows = grades.size();
		// le grade est-t-il présent dans la liste
		Grade monGrade = grades.stream().filter(g -> g.getCode().equals(monCode)).findFirst().orElse(null);
		assertThat(monGrade).isNotNull();

		// modificartion du grade récupéré
		monGrade.setNbHeuresBase(new BigDecimal("10.20"));
		monGrade.setTauxBase(new BigDecimal("4.05"));
		monGrade.setCode("grade_test2");
		// mise à jour dans la base et récupération des grades
		gradeService.mettreAJour(monGrade);
		List<Grade> grades2 = gradeService.lister();

		// le nombre de lignes doit être inchangé
		assertThat(grades2.size()).isEqualTo(numOfRows);

		Grade monGradeModifie = grades2.stream().filter(g -> g.getCode().equals("grade_test2")).findFirst()
				.orElse(null);
		assertThat(monGradeModifie).isNotNull();
		// le grade récupéré doit être égal au grade modifié
		assertThat(monGradeModifie.getNbHeuresBase()).isEqualTo(new BigDecimal("10.20"));
		assertThat(monGradeModifie.getTauxBase()).isEqualTo(new BigDecimal("4.05"));
	}

	/**
	 * Nettoyage de la base de donnée après chaque test => suppression de tout les
	 * grades commençant par 'grade_test' dans la table GRADE
	 */
	@After
	public void afterEach() {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(datasource);
		String sql = "DELETE FROM grade WHERE code LIKE 'grade_test%';";
		jdbcTemplate.update(sql);
	}
}
