package com.megaProject.Application.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "user")
public class DAOUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column
	private String username;
	@Column
	private String image;
	@Column
	@NotBlank
	private String email;
	@Column
	private String date_of_birth;
	@Column
	private String gender;
	@Column
	@NotBlank
	private Long phone;
	@Column
	@NotBlank
	@JsonIgnore
	private String password;

	
	
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
/*
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
*/
	public String getDate_of_birth() {
		return date_of_birth;
	}

	public void setDate_of_birth(String date_of_birth) {
		this.date_of_birth = date_of_birth;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Long getPhone() {
		return phone;
	}

	public void setPhone(Long phone) {
		this.phone = phone;
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
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
	public DAOUser() {
		
	}
	public DAOUser(String username, String image, String email, String date_of_birth, String gender, Long phone,
			String password) {
		super();
		this.username = username;
		this.image = image;
		this.email = email;
		this.date_of_birth = date_of_birth;
		this.gender = gender;
		this.phone = phone;
		this.password = password;
	}	

}
