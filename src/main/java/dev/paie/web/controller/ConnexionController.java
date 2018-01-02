package dev.paie.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/connexion")
public class ConnexionController {
	
	@GetMapping
	public String afficherPageCreer() {
		return "connexion";
	}
	
	@RequestMapping(method = RequestMethod.GET, path = "/logout")
	public String logout() {
		return "redirect:/mvc/connexion";
	}

}
