package dev.paie.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.paie.entite.BulletinSalaire;
import dev.paie.entite.BulletinVisualisationObject;
import dev.paie.entite.Cotisation;
import dev.paie.entite.Grade;
import dev.paie.entite.ResultatCalculRemuneration;
import dev.paie.repository.BulletinSalaireRepository;
import dev.paie.util.PaieUtils;

@Service
@Transactional
public class CalculerRemunerationServiceSimple implements CalculerRemunerationService {

	@Autowired
	private PaieUtils paieUtils;

	@Autowired
	BulletinSalaireRepository bulletinSalaireRepo;

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

	@Override
	public Map<BulletinSalaire, ResultatCalculRemuneration> calculerPourTous() {
		List<BulletinSalaire> bulletins = bulletinSalaireRepo.findAll();
		Map<BulletinSalaire, ResultatCalculRemuneration> bulletins_resultats = new HashMap<>();
		bulletins.forEach(b -> {
			bulletins_resultats.put(b, calculer(b));
		});
		return bulletins_resultats;
	}

	@Override
	public ResultatCalculRemuneration calculerPourBulletinId(Integer bulletin_id) {
		return calculer(bulletinSalaireRepo.findOne(bulletin_id));
	}

	@Override
	public Map<String, List<BulletinVisualisationObject>> genererBulletin(Integer bulletin_id) {
		BulletinSalaire bulletin = bulletinSalaireRepo.findOne(bulletin_id);
		ResultatCalculRemuneration resultat = calculer(bulletin);

		// Rubrique SALAIRE
		List<BulletinVisualisationObject> salaire = new ArrayList<>();
		salaire.add(new BulletinVisualisationObject("Salaire de base",
				paieUtils.formaterBigDecimal(bulletin.getRemunerationEmploye().getGrade().getNbHeuresBase()),
				paieUtils.formaterBigDecimal(bulletin.getRemunerationEmploye().getGrade().getTauxBase()),
				resultat.getSalaireDeBase(), null, null));
		salaire.add(new BulletinVisualisationObject("Prime Except.", null, null,
				paieUtils.formaterBigDecimal(bulletin.getPrimeExceptionnelle()), null, null));
		salaire.add(new BulletinVisualisationObject("Salaire Brut", null, null, resultat.getSalaireBrut(), null, null));

		// Rubrique Cotisations non Imposables
		List<BulletinVisualisationObject> nonImposables = cotiztoVizu(
				bulletin.getRemunerationEmploye().getProfilRemuneration().getCotisationsNonImposables(), resultat);

		nonImposables.add(new BulletinVisualisationObject("Total Retenue", null, null,
				resultat.getTotalRetenueSalarial(), null, resultat.getTotalCotisationsPatronales()));

		// Rubrique Cotisations Imposables
		List<BulletinVisualisationObject> imposables = cotiztoVizu(
				bulletin.getRemunerationEmploye().getProfilRemuneration().getCotisationsImposables(), resultat);

		// Remplissage de l'objet final
		Map<String, List<BulletinVisualisationObject>> finalObject = new HashMap<>();
		finalObject.put("salaire", salaire);
		finalObject.put("nonImposables", nonImposables);
		finalObject.put("imposables", imposables);
		return finalObject;
	}

	private List<BulletinVisualisationObject> cotiztoVizu(List<Cotisation> cotisations,
			ResultatCalculRemuneration resultat) {
		return cotisations.stream().map(c -> {
			String montantSalarial = null;
			if (c.getTauxSalarial() != null) {
				montantSalarial = paieUtils
						.formaterBigDecimal(new BigDecimal(resultat.getSalaireBrut()).multiply(c.getTauxSalarial()));
			}
			String montantPatronal = null;
			if (c.getTauxPatronal() != null) {
				montantPatronal = paieUtils
						.formaterBigDecimal(new BigDecimal(resultat.getSalaireBrut()).multiply(c.getTauxPatronal()));
			}
			return new BulletinVisualisationObject(c.getCode() + " " + c.getLibelle(), resultat.getSalaireBrut(),
					paieUtils.fromCentiemesToPercent(c.getTauxSalarial()), montantSalarial,
					paieUtils.fromCentiemesToPercent(c.getTauxPatronal()), montantPatronal);
		}).collect(Collectors.toList());
	}

}
