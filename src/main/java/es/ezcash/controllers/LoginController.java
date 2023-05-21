package es.ezcash.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import es.ezcash.models.User;
import es.ezcash.repository.UserRepo;
import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {
    private final UserRepo userRepository;

    @Autowired
    public LoginController(UserRepo userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(String username, String password, Model model, HttpSession session) {
        User user = userRepository.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            // Inicio de sesión exitoso, almacenar usuario en la sesión
            session.setAttribute("user", user);
            return "redirect:/Main";
        } else {
            // Error de inicio de sesión, mostrar mensaje de error
            model.addAttribute("error", "Credenciales inválidas");
            return "login";
        }
    }

    @GetMapping("/register")
    public String showRegistrationPage() {
        return "register";
    }

    @PostMapping("/register")
    public String register(String username, String password) {
        User user = new User(username, password);
        userRepository.save(user);
        // Realizar acciones adicionales después del registro
        return "redirect:/login";
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Invalidar la sesión actual
        return "redirect:/login"; // Redirigir a la página de inicio de sesión
    }
}
