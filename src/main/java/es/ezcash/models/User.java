package es.ezcash.models;

import java.io.Serializable;
import java.util.List;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@EnableJpaRepositories
@Data
@Entity
@Table(name="users")
public class User implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	@JdbcTypeCode(SqlTypes.INTEGER)
	@Column(name="referral_code")
	private int referral_code;
	
	@Column(name="Valance")
	@JdbcTypeCode(SqlTypes.DOUBLE)
    private double valance;
	
	@Column(name="Username", unique = true)
	@JdbcTypeCode(SqlTypes.VARCHAR)
    private String username;
	
	@Column(name="Password")
	@JdbcTypeCode(SqlTypes.VARCHAR)
    private String password;
	
	@OneToMany
	@JoinColumn(name = "id", referencedColumnName = "referral_code")
	private List<Ganador> ganadores;


	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public User() {
		
	}

	/**
	 * @return the referralCode
	 */
	public int getReferralCode() {
		return referral_code;
	}

	/**
	 * @param referralCode the referralCode to set
	 */
	public void setReferralCode(int referral_code) {
		this.referral_code = referral_code;
	}

	/**
	 * @return the valance
	 */
	public double getValance() {
		return valance;
	}

	/**
	 * @param valance the valance to set
	 */
	public void setValance(double valance) {
		this.valance = valance;
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
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "User [referral_code=" + referral_code + ", valance=" + valance + ", username=" + username + "]";
	}

	
}


