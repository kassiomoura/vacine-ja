package com.ufcg.psoft.vacinaja.service;

import java.util.Date;

import javax.servlet.http.Cookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.vacinaja.DTO.CredenciaisDTO;
import com.ufcg.psoft.vacinaja.enums.TipoUsuario;
import com.ufcg.psoft.vacinaja.exceptions.CredenciaisInvalidasException;
import com.ufcg.psoft.vacinaja.exceptions.UsuarioNaoAutorizadoException;
import com.ufcg.psoft.vacinaja.service.CidadaoService;
import com.ufcg.psoft.vacinaja.service.FuncionarioService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JWTServiceImpl implements JWTService {

	public static final String TOKEN_KEY = "Tokem Key";
	
	@Autowired
	private CidadaoService cidadaoService;
	
	@Autowired
	private FuncionarioService funcionarioService;

	@Override
	public Cookie autentica(CredenciaisDTO credenciais) throws CredenciaisInvalidasException {
		
		if(!validaUsuario(credenciais)) {
			throw new CredenciaisInvalidasException("Credenciais inválidas.");
		}

		String token = geraToken(credenciais);
		Cookie cookie = geraCookie(token);
		
		return cookie;
	}
	
	@Override
	public void possuiAcesso(String token, TipoUsuario tipoUsuario) throws UsuarioNaoAutorizadoException{
		
		
		String tipoUsuarioToken = (String) Jwts.parser()
									.setSigningKey(JWTServiceImpl.TOKEN_KEY)
									.parseClaimsJws(token)
									.getBody()
									.get("tipoUsuario");

		if(!tipoUsuario.toString().equals(tipoUsuarioToken)) {
			throw new UsuarioNaoAutorizadoException("Usuario não autorizado para realizar a operação");
		} 
	}
	
	@Override
	public String getUsuarioLogado(String token) {
		return Jwts.parser()
				.setSigningKey(JWTServiceImpl.TOKEN_KEY)
				.parseClaimsJws(token)
				.getBody()
				.getSubject();
	}
	
	@Override
	public TipoUsuario[] getTiposDeUsuario() {
		return TipoUsuario.values();
	}

	@Override
	public Cookie logout() {
		return geraCookieInvalido(geraTokenInvalido());
	}

	private String geraToken(CredenciaisDTO credenciais) {

		return Jwts.builder()
				.setSubject(credenciais.getCpf())
				.claim("tipoUsuario", credenciais.getTipoUsuario())
				.signWith(SignatureAlgorithm.HS512, TOKEN_KEY)
				.setExpiration(new Date(System.currentTimeMillis() + 30 * 60 * 1000))
				.compact();

	}
	
	private String geraTokenInvalido() {
		return Jwts.builder()
				.signWith(SignatureAlgorithm.HS512, TOKEN_KEY)
				.setExpiration(new Date(System.currentTimeMillis()))
				.compact();
	}

	private Cookie geraCookie(String token) {
		Cookie cookie = new Cookie("token", token);
		cookie.setPath("/");
		cookie.setHttpOnly(true);
		cookie.setMaxAge(30 * 60); // 30 minutos

		return cookie;
	}
	
	private Cookie geraCookieInvalido(String token) {
		Cookie cookie = new Cookie("token", token);
		cookie.setPath("/");
		cookie.setHttpOnly(true);
		cookie.setMaxAge(0);

		return cookie;
	}

	private boolean validaUsuario(CredenciaisDTO credenciais) {
		String cpf = credenciais.getCpf();
		String senha = credenciais.getSenha();
		TipoUsuario tipoUsuarioLogin = credenciais.getTipoUsuario();
		
		boolean valido = false;
		switch (tipoUsuarioLogin) {
		case CIDADAO:
			valido = this.validaCidadao(cpf,senha);
			break;
			
		case FUNCIONARIO:
			valido = this.validaFuncionario(cpf, senha);
			
			break;
			
		case ADMINISTRADOR:
			valido = this.validaFuncionario(cpf, senha) &&
					this.funcionarioService.isAdministrador(cpf);
			break;

		default:
			break;
		}
		return valido;
	}
	
	private boolean validaCidadao(String cpf, String senha) {
		return (this.cidadaoService.existeCidadao(cpf) &&
				this.cidadaoService.verificaSenha(cpf, senha));
	}
	
	private boolean validaFuncionario(String cpf, String senha) {
		return  (this.funcionarioService.existeFuncionario(cpf) &&
				this.funcionarioService.verificaSenha(cpf,senha));
	}


	
	

}
