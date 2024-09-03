package com.ufcg.psoft.vacinaja.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Funcionario {
	
	@Id
	private String cpf;
	
	private String cargo;
	private String localTrabalho;
	private String senha;
	private boolean isAdministrador;
	
	@OneToOne
	private Cidadao cidadao;
	
	public Funcionario() {}
	
	public Funcionario(String cpf, String cargo, String localTrabalho, String senha, Cidadao cidadao) {
		this.cpf = cpf;
		this.cargo = cargo;
		this.localTrabalho = localTrabalho;
		this.senha = senha;
		this.cidadao = cidadao;
	}
	
	public void setIsAdministrador(boolean bool) {
		this.isAdministrador = bool;
	}

	public String getCpf() {
		return cpf;
	}

	public String getCargo() {
		return cargo;
	}

	public String getLocalTrabalho() {
		return localTrabalho;
	}

	public String getSenha() {
		return senha;
	}

	public boolean isAdministrador() {
		return isAdministrador;
	}

	public Cidadao getCidadao() {
		return cidadao;
	}
	
	
}
