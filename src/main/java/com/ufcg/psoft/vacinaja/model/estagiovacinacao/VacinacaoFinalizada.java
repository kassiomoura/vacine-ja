package com.ufcg.psoft.vacinaja.model.estagiovacinacao;

import javax.persistence.Entity;

import com.ufcg.psoft.vacinaja.enums.EstagioVacinacaoEnum;

@Entity
public class VacinacaoFinalizada extends EstagioVacinacao {

	public VacinacaoFinalizada() {
		super.estagioVacinacao = EstagioVacinacaoEnum.VACINACAO_FINALIZADA;
	}
}
