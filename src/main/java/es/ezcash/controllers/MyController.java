package es.ezcash.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import es.ezcash.models.User;
import jakarta.servlet.http.HttpSession;

@Controller
public class MyController  {
	@GetMapping("/")
	public String Default(){
		return "Main";
	}
	
	@GetMapping("/Main")
	public String Main(Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        if (user != null) {
            model.addAttribute("username", user.getUsername());
        }
		return "Main";
	}

}
