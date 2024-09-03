package com.ufcg.psoft.vacinaja.model.estagiovacinacao;

import java.time.LocalDateTime;

import javax.persistence.Entity;

import com.ufcg.psoft.vacinaja.enums.EstagioVacinacaoEnum;
import com.ufcg.psoft.vacinaja.model.Cidadao;
import com.ufcg.psoft.vacinaja.model.Vacina;

@Entity
public class HabilitadoPrimeiraDose extends EstagioVacinacao {
	
	public HabilitadoPrimeiraDose() {
		super.estagioVacinacao = EstagioVacinacaoEnum.HABILITADO_PRIMEIRA_DOSE;
	}
	
	@Override
	public void vacina(Cidadao cidadao, Vacina vacina, LocalDateTime dataPrimeiraDose) {
		if (vacina.getQntDosesNecessarias() == 1) {
			cidadao.getStatusVacinacao().setDataPrimeiraDose(dataPrimeiraDose);
			cidadao.getStatusVacinacao().setEstagioVacinacao(new VacinacaoFinalizada());
		} 
		else if (vacina.getQntDosesNecessarias() == 2) {
			cidadao.getStatusVacinacao().setDataPrimeiraDose(dataPrimeiraDose);
			cidadao.getStatusVacinacao().setEstagioVacinacao(new EsperandoSegundaDose());
		}
	}
}
