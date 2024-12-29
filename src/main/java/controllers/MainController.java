package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import service.ImageService;

@Controller
public class MainController {
	
	 @Autowired
	 private ImageService imageService;
	
	@GetMapping("/")
	public String home(Model model) {
		model.addAttribute("images", imageService.getAllImages());
		return "home";
	}

}
