package dev.paie.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import dev.paie.entite.BulletinSalaire;
import dev.paie.entite.BulletinVisualisationObject;
import dev.paie.entite.RemunerationEmploye;
import dev.paie.entite.ResultatCalculRemuneration;
import dev.paie.repository.BulletinSalaireRepository;
import dev.paie.repository.PeriodeRepository;
import dev.paie.repository.RemunerationEmployeRepository;
import dev.paie.service.CalculerRemunerationService;

@Controller
@RequestMapping("/bulletins")
public class BulletinSalaireController {
	private static final Logger LOGGER = LoggerFactory.getLogger(BulletinSalaireController.class);

	@Autowired
	RemunerationEmployeRepository remunerationEmployeRepo;

	@Autowired
	PeriodeRepository periodeRepo;

	@Autowired
	BulletinSalaireRepository bulletinSalaireRepo;

	@Autowired
	CalculerRemunerationService calculRemuService;

	@RequestMapping(method = RequestMethod.GET, path = "/creer")
	public ModelAndView creerBulletinSalaire() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("bulletins/creerBulletin");
		mv.addObject("periodes", periodeRepo.findAll());
		mv.addObject("employes", remunerationEmployeRepo.findAll());
		BulletinSalaire bulletinSalaire = new BulletinSalaire();
		mv.addObject("bulletinSalaire", bulletinSalaire);
		return mv;
	}

	@RequestMapping(method = RequestMethod.GET, path = "/visualiser/{bulletin_id}")
	public ModelAndView visualiserBulletinSalaire(@PathVariable int bulletin_id) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("bulletins/visualiserBulletin");
		mv.addObject("bulletin", bulletinSalaireRepo.findOne(bulletin_id));
		mv.addObject("resultat", calculRemuService.calculerPourBulletinId(bulletin_id));
		mv.addAllObjects(calculRemuService.genererBulletin(bulletin_id));
		return mv;
	}

	@RequestMapping(method = RequestMethod.POST, path = "/creer")
	public String ajouterBulletinSalaire(@ModelAttribute("bulletinSalaire") BulletinSalaire bulletinSalaire,
			@RequestParam("employe_id") Integer employe_id, @RequestParam("periode_id") Integer periode_id) {
		bulletinSalaire.setPeriode(periodeRepo.findOne(periode_id));
		bulletinSalaire.setRemunerationEmploye(remunerationEmployeRepo.findOne(employe_id));
		bulletinSalaireRepo.save(bulletinSalaire);
		return "redirect:lister";
	}

	@RequestMapping(method = RequestMethod.GET, path = "/lister")
	public ModelAndView listerBulletinSalaire() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("bulletins/listerBulletins");
		mv.addObject("bulletins_resultats", calculRemuService.calculerPourTous());
		return mv;
	}
}
