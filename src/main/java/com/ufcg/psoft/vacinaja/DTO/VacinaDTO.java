package com.ufcg.psoft.vacinaja.DTO;

public class VacinaDTO {
	private String nome;
	private String fabricante;
	private int qntDosesNecessarias;
	private int qntDiasEntreDoses;
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getFabricante() {
		return fabricante;
	}
	
	public void setFabricante(String fabricante) {
		this.fabricante = fabricante;
	}
	
	public int getQntDosesNecessarias() {
		return qntDosesNecessarias;
	}
	
	public void setQntDosesNecessarias(int qntDosesNecessarias) {
		this.qntDosesNecessarias = qntDosesNecessarias;
	}
	
	public int getQntDiasEntreDoses() {
		return qntDiasEntreDoses;
	}
	
	public void setQntDiasEntreDoses(int qntDiasEntreDoses) {
		this.qntDiasEntreDoses = qntDiasEntreDoses;
	}
}