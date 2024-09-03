package com.ufcg.psoft.vacinaja.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.WebUtils;

import com.ufcg.psoft.vacinaja.DTO.FuncionarioCpfDTO;
import com.ufcg.psoft.vacinaja.DTO.VacinaDTO;
import com.ufcg.psoft.vacinaja.model.Vacina;
import com.ufcg.psoft.vacinaja.service.VacinaService;
import com.ufcg.psoft.vacinaja.enums.TipoUsuario;
import com.ufcg.psoft.vacinaja.exceptions.UsuarioNaoAutorizadoException;
import com.ufcg.psoft.vacinaja.service.FuncionarioService;
import com.ufcg.psoft.vacinaja.service.JWTService;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class AdministradorController {
	
	private static final TipoUsuario USUARIO = TipoUsuario.ADMINISTRADOR;
	
	@Autowired
	private JWTService jwtService;
	
	@Autowired
	private VacinaService vacinaService;
	
	@Autowired 
	private FuncionarioService funcionarioService;

	@PostMapping("/admin/vacina")
	public ResponseEntity<?> criaVacina(@RequestBody VacinaDTO vacinaDTO, HttpServletRequest request) {
		try {
			String token = WebUtils.getCookie(request, "token").getValue();
			
			jwtService.possuiAcesso(token, USUARIO);
			
			Vacina vacina = vacinaService.criaVacina(vacinaDTO);
			
			return new ResponseEntity<Vacina>(vacina, HttpStatus.CREATED);
		} catch (UsuarioNaoAutorizadoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/admin/funcionario")//TODO nome do metodo
	public ResponseEntity<?> aprovaCadastroFuncionario(@RequestBody FuncionarioCpfDTO funcionarioCpfDTO, HttpServletRequest request) {
		try {	
			String token = WebUtils.getCookie(request, "token").getValue();
			
			jwtService.possuiAcesso(token, USUARIO);
			
			funcionarioService.aprovaCadastroFuncionario(funcionarioCpfDTO);
			
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (UsuarioNaoAutorizadoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
		}
		catch (IllegalArgumentException | NullPointerException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}	
	}
	
	@GetMapping("/admin/funcionario")
	public ResponseEntity<?> listaFuncionariosPendentes(HttpServletRequest request) {
		try {	
			String token = WebUtils.getCookie(request, "token").getValue();
			
			jwtService.possuiAcesso(token, USUARIO);
			
			return new ResponseEntity<>(funcionarioService.listaFuncionariosPendentes(),HttpStatus.OK);
		} catch (UsuarioNaoAutorizadoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
		}
		catch (IllegalArgumentException | NullPointerException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}	
	}
	
	
	
	
}