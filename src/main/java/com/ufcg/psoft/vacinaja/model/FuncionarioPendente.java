package com.ufcg.psoft.vacinaja.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class FuncionarioPendente {
	
	@Id
	private String cpf;
	
	private String cargo;
	private String localTrabalho;
	private String senha;
	
	@OneToOne
	private Cidadao cidadao;
	
	public FuncionarioPendente() {}
	
	public FuncionarioPendente(String cpf, String cargo, String localTrabalho, String senha, Cidadao cidadao) {
		this.cpf = cpf;
		this.cargo = cargo;
		this.localTrabalho = localTrabalho;
		this.senha = senha;
		this.cidadao = cidadao;
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

	public Cidadao getCidadao() {
		return cidadao;
	}

}
