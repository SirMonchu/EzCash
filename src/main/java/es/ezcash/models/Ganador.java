package es.ezcash.models;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@EnableJpaRepositories
@Data
@Entity
@Table(name="ganadores")
public class Ganador {
	
	@Id
	@JdbcTypeCode(SqlTypes.INTEGER)
	@Column(name="id")
	private int id;
	
	@Column(name = "username")
    private String username;
	
	@Column(name = "premio")
    private double premio;

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the premio
	 */
	public double getPremio() {
		return premio;
	}

	/**
	 * @param premio the premio to set
	 */
	public void setPremio(double premio) {
		this.premio = premio;
	}

	@Override
	public String toString() {
		return "Ganador [username=" + username + ", premio=" + premio + "]";
	}
	
    
    
}
