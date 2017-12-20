package dev.paie.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
	public String ajouterEmploye(@ModelAttribute("remunerationEmploye") RemunerationEmploye remunerationEmploye,
			HttpServletRequest request, HttpServletResponse response) {
		remunerationEmploye.setGrade(gradeRepo.findOne(Integer.parseInt(request.getParameter("grade_id"))));
		remunerationEmploye.setEntreprise(entrepriseRepo.findOne(Integer.parseInt(request.getParameter("entreprise_id"))));
		remunerationEmploye.setProfilRemuneration(profilRepo.findOne(Integer.parseInt(request.getParameter("profil_id"))));
		remunerationEmployeRepo.save(remunerationEmploye);
		return "redirect:lister";
	}
	
	@RequestMapping(method = RequestMethod.GET, path = "/lister")
	public ModelAndView listerEmployes() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("employes/listerEmployes");
		List<RemunerationEmploye> employes = remunerationEmployeRepo.findAll();
		mv.addObject("employes", employes);
		return mv;
	}
	
}
