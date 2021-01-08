package com.megaProject.Application.controller;



import java.util.NoSuchElementException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.megaProject.Application.config.JwtTokenUtil;
import com.megaProject.Application.model.DAOUser;
import com.megaProject.Application.model.JwtBlackList;
import com.megaProject.Application.model.JwtRequest;
import com.megaProject.Application.model.JwtResponse;
import com.megaProject.Application.model.UserDTO;
import com.megaProject.Application.repository.BlacklistRepository;
import com.megaProject.Application.repository.FavouriteRepository;
import com.megaProject.Application.repository.PlaceRepository;
import com.megaProject.Application.repository.UserDao;
import com.megaProject.Application.response.MessageResponse;
import com.megaProject.Application.service.JwtUserDetailsService;


@RestController
@CrossOrigin
public class JwtAuthenticationController {
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private PasswordEncoder bcryptEncoder;
	
	
	@Autowired
	FavouriteRepository favouriteRepository;
	
	@Autowired
	PlaceRepository placeRepository;
	
	
	@Autowired
	BlacklistRepository jwtBlacklistRepository;
	


	@Autowired
	private JwtUserDetailsService userDetailsService;

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

		authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());

		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
				//.loadUserByUsername(authenticationRequest.getEmail());

		final String token = jwtTokenUtil.generateToken(userDetails);
		String email = authenticationRequest.getEmail();
		DAOUser value = userDao.findByEmail(email);
		
		


		return ResponseEntity.ok(new JwtResponse(token,value.getUsername(),value.getEmail(),value.getDate_of_birth(),
				value.getGender(),value.getPhone()));
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<?> saveUser(@RequestBody UserDTO user) throws Exception {
		return ResponseEntity.ok(userDetailsService.save(user));
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
	
	
	
	@PutMapping("/updateUser")
	@ResponseStatus(HttpStatus.CREATED)
	public MessageResponse updateUserDetails(@RequestBody UserDTO userValue) {
		try {
			DAOUser user = userDao.findByEmail(userValue.getEmail());
			if(!(userDao.existsByEmail(user.getEmail()))) {
				return new MessageResponse("email not found ");
				}
			user.setGender(userValue.getGender());
			user.setPhone(userValue.getPhone());
			user.setUsername(userValue.getUsername());
			user.setDate_of_birth(userValue.getDob());
			userDetailsService.update(user);
			
			
			
				
		} catch(NoSuchElementException e) {
			return new MessageResponse("Error"+e);
			
		}
		return new MessageResponse("User record successfully");
		}
	
	
	
	@PutMapping("/changePassword")
	@ResponseStatus(HttpStatus.CREATED)
	public MessageResponse changePassword(@RequestBody UserDTO userValue) {
		try {
			
			if(!(userDao.existsByEmail(userValue.getEmail()))) {
				return new MessageResponse("email not found ");
				}
			DAOUser user = userDao.findByEmail(userValue.getEmail());

			user.setPassword(bcryptEncoder.encode(userValue.getPassword()));
			userDetailsService.update(user);
			
			
			
				
		} catch(NoSuchElementException e) {
			return new MessageResponse("Error"+e);
			
		}
		return new MessageResponse("Password updated successfully");
		}
	
	
	@GetMapping("/getUser/{userId}")
	public DAOUser getUserById(@PathVariable(value = "userId") long userId) {
		return userDao.findByUserID(userId);
	}
	



	@PutMapping(value = "/logOut")
	public  MessageResponse logout(HttpServletRequest request,HttpSession httpSession){

	String bearerToken = request.getHeader("Authorization");
	String token=bearerToken.substring(7);
	JwtBlackList jwtBlacklist = new JwtBlackList(token);
	jwtBlacklistRepository.save(jwtBlacklist);
	return new MessageResponse("Logged Out successfully");
	}

	
	
}


