package com.ufcg.psoft.vacinaja.DTO;

import com.ufcg.psoft.vacinaja.enums.TipoUsuario;

public class CredenciaisDTO {
	
	private String cpf;
	private String senha;
	private TipoUsuario tipoUsuario;
	
	
	public String getCpf() {
		return cpf;
	}
	public String getSenha() {
		return senha;
	}
	public TipoUsuario getTipoUsuario() {
		return tipoUsuario;
	}

}
