package es.ezcash.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import es.ezcash.models.User;
import jakarta.servlet.http.HttpSession;

@Controller
public class WalletController {
	
	@GetMapping("/Cartera")
	public String Cartera(Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        if (user != null) {
        	model.addAttribute("valance", user.getValance());
            model.addAttribute("username", user.getUsername());
        }
		return "Cartera";
	}
	
}
