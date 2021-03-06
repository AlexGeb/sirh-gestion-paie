package dev.paie.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import dev.paie.entite.RemunerationEmploye;
import dev.paie.repository.EntrepriseRepository;
import dev.paie.repository.GradeRepository;
import dev.paie.repository.ProfilRemunerationRepository;
import dev.paie.repository.RemunerationEmployeRepository;

@Controller
@RequestMapping("/employes")
public class RemunerationEmployeController {
	private static final Logger LOGGER = LoggerFactory.getLogger(RemunerationEmployeController.class);

	@Autowired
	EntrepriseRepository entrepriseRepo;

	@Autowired
	ProfilRemunerationRepository profilRepo;

	@Autowired
	GradeRepository gradeRepo;

	@Autowired
	RemunerationEmployeRepository remunerationEmployeRepo;

	@RequestMapping(method = RequestMethod.GET, path = "/creer")
	@Secured({"ROLE_ADMINISTRATEUR"})
	public ModelAndView creerEmploye() {
		ModelAndView mv = new ModelAndView();
		RemunerationEmploye remunerationEmploye = new RemunerationEmploye();
		mv.setViewName("employes/creerEmploye");
		mv.addObject("entreprises", entrepriseRepo.findAll());
		mv.addObject("profils", profilRepo.findAll());
		mv.addObject("grades", gradeRepo.findAll());
		mv.addObject("remunerationEmploye", remunerationEmploye);
		return mv;
	}

	@RequestMapping(method = RequestMethod.POST, path = "/creer")
	@Secured({"ROLE_ADMINISTRATEUR"})
	public String ajouterEmploye(@ModelAttribute("remunerationEmploye") RemunerationEmploye remunerationEmploye,
			@RequestParam("grade_id") Integer grade_id, @RequestParam("entreprise_id") Integer entreprise_id,
			@RequestParam("profil_id") Integer profil_id) {
		remunerationEmploye.setGrade(gradeRepo.findOne(grade_id));
		remunerationEmploye.setEntreprise(entrepriseRepo.findOne(entreprise_id));
		remunerationEmploye.setProfilRemuneration(profilRepo.findOne(profil_id));
		remunerationEmployeRepo.save(remunerationEmploye);
		return "redirect:lister";
	}

	@RequestMapping(method = RequestMethod.GET, path = "/lister")
	@Secured({"ROLE_UTILISATEUR", "ROLE_ADMINISTRATEUR"})
	public ModelAndView listerEmployes() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("employes/listerEmployes");
		List<RemunerationEmploye> employes = remunerationEmployeRepo.findAll();
		mv.addObject("employes", employes);
		return mv;
	}

}
