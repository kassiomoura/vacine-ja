package com.ufcg.psoft.vacinaja.controller;

import java.util.List;

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

import com.ufcg.psoft.vacinaja.DTO.FuncionarioDTO;
import com.ufcg.psoft.vacinaja.DTO.LoteDTO;
import com.ufcg.psoft.vacinaja.DTO.PerfilVacinacaoDTO;
import com.ufcg.psoft.vacinaja.DTO.VacinacaoDTO;
import com.ufcg.psoft.vacinaja.exceptions.UsuarioNaoAutorizadoException;
import com.ufcg.psoft.vacinaja.model.Vacina;
import com.ufcg.psoft.vacinaja.service.FuncionarioService;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class FuncionarioController {

	@Autowired
	private FuncionarioService funcionarioService;

	@PostMapping("/funcionario")
	public ResponseEntity<?> solicitaCadastroFuncionario(@RequestBody FuncionarioDTO funcionarioDTO) {
		try {
			funcionarioService.solicitaCadastroFuncionario(funcionarioDTO);
			return new ResponseEntity<>(HttpStatus.CREATED);

		} catch (NullPointerException | IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/funcionario/vacina/lote")
	public ResponseEntity<?> criarLote(@RequestBody LoteDTO loteDTO, HttpServletRequest request) {
		try {
			String token = WebUtils.getCookie(request, "token").getValue();
			this.funcionarioService.criarLote(loteDTO, token);
			return new ResponseEntity<>(HttpStatus.CREATED);

		} catch (UsuarioNaoAutorizadoException | IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
		}

	}

	@GetMapping("/funcionario/vacina/lote")
	public ResponseEntity<?> listarLotes(HttpServletRequest request) {
		try {
			String token = WebUtils.getCookie(request, "token").getValue();
			return new ResponseEntity<>(this.funcionarioService.listarTodosOsLotes(token),HttpStatus.OK);
		} catch (UsuarioNaoAutorizadoException | IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
		} 
	}

	@PostMapping("/funcionario/vacinacao/perfil")
	public ResponseEntity<?> criaPerfilVacinacao(@RequestBody PerfilVacinacaoDTO perfilDTO,
			HttpServletRequest request) {
		try {
			String token = WebUtils.getCookie(request, "token").getValue();
			
			this.funcionarioService.criaPerfilVacinacao(perfilDTO, token);
			return new ResponseEntity<>(HttpStatus.CREATED);

		} catch (UsuarioNaoAutorizadoException | IllegalArgumentException | NullPointerException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
		}
	}
	
	@PostMapping("/funcionario/vacinacao/registra")
	public ResponseEntity<?> registraVacinacao(@RequestBody VacinacaoDTO vacinacaoDTO, HttpServletRequest request) {
		try {
			String token = WebUtils.getCookie(request, "token").getValue();
			this.funcionarioService.registraVacinacao(vacinacaoDTO, token);
			return new ResponseEntity<>(HttpStatus.CREATED);

		} catch (UsuarioNaoAutorizadoException | IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
		}
	}
	
	@GetMapping("/funcionario/vacina") // ??
	public ResponseEntity<?> listaVacinas(HttpServletRequest request) {		
		try {
			String token = WebUtils.getCookie(request, "token").getValue();
			return new ResponseEntity<List<Vacina>>(funcionarioService.listaTiposVacinas(token), HttpStatus.OK);

		} catch (UsuarioNaoAutorizadoException | IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
		}
	}
}