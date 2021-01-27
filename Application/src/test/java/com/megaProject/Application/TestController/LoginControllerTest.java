package com.megaProject.Application.TestController;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.megaProject.Application.controller.JwtAuthenticationController;
import com.megaProject.Application.model.JwtRequest;
import com.megaProject.Application.model.Place;
import com.megaProject.Application.repository.PlaceRepository;

@SpringBootTest
@AutoConfigureMockMvc
@EnableWebMvc
public class LoginControllerTest {

	private MockMvc mockMvc;

	@Autowired
	JwtAuthenticationController jwtAuthenticationController;

	@Autowired
	JwtRequest jwtRequest;
	
	@Autowired
	PlaceRepository placesRepo;
	
	@Autowired
	Place place;

	@BeforeEach
	public void setup() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(this.jwtAuthenticationController).build();
	}


}
