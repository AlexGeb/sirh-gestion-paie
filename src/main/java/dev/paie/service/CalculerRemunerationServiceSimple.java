package dev.paie.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.paie.entite.BulletinSalaire;
import dev.paie.entite.Cotisation;
import dev.paie.entite.Grade;
import dev.paie.entite.ResultatCalculRemuneration;
import dev.paie.util.PaieUtils;

@Service
public class CalculerRemunerationServiceSimple implements CalculerRemunerationService {

	@Autowired
	private PaieUtils paieUtils;

	@Override
	public ResultatCalculRemuneration calculer(BulletinSalaire bulletin) {
		ResultatCalculRemuneration resultat = new ResultatCalculRemuneration();
		Grade grade = bulletin.getRemunerationEmploye().getGrade();

		// SALAIRE_BASE
		BigDecimal salaireDeBase = grade.getNbHeuresBase().multiply(grade.getTauxBase());
		String salaireDeBaseStr = paieUtils.formaterBigDecimal(salaireDeBase);
		BigDecimal salaireDeBaseRedefini = new BigDecimal(salaireDeBaseStr);
		resultat.setSalaireDeBase(salaireDeBaseStr);

		// SALAIRE_BRUT
		BigDecimal salaireBrut = salaireDeBaseRedefini.add(bulletin.getPrimeExceptionnelle());
		String salaireBrutStr = paieUtils.formaterBigDecimal(salaireBrut);
		BigDecimal salaireBrutRedefini = new BigDecimal(salaireBrutStr);
		resultat.setSalaireBrut(salaireBrutStr);

		// TOTAL_RETENUE_SALARIALE
		List<Cotisation> cotisationsNonImposables = bulletin.getRemunerationEmploye().getProfilRemuneration()
				.getCotisationsNonImposables();
		BigDecimal totalRetenueSalariale = cotisationsNonImposables.stream().filter(c -> c.getTauxSalarial() != null)
				.map(c -> c.getTauxSalarial().multiply(salaireBrutRedefini)).reduce((c1, c2) -> {
					return c1.add(c2);
				}).orElse(new BigDecimal("0.00"));
		String totalRetenueSalarialeStr = paieUtils.formaterBigDecimal(totalRetenueSalariale);
		BigDecimal totalRetenueSalarialeRedefini = new BigDecimal(totalRetenueSalarialeStr);
		resultat.setTotalRetenueSalarial(totalRetenueSalarialeStr);

		// TOTAL_COTISATIONS_PATRONALES
		BigDecimal totalCotisationsPatronales = cotisationsNonImposables.stream()
				.filter(c -> c.getTauxPatronal() != null).map(c -> c.getTauxPatronal().multiply(salaireBrutRedefini))
				.reduce((c1, c2) -> {
					return c1.add(c2);
				}).orElse(new BigDecimal("0.00"));
		resultat.setTotalCotisationsPatronales(paieUtils.formaterBigDecimal(totalCotisationsPatronales));

		// NET_IMPOSABLE
		BigDecimal netImposable = salaireBrutRedefini.subtract(totalRetenueSalarialeRedefini);
		resultat.setNetImposable(paieUtils.formaterBigDecimal(netImposable));

		// NET_A_PAYER
		List<Cotisation> cotisationsImposables = bulletin.getRemunerationEmploye().getProfilRemuneration()
				.getCotisationsImposables();
		BigDecimal totalCotisationsImposables = cotisationsImposables.stream().filter(c -> c.getTauxSalarial() != null)
				.map(c -> c.getTauxSalarial().multiply(salaireBrutRedefini)).reduce((c1, c2) -> {
					return c1.add(c2);
				}).orElse(new BigDecimal("0.00"));
		BigDecimal netAPayer = netImposable.subtract(totalCotisationsImposables);
		resultat.setNetAPayer(paieUtils.formaterBigDecimal(netAPayer));

		return resultat;
	}

}
