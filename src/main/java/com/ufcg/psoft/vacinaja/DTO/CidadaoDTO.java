package com.ufcg.psoft.vacinaja.DTO;

import java.time.LocalDate;
import java.util.List;

import com.ufcg.psoft.vacinaja.enums.ComorbidadesEnum;
import com.ufcg.psoft.vacinaja.enums.ProfissoesEnum;

public class CidadaoDTO {

	private String nome;

	private String endereco;
	
	private String cpf;

	private String cartaoSUS;

	private String email;
	
	private String senha;

	private LocalDate dataDeNascimento;

	private String telefone;

	private ProfissoesEnum profissao;

	private List<ComorbidadesEnum> comorbidades;

	public String getNome() {
		return nome;
	}

	public String getEndereco() {
		return endereco;
	}

	public String getCpf() {
		return cpf;
	}

	public String getCartaoSUS() {
		return cartaoSUS;
	}

	public String getEmail() {
		return email;
	}

	public String getSenha() {
		return senha;
	}

	public LocalDate getDataDeNascimento() {
		return dataDeNascimento;
	}

	public String getTelefone() {
		return telefone;
	}

	public ProfissoesEnum getProfissao() {
		return profissao;
	}

	public List<ComorbidadesEnum> getComorbidades() {
		return comorbidades;
	}
}