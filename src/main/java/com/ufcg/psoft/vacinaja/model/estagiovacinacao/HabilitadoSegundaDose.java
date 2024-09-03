package com.ufcg.psoft.vacinaja.model.estagiovacinacao;

import java.time.LocalDateTime;

import javax.persistence.Entity;

import com.ufcg.psoft.vacinaja.enums.EstagioVacinacaoEnum;
import com.ufcg.psoft.vacinaja.model.Cidadao;
import com.ufcg.psoft.vacinaja.model.Vacina;

@Entity
public class HabilitadoSegundaDose extends EstagioVacinacao {

	public HabilitadoSegundaDose() {
		super.estagioVacinacao = EstagioVacinacaoEnum.HABILITADO_SEGUNDA_DOSE;
	}
	
	@Override
	public void vacina(Cidadao cidadao, Vacina vacina, LocalDateTime data) {
		if (vacina.getQntDosesNecessarias() == 2) {
			cidadao.getStatusVacinacao().setEstagioVacinacao(new VacinacaoFinalizada());
		}
	}
}
