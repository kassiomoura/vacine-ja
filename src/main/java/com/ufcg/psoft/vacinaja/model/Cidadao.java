package com.ufcg.psoft.vacinaja.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

import com.ufcg.psoft.vacinaja.enums.ComorbidadesEnum;
import com.ufcg.psoft.vacinaja.enums.EstagioVacinacaoEnum;
import com.ufcg.psoft.vacinaja.enums.ProfissoesEnum;

@Entity
public class Cidadao {

	private String nome;

	private String endereco;

	@Id
	private String cpf;

	private String cartaoSUS;

	private String email;

	private String senha;

	private LocalDate dataDeNascimento;

	private String telefone;

	private ProfissoesEnum profissao;
	
	@OneToOne
	private StatusVacinacao statusVacinacao;

	@ManyToMany
	private List<Comorbidades> comorbidades;

	public Cidadao() {
	}

	public Cidadao(String nome, String endereco, String cpf, String cartaoSUS, String email, String senha,
			LocalDate dataDeNascimento, String telefone, ProfissoesEnum profissao, List<Comorbidades> comorbidades) {
		this.nome = nome;
		this.endereco = endereco;
		this.cpf = cpf;
		this.cartaoSUS = cartaoSUS;
		this.email = email;
		this.senha = senha;
		this.dataDeNascimento = dataDeNascimento;
		this.telefone = telefone;
		this.profissao = profissao;
		this.comorbidades = comorbidades;
	}
	
	public void addComorbidade(ComorbidadesEnum comorbidade) {
		this.comorbidades.add(new Comorbidades(comorbidade));
	}

	public void setProfissao(ProfissoesEnum profissao) {
		this.profissao = profissao;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getCpf() {
		return cpf;
	}

	public String getCartaoSUS() {
		return cartaoSUS;
	}

	public void setCartaoSUS(String cartao_SUS) {
		this.cartaoSUS = cartao_SUS;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDate getDataDeNascimento() {
		return dataDeNascimento;
	}

	public void setDataDeNascimento(LocalDate dataDeNascimento) {
		this.dataDeNascimento = dataDeNascimento;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public ProfissoesEnum getProfissao() {
		return profissao;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public List<Comorbidades> getComorbidades() {
		return comorbidades;
	}

	public void setComorbidades(List<Comorbidades> comorbidades) {
		this.comorbidades = comorbidades;
	}
	
	public EstagioVacinacaoEnum getEstagioVacinacao() {
		return this.statusVacinacao.getEstagioVacinacao();
	}
	
	public void setStatusVacincao(StatusVacinacao statusVacinacao) {
		this.statusVacinacao = statusVacinacao;
	}
	
	@Override
	public String toString() {
		return this.cpf;
	}

	public boolean habilitaVacinacao(PerfilVacinacao perfilVacinacao) {
		return this.statusVacinacao.habilitaVacinacao(this, perfilVacinacao);		
	}
	
	// IMPLEMENTAR!!!!
	public int getIdade() {
		return Period.between(this.dataDeNascimento, LocalDate.now()).getYears();
	}

	public StatusVacinacao getStatusVacinacao() {
		return statusVacinacao;
	}

	public void vacina(Vacina vacina, LocalDateTime dataVacinacao) {
		this.statusVacinacao.vacina(this, vacina, dataVacinacao);
	}


}
