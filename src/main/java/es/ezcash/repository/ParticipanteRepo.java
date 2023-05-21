package es.ezcash.repository;

import es.ezcash.models.Participante;
import jakarta.transaction.Transactional;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipanteRepo extends JpaRepository<Participante, Long> {
	
	@Query(value = "SELECT * FROM participantes", nativeQuery = true)
	public List<Participante> findAll();
	
	@Query(value = "SELECT * FROM participantes", nativeQuery = true)
	public List<Participante> findById();
	
	@Query(value = "INSERT INTO participantes (id, username) VALUES (?1, ?2)", nativeQuery = true)
    void insertPart(int id, String username);
	
	@Modifying
	@Transactional
	@Query(value = "TRUNCATE TABLE participantes", nativeQuery = true)
    void dropParts();
}

