package com.ufcg.psoft.vacinaja.model.estagiovacinacao;

import javax.persistence.Entity;

import com.ufcg.psoft.vacinaja.enums.EstagioVacinacaoEnum;
import com.ufcg.psoft.vacinaja.model.Cidadao;
import com.ufcg.psoft.vacinaja.model.Comorbidades;
import com.ufcg.psoft.vacinaja.model.PerfilVacinacao;

@Entity
public class NaoHabilitado extends EstagioVacinacao {
	
	public NaoHabilitado() {
		super.estagioVacinacao = EstagioVacinacaoEnum.NAO_HABILITADO;
	}
	
	@Override
	public boolean habilita(Cidadao cidadao, PerfilVacinacao perfilVacinacao) {
		
		if (cidadao.getIdade() >= perfilVacinacao.getIdade() || cidadao.getProfissao() == perfilVacinacao.getProfissao() || 
			cidadao.getComorbidades().contains(new Comorbidades(perfilVacinacao.getComorbidade()))) {
			
			cidadao.getStatusVacinacao().setEstagioVacinacao(new HabilitadoPrimeiraDose());
			return true;
		}
		return false;
	}
	
}
