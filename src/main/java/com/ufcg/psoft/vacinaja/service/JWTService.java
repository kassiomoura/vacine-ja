package com.ufcg.psoft.vacinaja.service;

import javax.servlet.http.Cookie;

import com.ufcg.psoft.vacinaja.DTO.CredenciaisDTO;
import com.ufcg.psoft.vacinaja.enums.TipoUsuario;
import com.ufcg.psoft.vacinaja.exceptions.CredenciaisInvalidasException;

public interface JWTService {
	
	public Cookie autentica(CredenciaisDTO credenciais) throws CredenciaisInvalidasException;

	public void possuiAcesso(String token, TipoUsuario tipoUsuario);

	public String getUsuarioLogado(String token);

	public TipoUsuario[] getTiposDeUsuario();

	public Cookie logout();

}
