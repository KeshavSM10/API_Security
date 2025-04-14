package user;

import jakarta.persistence.*;

import jakarta.persistence.Id;
import monetization.APIPlan;
	
@Entity
@Table(name = "users")
public class User {
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Long id;

	 private String username;
	 private String password;

	 @Enumerated(EnumType.STRING)
	 private APIPlan apiPlan = APIPlan.FREE;
	 
	 private String email;
	 private int age;
	 private String name;
	 
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public APIPlan getApiPlan() {
		return apiPlan;
	}
	public void setApiPlan(APIPlan apiPlan) {
		this.apiPlan = apiPlan;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}

}
