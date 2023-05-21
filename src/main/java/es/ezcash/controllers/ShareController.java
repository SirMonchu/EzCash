package es.ezcash.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import es.ezcash.models.User;
import jakarta.servlet.http.HttpSession;

@Controller
public class ShareController {
	
	@GetMapping("/Inviationes")
	public String Invitaciones(Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        if (user != null) {
        	model.addAttribute("referral_code", user.getReferralCode());
            model.addAttribute("username", user.getUsername());
        }
		return "Inviationes";
	}
	
	
	@GetMapping("/obtener-codigo-referencia")
	public ResponseEntity<?> obtenerCodigoReferencia(HttpSession session) {
	    User user = (User) session.getAttribute("user");
	    if (user != null) {
	        int referralCode = user.getReferralCode();
	        // Devuelve el código de referencia como respuesta
	        return ResponseEntity.ok(referralCode);
	    } else {
	        // Devuelve una respuesta adecuada en caso de que no haya un usuario en sesión
	        return ResponseEntity.badRequest().body("No se encontró un usuario en sesión.");
	    }
	}

}
