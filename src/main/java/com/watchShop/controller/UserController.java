package com.watchShop.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.watchShop.model.Form;
import com.watchShop.model.RegisterForm;
import com.watchShop.model.Watch;
import com.watchShop.service.QuoteService;
import com.watchShop.service.UserService;
import com.watchShop.service.WatchService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;
	private final WatchService watchService;
	private final QuoteService quoteService;
	
	@GetMapping("/")
	public String home(@RequestParam(name = "sortOrder", defaultValue = "brand") String sortOrder, Model model, HttpServletResponse response) {
		List<Watch> watches = watchService.findAllByOrder(sortOrder);
		model.addAttribute("watches", watches);
		model.addAttribute("sortOrder", sortOrder);
		return "home";  
	}
	
	@GetMapping("/login")
	public String showLoginPage(Form form, Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		boolean isAuthenticated = false;
		if (authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
			isAuthenticated = true;
		}
		model.addAttribute("authentication", isAuthenticated);
		return "login";
	}

	@PostMapping("/login")
	public String loginUser(@Valid Form form, BindingResult result, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("form", form); 
			return "login"; 
		}
		else userService.authenticateUser(form.getUsername(), form.getPassword());
		return "redirect:/";
	}
		
	@GetMapping("/register")
	public String showRegisterPage(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		boolean isAuthenticated = false;
		if (authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
			isAuthenticated = true;
		}
		model.addAttribute("authentication", isAuthenticated);
	    model.addAttribute("form", new RegisterForm());
	    return "register"; 
	}

	@PostMapping("/register")
	public String createAndSaveUser(@Valid @ModelAttribute("form") RegisterForm form, BindingResult result, Model model) {
	    if (result.hasErrors()) {
	        model.addAttribute("form", form); 
	        return "register";
	    }
		userService.registerUser(form.getUsername(), form.getPassword(), form.getEmail());
		userService.authenticateUser(form.getUsername(), form.getPassword());
		return "redirect:/";
	}
	
	@GetMapping("/admin")
	public String showAdminPage(Model model) {
		Map<String, String> quote = quoteService.getQuote();
		model.addAttribute("quote", quote.get("q"));
		model.addAttribute("author", quote.get("a"));
		return "admin";
	}
	
	@GetMapping("/noaccess")
    public String noAccessPage() {
        return "noaccess";     }
}
