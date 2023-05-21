package es.ezcash.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import es.ezcash.models.Ganador;
import jakarta.transaction.Transactional;

@Repository
public interface GanadorRepo extends JpaRepository<Ganador, Long> {
	
	@Query(value = "SELECT * FROM ganadores", nativeQuery = true)
	public List<Ganador> findAll();
	
	@Query(value = "SELECT COUNT(*) FROM ganadores", nativeQuery = true)
	public long count();
	
	@Query(value = "INSERT INTO ganadores (id, username, premio) VALUES (?1, ?2, ?3)", nativeQuery = true)
    void insertGan(int id, String username, double premio);
	
	@Modifying
	@Transactional
	@Query(value = "DELETE FROM ganadores ORDER BY columna_fecha ASC LIMIT 1", nativeQuery = true)
	void deleteLast();
	
	 @Query("SELECT g FROM Ganador g")
	 List<Ganador> findAllWithoutGrouping();
	
}
