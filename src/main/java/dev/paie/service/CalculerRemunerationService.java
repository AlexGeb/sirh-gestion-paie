package dev.paie.service;

import java.util.List;
import java.util.Map;

import dev.paie.entite.BulletinSalaire;
import dev.paie.entite.BulletinVisualisationObject;
import dev.paie.entite.ResultatCalculRemuneration;

public interface CalculerRemunerationService {
	ResultatCalculRemuneration calculer(BulletinSalaire bulletin);
	Map<BulletinSalaire, ResultatCalculRemuneration> calculerPourTous();
	ResultatCalculRemuneration calculerPourBulletinId(Integer bulletin_id);
	Map<String, List<BulletinVisualisationObject>> genererBulletin(Integer bulletin_id);
}
