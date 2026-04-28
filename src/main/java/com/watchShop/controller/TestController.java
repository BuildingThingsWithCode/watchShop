package com.watchShop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/student")
@Controller
public class TestController {
	


	    @GetMapping("/home")
	    public String home(Model model) {
	        model.addAttribute("username", "John Doe");
	        return "home";
	    }
}
