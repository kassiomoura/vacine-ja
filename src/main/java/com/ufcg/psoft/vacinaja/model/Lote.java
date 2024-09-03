package com.ufcg.psoft.vacinaja.model;


import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Lote {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@OneToOne
	private Vacina vacina;
	private int quantidadeDeDoses;
	private LocalDate dataDeValidade;
	
	public Lote() {
		
	}

	public Lote(Vacina vacina, int quantidadeDeDoses, LocalDate dataDeValidade) {
		super();
		this.vacina = vacina;
		this.quantidadeDeDoses = quantidadeDeDoses;
		this.dataDeValidade = dataDeValidade;
	}
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Vacina getVacina() {
		return vacina;
	}

	public void setVacina(Vacina vacina) {
		this.vacina = vacina;
	}



	public LocalDate getDataDeValidade() {
		return dataDeValidade;
	}

	public void setDataDeValidade(LocalDate dataDeValidade) {
		this.dataDeValidade = dataDeValidade;
	}
	
	@Override
	public String toString() {
		return "Lote{" + "id=" + id + ", vacina=" + vacina.getNome() + ", numeroDeDoses=" + quantidadeDeDoses
				+ ", dataDeValidade='" + dataDeValidade + '\'' + '}';
	}
	
	public boolean isVencido() {		
		return this.dataDeValidade.isBefore(LocalDate.now());
	}

	public int getQuantidadeDeDoses() {
		return quantidadeDeDoses;
	}

	public void setQuantidadeDeDoses(int quantidadeDeDoses) {
		this.quantidadeDeDoses = quantidadeDeDoses;
	}
	
	public void reduzQuantidade() {
		this.quantidadeDeDoses--;
	}
}
