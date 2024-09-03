package com.ufcg.psoft.vacinaja.model.estagiovacinacao;

import javax.persistence.Entity;

import com.ufcg.psoft.vacinaja.enums.EstagioVacinacaoEnum;
import com.ufcg.psoft.vacinaja.model.Cidadao;

@Entity
public class EsperandoSegundaDose extends EstagioVacinacao {

	public EsperandoSegundaDose() {
		super.estagioVacinacao = EstagioVacinacaoEnum.ESPERANDO_SEGUNDA_DOSE;
	}
	
	@Override
	public void habilita(Cidadao cidadao) {
		cidadao.getStatusVacinacao().setEstagioVacinacao(new HabilitadoSegundaDose());
	}
}
