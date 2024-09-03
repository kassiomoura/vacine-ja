package com.ufcg.psoft.vacinaja.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


import com.ufcg.psoft.vacinaja.enums.NumeroDoseEnum;

@Entity
public class Agendamento {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne
	private Cidadao cidadao;
	
	private LocalDateTime data;
	private NumeroDoseEnum dose;
	
	public Agendamento() {
	}
	
	public Agendamento(Cidadao cidadao, LocalDateTime data, NumeroDoseEnum dose) {
		this.cidadao = cidadao;
		this.data = data;
		this.dose = dose;
	}

	public long getId() {
		return id;
	}

	public Cidadao getCidadao() {
		return cidadao;
	}

	public LocalDateTime getData() {
		return data;
	}

	public NumeroDoseEnum getDose() {
		return dose;
	}

	public void setData(LocalDateTime data) {
		this.data = data;
	}
}