package es.ezcash.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.ezcash.models.User;
import jakarta.transaction.Transactional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
	
	@Query(value = "SELECT * FROM users", nativeQuery = true)
	public List<User> findAll();
	
	@Query(value = "SELECT * FROM users WHERE username = ?1 AND password = ?2", nativeQuery = true)
	User findByUsernameAndPassword(String username, String password);
	
	@Query(value = "SELECT referral_code FROM users WHERE username = ?1", nativeQuery = true)
	int getReferralCode(String username);
	
	@Query(value = "SELECT * FROM users WHERE username = ?1", nativeQuery = true)
	User findByUsername(String username);
	
	@Query(value = "SELECT * FROM users WHERE referral_code = ?1", nativeQuery = true)
	User findById(int referral_code);
	
	@Query(value = "SELECT * FROM users ORDER BY Valance desc", nativeQuery = true)
	List<User> findAllByOrderByValanceDesc();
	
	@Query(value = "INSERT INTO users (username, password) VALUES (?1, ?2)", nativeQuery = true)
    void insertUser(String username, String password);
	
	@Modifying
	@Query(value = "UPDATE users SET username = :newUsername WHERE username = :currentUsername", nativeQuery = true)
	void updateUsername(@Param("currentUsername") String currentUsername, @Param("newUsername") String newUsername);
	
	@Query(value = "SELECT * FROM users WHERE referral_code = :referral_code", nativeQuery = true)
	User findByReferralCode(@Param("referral_code") String referral_code);
	
	@Modifying
	@Transactional
	@Query(value = "UPDATE users u SET u.valance = u.valance + :cantidadGanada WHERE u.referral_code = :referral_code", nativeQuery = true)
	void updateValance(@Param("referral_code") int referral_code, @Param("cantidadGanada") double cantidadGanada);


}
