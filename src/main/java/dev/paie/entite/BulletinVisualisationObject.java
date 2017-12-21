package dev.paie.entite;

public class BulletinVisualisationObject {

	private String rubrique;
	private String base;
	private String tauxSalarial;
	private String montantSalarial;
	private String tauxPatronal;
	private String cotisationsPatronales;

	/**
	 * @param rubriques
	 * @param tauxSalarial
	 * @param montantSalarial
	 * @param tauxPatronal
	 * @param cotisationsPatronales
	 */
	public BulletinVisualisationObject(String rubrique, String base, String tauxSalarial, String montantSalarial,
			String tauxPatronal, String cotisationsPatronales) {
		this.rubrique = rubrique;
		this.base = base;
		this.tauxSalarial = tauxSalarial;
		this.montantSalarial = montantSalarial;
		this.tauxPatronal = tauxPatronal;
		this.cotisationsPatronales = cotisationsPatronales;
	}

	public String getRubrique() {
		return rubrique;
	}

	public void setRubrique(String rubriques) {
		this.rubrique = rubriques;
	}

	public String getTauxSalarial() {
		return tauxSalarial;
	}

	public void setTauxSalarial(String tauxSalarial) {
		this.tauxSalarial = tauxSalarial;
	}

	public String getMontantSalarial() {
		return montantSalarial;
	}

	public void setMontantSalarial(String montantSalarial) {
		this.montantSalarial = montantSalarial;
	}

	public String getTauxPatronal() {
		return tauxPatronal;
	}

	public void setTauxPatronal(String tauxPatronal) {
		this.tauxPatronal = tauxPatronal;
	}

	public String getCotisationsPatronales() {
		return cotisationsPatronales;
	}

	public void setCotisationsPatronales(String cotisationsPatronales) {
		this.cotisationsPatronales = cotisationsPatronales;
	}

	/**
	 * @return the base
	 */
	public String getBase() {
		return base;
	}

	/**
	 * @param base
	 *            the base to set
	 */
	public void setBase(String base) {
		this.base = base;
	}

}
