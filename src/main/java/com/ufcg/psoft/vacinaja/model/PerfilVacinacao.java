package com.ufcg.psoft.vacinaja.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.ufcg.psoft.vacinaja.enums.ComorbidadesEnum;
import com.ufcg.psoft.vacinaja.enums.ProfissoesEnum;

@Entity
public class PerfilVacinacao {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private int idade;

	private ProfissoesEnum profissao;

	private ComorbidadesEnum comorbidade;

	public PerfilVacinacao() {
	}

	public PerfilVacinacao(int idade, ProfissoesEnum profissao, ComorbidadesEnum comorbidade) {
		this.idade = idade;
		this.profissao = profissao;
		this.comorbidade = comorbidade;
	}

	public int getIdade() {
		return idade;
	}

	public ProfissoesEnum getProfissao() {
		return profissao;
	}

	public ComorbidadesEnum getComorbidade() {
		return comorbidade;
	}
	

}