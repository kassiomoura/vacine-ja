package com.ufcg.psoft.vacinaja.DTO;

import com.ufcg.psoft.vacinaja.enums.ComorbidadesEnum;
import com.ufcg.psoft.vacinaja.enums.ProfissoesEnum;

public class PerfilVacinacaoDTO {

	private int idade;

	private ProfissoesEnum profissao;

	private ComorbidadesEnum comorbidade;

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