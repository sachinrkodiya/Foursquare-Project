package com.megaProject.Application.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;


import org.springframework.stereotype.Component;

@Entity
@Component
@Table(name = "blacklists")
public class JwtBlackList {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	
	@NotBlank
	@Column
	String tokens;

	public String getTokens() {
		return tokens;
	}

	public void setTokens(String tokens) {
		this.tokens = tokens;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	public JwtBlackList() {
		
	}

	public JwtBlackList(@NotBlank String tokens) {
		super();
		this.tokens = tokens;
	}
	
	
	
	
	
	

}
