package com.watchShop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FragmentsController {
	
	  @GetMapping("/header")
	    public String showHeader() {
	        return "fragments/header";
	    }
}
