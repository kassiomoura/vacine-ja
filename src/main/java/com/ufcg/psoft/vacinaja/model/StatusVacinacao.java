package com.ufcg.psoft.vacinaja.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.ufcg.psoft.vacinaja.enums.EstagioVacinacaoEnum;
import com.ufcg.psoft.vacinaja.model.estagiovacinacao.EstagioVacinacao;
import com.ufcg.psoft.vacinaja.model.estagiovacinacao.NaoHabilitado;

@Entity
public class StatusVacinacao {
	
	@Id
	private String cpfPessoa;
	
	@ManyToOne
	private Vacina tipoVacina;
	
	@ManyToOne
	private EstagioVacinacao estagioVacinacao;
	
	private LocalDateTime dataPrimeiraDose;
	
	public StatusVacinacao() {}
	
	
	public StatusVacinacao(String cpfPessoa) {
		this.cpfPessoa = cpfPessoa;
		this.estagioVacinacao = new NaoHabilitado();
	}
	
	public EstagioVacinacaoEnum getEstagioVacinacao() {
		return this.estagioVacinacao.get();
	}


	public boolean habilitaVacinacao(Cidadao cidadao, PerfilVacinacao perfilVacinacao) {
		return this.estagioVacinacao.habilita(cidadao, perfilVacinacao);		
	}


	public void setEstagioVacinacao(EstagioVacinacao estagioVacinacao) {
		this.estagioVacinacao = estagioVacinacao;
	} 
	
	public Vacina getVacina() {
		if(tipoVacina == null) {
			throw new IllegalArgumentException("Vacina nula!");
		}
		return tipoVacina;
	}


	public void vacina(Cidadao cidadao, Vacina vacina, LocalDateTime dataPrimeiraDose) {
		this.estagioVacinacao.vacina(cidadao, vacina, dataPrimeiraDose);
		this.tipoVacina = vacina;
	}
	
	public void setDataPrimeiraDose(LocalDateTime dataPrimeiraDose) {
		this.dataPrimeiraDose = dataPrimeiraDose;
	}

	public LocalDateTime getDataPrimeiraDose() {
		return this.dataPrimeiraDose;
	}


	public void habilitaVacinacao(Cidadao cidadao) {
		this.estagioVacinacao.habilita(cidadao);
	}
	
	
}
