package es.ezcash.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import es.ezcash.models.User;
import es.ezcash.repository.UserRepo;
import jakarta.servlet.http.HttpSession;
import org.springframework.transaction.annotation.Transactional;

@Controller
public class ProfileController {

private final UserRepo userRepository;	

	@Autowired
	public ProfileController(UserRepo userRepository) {
	    this.userRepository = userRepository;
	}
    
	@GetMapping("/Perfil")
	public String showProfilePage(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            model.addAttribute("username", user.getUsername());
        }
	    return "Perfil";
	}
	
	@PostMapping("/update-username")
	@ResponseBody
	@Transactional
	public String updateUsername(@RequestBody String newUsername, HttpSession session) {
	    User currentUser = getCurrentUser(session);

	    if (currentUser != null) {
	        // Actualizar el nombre de usuario del usuario actual en la base de datos
	        userRepository.updateUsername(currentUser.getUsername(), newUsername.trim());

	        return "Nombre de usuario actualizado con éxito";
	    } else {
	        return "No se encontró un usuario actualmente autenticado";
	    }
	}
	
	private User getCurrentUser(HttpSession session) {
	    return (User) session.getAttribute("user");
	}

}
