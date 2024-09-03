package com.ufcg.psoft.vacinaja.DTO;

public class FuncionarioPendenteDTO {
	
	private String cpf;
	private String cargo;
	private String localTrabalho;
	
	public FuncionarioPendenteDTO(String cpf, String cargo, String localTrabalho) {
		this.cpf = cpf;
		this.cargo = cargo;
		this.localTrabalho = localTrabalho;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public String getLocalTrabalho() {
		return localTrabalho;
	}

	public void setLocalTrabalho(String localTrabalho) {
		this.localTrabalho = localTrabalho;
	}
	
}
