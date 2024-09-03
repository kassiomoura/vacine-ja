package com.ufcg.psoft.vacinaja.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.WebUtils;

import com.ufcg.psoft.vacinaja.DTO.AgendamentoDTO;
import com.ufcg.psoft.vacinaja.DTO.AlteraCidadaoDTO;
import com.ufcg.psoft.vacinaja.DTO.CidadaoDTO;
import com.ufcg.psoft.vacinaja.exceptions.UsuarioNaoAutorizadoException;
import com.ufcg.psoft.vacinaja.service.CidadaoService;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class CidadaoController {

	@Autowired
	private CidadaoService cidadaoService;

	@PostMapping("/cidadao")
	public ResponseEntity<?> cadastrarCidadao(@RequestBody CidadaoDTO cidadaoDTO) {
		try {
			cidadaoService.cadrastarCidadao(cidadaoDTO);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (NullPointerException |IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/comorbidades")
	public ResponseEntity<?> listarComorbidades() {
		return new ResponseEntity<>(cidadaoService.listarComorbidades(), HttpStatus.OK);
	}
	
	@PutMapping("/cidadao/alterar")
	public ResponseEntity<?> alterarCadastroCidadao(@RequestBody AlteraCidadaoDTO alteraCidadaoDTO,
			HttpServletRequest request) {
		try {
			String token = WebUtils.getCookie(request, "token").getValue();
			cidadaoService.alterarCadastroCidadao(alteraCidadaoDTO, token);
			return new ResponseEntity<>("Cadastro atualizado.",HttpStatus.OK);
		} catch (NullPointerException | IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/profissoes")
	public ResponseEntity<?> listarProfissoes() {
		return new ResponseEntity<>(cidadaoService.listarProfissoes(), HttpStatus.OK);
	}
	
	@GetMapping("/cidadao/vacinacao/status")
	public ResponseEntity<?> getStatusVacinacao(HttpServletRequest request) {
		try {
			String token = WebUtils.getCookie(request, "token").getValue();
			return new ResponseEntity<>(cidadaoService.getEstagioVacinacao(token), HttpStatus.OK);			
		} catch (UsuarioNaoAutorizadoException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);			
		}
	}
	
	@PostMapping("/cidadao/vacinacao/agendar")
	public ResponseEntity<?> agendar(@RequestBody AgendamentoDTO agendamentoDTO, HttpServletRequest request) {
		try {	
			String token = WebUtils.getCookie(request, "token").getValue();
			return new ResponseEntity<>(cidadaoService.agendar(agendamentoDTO, token), HttpStatus.CREATED);
		} catch (UsuarioNaoAutorizadoException | IllegalArgumentException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);			
		}	
	}
}