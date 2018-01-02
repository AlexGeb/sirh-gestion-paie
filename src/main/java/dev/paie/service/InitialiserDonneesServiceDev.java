package dev.paie.service;

import java.time.LocalDate;
import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.paie.entite.Cotisation;
import dev.paie.entite.Entreprise;
import dev.paie.entite.Grade;
import dev.paie.entite.Periode;
import dev.paie.entite.ProfilRemuneration;
import dev.paie.entite.RemunerationEmploye;
import dev.paie.entite.Utilisateur;

@Service
public class InitialiserDonneesServiceDev implements InitialiserDonneesService {

	@Autowired
	private ApplicationContext context;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@PersistenceContext
	private EntityManager em;

	@Override
	@Transactional
	public void initialiser() {
		Stream.of(Cotisation.class, Grade.class, Entreprise.class, ProfilRemuneration.class, RemunerationEmploye.class)
				.flatMap(maClasse -> context.getBeansOfType(maClasse).values().stream()).forEach(bean -> {
					em.persist(bean);
				});

		for (int month = 1; month <= 12; month++) {
			Periode p = new Periode();
			LocalDate dateDebut = LocalDate.of(2017, month, 1);
			LocalDate dateFin = dateDebut.withDayOfMonth(dateDebut.lengthOfMonth());
			p.setDateDebut(dateDebut);
			p.setDateFin(dateFin);
			em.persist(p);
		}

		Utilisateur user1 = new Utilisateur();
		user1.setNomUtilisateur("admin");
		user1.setMotDePasse(passwordEncoder.encode("admin"));
		user1.setRole(Utilisateur.ROLES.ROLE_ADMINISTRATEUR);
		user1.setEstActif(true);
		em.persist(user1);

		Utilisateur user2 = new Utilisateur();
		user2.setNomUtilisateur("user");
		user2.setMotDePasse(passwordEncoder.encode("user"));
		user2.setRole(Utilisateur.ROLES.ROLE_UTILISATEUR);
		user2.setEstActif(true);
		em.persist(user2);
	}

}
