package com.ufcg.psoft.vacinaja.DTO;

import com.ufcg.psoft.vacinaja.model.Vacina;

public class VacinaQuantidadeDTO {
	
	private int quantidade;
	private Vacina vacina;
	
	public VacinaQuantidadeDTO() {
		
	}

	public VacinaQuantidadeDTO (Vacina vacina, int quantidade) {
		super();
		this.vacina = vacina;
		this.quantidade = quantidade;
	}
	
	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public Vacina getVacina() {
		return vacina;
	}

	public void setVacina(Vacina vacina) {
		this.vacina = vacina;
	}

	public void incrementaQuantd(int quantidade) {
		this.setQuantidade(this.getQuantidade() + quantidade);
	}
}
