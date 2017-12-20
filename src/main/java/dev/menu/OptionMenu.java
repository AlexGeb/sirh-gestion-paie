package dev.menu;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import dev.paie.service.CotisationService;

public abstract class OptionMenu {
	
	protected static final Logger LOGGER = LoggerFactory.getLogger(OptionMenu.class);

	
	@Autowired
	protected CotisationService cotisationService;
	
	protected String libelle;
	protected Map<String, OptionMenu> options = new HashMap<>();
	
	public OptionMenu(String libelle) {
		this.libelle = libelle;
	}
	

	
	
	/**
	 * The specific logic for each option is implemented here
	 * @return nothing
	 * @throws Exception
	 */
	public abstract void execute() throws Exception;


	/**
	 * @return the libelle
	 */
	public String getLibelle() {
		return libelle;
	}


	/**
	 * @param libelle the libelle to set
	 */
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

}
