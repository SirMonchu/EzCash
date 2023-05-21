package es.ezcash.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import es.ezcash.models.User;
import es.ezcash.repository.UserRepo;
import jakarta.servlet.http.HttpSession;

@Controller
public class PodioController {

    private UserRepo userRepository;

    @Autowired
    public PodioController(UserRepo userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/Podio")
    public String Podio(Model model, HttpSession session) {
        // Obtener los usuarios de la base de datos ordenados por balance descendente
        List<User> users = userRepository.findAllByOrderByValanceDesc();

        // Obtener los tres primeros usuarios
        List<User> topUsers = users.subList(0, Math.min(10, users.size()));
        // Agregar los valores al modelo
        model.addAttribute("topUsers", topUsers);

        User user = (User) session.getAttribute("user");
        if (user != null) {
            model.addAttribute("username", user.getUsername());
        }

        return "Podio";
    }
}
