package dev.paie.service;

import java.math.BigDecimal;
import java.util.List;

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
		resultat.setSalaireDeBase(paieUtils.formaterBigDecimal(salaireDeBase));
		BigDecimal salaireDeBaseArrondi = new BigDecimal(resultat.getSalaireDeBase());

		// SALAIRE_BRUT
		BigDecimal salaireBrut = salaireDeBaseArrondi.add(bulletin.getPrimeExceptionnelle());
		resultat.setSalaireBrut(paieUtils.formaterBigDecimal(salaireBrut));
		BigDecimal salaireBrutArrondi = new BigDecimal(resultat.getSalaireBrut());
		
		// TOTAL_RETENUE_SALARIALE
		List<Cotisation> cotisationsNonImposables = bulletin.getRemunerationEmploye().getProfilRemuneration()
				.getCotisationsNonImposables();
		BigDecimal totalRetenueSalariale = cotisationsNonImposables.stream().filter(c -> c.getTauxSalarial() != null)
				.map(c -> c.getTauxSalarial().multiply(salaireBrutArrondi)).reduce((c1, c2) -> {
					return c1.add(c2);
				}).orElse(new BigDecimal("0.00"));
		resultat.setTotalRetenueSalarial(paieUtils.formaterBigDecimal(totalRetenueSalariale));
		BigDecimal totalRetenueSalarialeArrondi = new BigDecimal(resultat.getTotalRetenueSalarial());
		
		// TOTAL_COTISATIONS_PATRONALES
		BigDecimal totalCotisationsPatronales = cotisationsNonImposables.stream()
				.filter(c -> c.getTauxPatronal() != null).map(c -> c.getTauxPatronal().multiply(salaireBrutArrondi))
				.reduce((c1, c2) -> {
					return c1.add(c2);
				}).orElse(new BigDecimal("0.00"));
		resultat.setTotalCotisationsPatronales(paieUtils.formaterBigDecimal(totalCotisationsPatronales));

		// NET_IMPOSABLE
		BigDecimal netImposable = salaireBrutArrondi.subtract(totalRetenueSalarialeArrondi);
		resultat.setNetImposable(paieUtils.formaterBigDecimal(netImposable));

		// NET_A_PAYER
		List<Cotisation> cotisationsImposables = bulletin.getRemunerationEmploye().getProfilRemuneration()
				.getCotisationsImposables();
		BigDecimal totalCotisationsImposables = cotisationsImposables.stream().filter(c -> c.getTauxSalarial() != null)
				.map(c -> c.getTauxSalarial().multiply(salaireBrutArrondi)).reduce((c1, c2) -> {
					return c1.add(c2);
				}).orElse(new BigDecimal("0.00"));
		BigDecimal netAPayer = netImposable.subtract(totalCotisationsImposables);
		resultat.setNetAPayer(paieUtils.formaterBigDecimal(netAPayer));

		return resultat;
	}

}
