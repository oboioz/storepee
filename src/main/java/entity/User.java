package entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class User {
	
	
	private Integer userId;
	private String email;
	private String name;
	private String password;
	
	public User() {
	}

	public User(Integer userId, String email, String fullName, String password) {
		this(email, fullName, password);
		this.userId = userId;
	}
	
	public User(String email, String name, String password) {
		super();
		this.email = email;
		this.name = name;
		this.password = password;
	}
	

	
	@Column(name = "user_id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	

	@Override
	public String toString() {
		return this.name;
	}
	
	
	
	
	
}
