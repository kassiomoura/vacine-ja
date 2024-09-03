package com.ufcg.psoft.vacinaja.model.estagiovacinacao;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.ufcg.psoft.vacinaja.enums.EstagioVacinacaoEnum;
import com.ufcg.psoft.vacinaja.model.Cidadao;
import com.ufcg.psoft.vacinaja.model.PerfilVacinacao;
import com.ufcg.psoft.vacinaja.model.Vacina;


@Entity
public abstract class EstagioVacinacao {
	
	@Id
	protected EstagioVacinacaoEnum estagioVacinacao;
	
	public EstagioVacinacaoEnum get() {
		return this.estagioVacinacao;
	}

	public boolean habilita(Cidadao cidadao, PerfilVacinacao perfilVacinacao) {return false;}
	
	public void habilita(Cidadao cidadao) {}
	
	public void vacina(Cidadao cidadao, Vacina vacina, LocalDateTime dataVacinacao) {};

}
