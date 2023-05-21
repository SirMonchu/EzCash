package es.ezcash.services;

import java.util.List;

import org.springframework.stereotype.Service;

import es.ezcash.models.User;
import es.ezcash.repository.UserRepo;


@Service
public class ServicesUser implements InterfacesUser{
	
	UserRepo userRepo;

	public ServicesUser(UserRepo userRepo) {
		this.userRepo = userRepo;
	}

	@Override
	public List<User> findAll() {
		return userRepo.findAll();
	}
	
	
	
}
