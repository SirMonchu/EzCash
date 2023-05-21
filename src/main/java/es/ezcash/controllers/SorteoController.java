package es.ezcash.controllers;

import es.ezcash.models.Ganador;
import es.ezcash.models.Participante;
import es.ezcash.models.User;
import es.ezcash.repository.GanadorRepo;
import es.ezcash.repository.ParticipanteRepo;
import es.ezcash.repository.UserRepo;
import es.ezcash.services.ServicesUser;
import jakarta.servlet.http.HttpSession;
import java.text.DecimalFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

@Controller
public class SorteoController {
    private final ServicesUser userServices;
    private final ParticipanteRepo participanteRepo;
    private final UserRepo userRepo;
    private final GanadorRepo ganadorRepo;
    private User user;
    
    @Autowired
    public SorteoController(ServicesUser userServices, ParticipanteRepo participanteRepo, UserRepo userRepo, GanadorRepo ganadorRepo) {
        this.userServices = userServices;
        this.participanteRepo = participanteRepo;
        this.userRepo = userRepo;
        this.ganadorRepo = ganadorRepo;
    }

    @GetMapping("/Sorteo")
    public String Sorteo(Model model, HttpSession session) {
        List<Participante> listaParticipantes = participanteRepo.findAll();
        model.addAttribute("participantes", listaParticipantes);
        user = (User) session.getAttribute("user");
        List<Ganador> ganador = ganadorRepo.findAllWithoutGrouping();
        System.out.println(ganador.toString());
        // Agregar los ganadores al modelo para enviarlos al frontend
        model.addAttribute("ganador", ganador);
        if (user != null) {
        	model.addAttribute("valance", user.getValance());
            model.addAttribute("username", user.getUsername());
        }
        return "Sorteo";
    }
    
    @PostMapping("/agregar-usuario")
    public ResponseEntity<?> agregarUsuario() {
    	
    	int id = userRepo.getReferralCode(user.getUsername());
    	String username = user.getUsername();
        // Realiza la lógica para agregar el participante a la base de datos
        participanteRepo.insertPart(id, username);

        // Devuelve una respuesta adecuada, como un código de estado 200 OK
        return ResponseEntity.ok().build();
    }

    @GetMapping("/obtener-ganador")
    public ResponseEntity<?> obtenerGanador() {
        List<Participante> participantes = participanteRepo.findAll();
        if (participantes.isEmpty()) {
            return ResponseEntity.badRequest().body("No hay participantes registrados");
        }
        Random random = new Random(System.currentTimeMillis());
        Participante ganador = participantes.get(random.nextInt(participantes.size()));
        
        String usernameGanador = ganador.getUsername(); // Obtener el nombre de usuario del ganador
        int idGanador = ganador.getId(); // Obtener el ID del ganador

        Map<String, Object> response = new HashMap<>();
        response.put("idGanador", idGanador);
        response.put("ganador", usernameGanador);

        return ResponseEntity.ok().body(response);
    }


    
    @DeleteMapping("/renew-parts")
    public void deleteAll() {
    	List<Participante> participantes = participanteRepo.findAll();
    	if (participantes.isEmpty()) {
    		return;
    	} else {
    		participanteRepo.dropParts();
    	}
    	return;
    }

    @PutMapping("/actualizar-balance/{idGanador}")
    public ResponseEntity<?> actualizarBalance(@PathVariable int idGanador, @RequestBody Map<String, Double> request) {
      double cantidadGanada = request.get("cantidadGanada");
      int referral_code = idGanador;
      userRepo.updateValance(referral_code, cantidadGanada);
      return ResponseEntity.ok().body("Balance actualizado correctamente");
    }
    
    @PostMapping("/agregar-ganador/{idGanador}/{cantidadGanada}")
    public ResponseEntity<?> agregarGanador(@PathVariable int idGanador, @PathVariable double cantidadGanada) {
    	if (ganadorRepo.count() == 7) {
    		ganadorRepo.deleteLast();
    		System.out.println("Eliminar el ultimo");
    	}
        User ganador = userRepo.findById(idGanador);
        if (ganador == null) {
            return ResponseEntity.badRequest().body("El ganador no existe");
        }
        double premio = cantidadGanada;
        String usernameGanador = ganador.getUsername();
        ganadorRepo.insertGan(idGanador, usernameGanador, premio);
        return ResponseEntity.ok().build();
    }
}
