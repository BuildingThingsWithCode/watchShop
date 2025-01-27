package com.watchShop.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import org.springframework.web.bind.annotation.SessionAttributes;

import com.watchShop.model.Form;
import com.watchShop.model.RegisterForm;
import com.watchShop.model.Watch;
import com.watchShop.service.CartService;
import com.watchShop.service.QuoteService;
import com.watchShop.service.UserService;
import com.watchShop.service.WatchService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@SessionAttributes("soldWatches")
public class UserController {

	private final UserService userService;
	private final WatchService watchService;
	private final QuoteService quoteService;
	private final CartService cartService;

	@GetMapping("/")
	public String home(@RequestParam(name = "sortOrder", defaultValue = "brand") String sortOrder, Model model) {
		List<Watch> watches = watchService.findAllByOrder(sortOrder);
		model.addAttribute("watches", watches);
		model.addAttribute("sortOrder", sortOrder);
		return "home";  
	}

	@GetMapping("/login")
	public String showLoginPage(Form form, Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		boolean isAuthenticated = false;
		if (authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
			isAuthenticated = true;
		}
		model.addAttribute("authentication", isAuthenticated);
		return "login2";
	}
	
	/*
	 * Automatic Model Binding: When you use @Valid Form form, as a parameter Spring MVC 
	 * automatically: a) Creates a Form object, b) Binds the request parameters to it 
	 * c) Adds it to the model with the attribute name "form" (derived from the class name
	 * with first letter lowercase). All this happens BEFORE your controller method is 
	 * even executed.
	 * So, we do not need to bind the form attribute to the model. Spring handles this for us.
	 */
	@PostMapping("/login")
	public String loginUser(@Valid Form form, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "login2"; 
		}
		else {
			userService.authenticateUser(form.getUsername(), form.getPassword());
			return "redirect:/";
		}
	}

	@GetMapping("/register")
	public String showRegisterPage(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		boolean authenticated = false;
		if (authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
			authenticated = true;
		}
		model.addAttribute("authentication", authenticated);
		if (!authenticated) model.addAttribute("form", new RegisterForm());
		return "register2"; 
	}

	@PostMapping("/register")
	public String createAndSaveUser(@Valid @ModelAttribute("form") RegisterForm form, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "register2";
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
		return "admin2";
	}

	@GetMapping("/noaccess")
	public String noAccessPage() {
		return "noaccess2";     
	}
	
	@PostMapping("/noaccess")
	public String sessionExpiredPage() {
		return "sessionexpired";
	}
	
	@GetMapping("/checkout")
	public String showCheckoutPage(Model model) {
		model.addAttribute("cartItems", cartService.getAll());
		model.addAttribute("totalPrice", cartService.getTotal());
		return "checkout";
	}
	
	@GetMapping("/completed-sale")
	public String finishSale(@ModelAttribute("soldWatches") Set<Long> soldWatches) {
		cartService.getAll().forEach(entry -> soldWatches.add(entry.getKey().getId()));
		cartService.emptyCart();
		return "redirect:/";
	}
	
	@ModelAttribute("soldWatches")
	public Set<Long> initializeSoldWatches() {
		return new HashSet<>();
	}
}
