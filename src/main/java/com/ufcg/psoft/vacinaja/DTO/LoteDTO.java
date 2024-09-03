package com.ufcg.psoft.vacinaja.DTO;

import java.time.LocalDate;

public class LoteDTO {
	
	private String nomeDaVacina;
	private int quantidadeDeDoses;
	private LocalDate dataDeValidade;
	

	public LoteDTO(String nomeDaVacina, int quantidadeDeDoses, LocalDate dataDeValidade) {
		this.nomeDaVacina = nomeDaVacina;
		this.quantidadeDeDoses = quantidadeDeDoses;
		this.dataDeValidade = dataDeValidade;
	}
	
	
	public LocalDate getDataDeValidade() {
		return dataDeValidade;
	}

	public void setDataDeValidade(LocalDate dataDeValidade) {
		this.dataDeValidade = dataDeValidade;
	}


	public int getQuantidadeDeDoses() {
		return quantidadeDeDoses;
	}

	public void setQuantidadeDeDoses(int quantidadeDeDoses) {
		this.quantidadeDeDoses = quantidadeDeDoses;
	}

	public String getNomeDaVacina() {
		return nomeDaVacina;
	}

	
}
