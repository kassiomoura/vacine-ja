package com.ufcg.psoft.vacinaja.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ufcg.psoft.vacinaja.DTO.CredenciaisDTO;
import com.ufcg.psoft.vacinaja.enums.TipoUsuario;
import com.ufcg.psoft.vacinaja.exceptions.CredenciaisInvalidasException;
import com.ufcg.psoft.vacinaja.service.JWTService;


@RestController
@RequestMapping("/api")
@CrossOrigin
public class LoginController {
	
	@Autowired
	private JWTService jwtService;
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody CredenciaisDTO credenciais, HttpServletResponse response) throws ServletException {
		
		try {
			response.addCookie(jwtService.autentica(credenciais));
			return new ResponseEntity<String>("Login efetuado com sucesso", HttpStatus.OK); 
		} catch (CredenciaisInvalidasException e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.UNAUTHORIZED);
		}
	}
	
	@GetMapping("/login")
	public ResponseEntity<?> getTiposDeUsuario(){

		return new ResponseEntity<TipoUsuario[]>(jwtService.getTiposDeUsuario(),HttpStatus.OK);
		

	}
	
	@PostMapping("/logout")
	public ResponseEntity<?> logout(HttpServletResponse response){
		try {
			response.addCookie(jwtService.logout());
			return new ResponseEntity<String>("Logout efetuado com sucesso", HttpStatus.OK); 
		} catch (CredenciaisInvalidasException e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.UNAUTHORIZED);
		}
		
	}

}
