package com.ufcg.psoft.vacinaja.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.ufcg.psoft.vacinaja.enums.NumeroDoseEnum;

@Entity
public class Vacinacao {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idVacinacao;
	
	@ManyToOne //TODO: verificação da cardinalidade
	private Cidadao cidadao;
	
	@ManyToOne
	private Lote lote;
	
	private NumeroDoseEnum numeroDose;
	
	private LocalDateTime data;
	
	@ManyToOne
	private Vacina vacina;
	
	public Vacinacao() {
		super();
	}

	public Vacinacao(Cidadao cidadao, Lote lote, NumeroDoseEnum numeroDose, LocalDateTime data, Vacina vacina) {
		super();
		this.cidadao = cidadao;
		this.lote = lote;
		this.numeroDose = numeroDose;
		this.data = data;
		this.vacina = vacina;
	}

	public long getIdVacinacao() {
		return idVacinacao;
	}

	public Cidadao getCidadao() {
		return cidadao;
	}

	public Lote getLote() {
		return lote;
	}

	public NumeroDoseEnum getNumeroDose() {
		return numeroDose;
	}
	
	public LocalDateTime getData() {
		return data;
	}

	public Vacina getVacina() {
		return vacina;
	}
}