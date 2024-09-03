package com.ufcg.psoft.vacinaja.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Vacina {
	@Id
	private String nome;
	private String fabricante;
	private int qntDosesNecessarias;
	private int qntDiasEntreDoses;
	
	public Vacina() {
		super();
	}

	public Vacina(String nome, String fabricante, int qntDosesNecessarias, int qntDiasEntreDoses) {
		super();
		this.nome = nome;
		this.fabricante = fabricante;
		this.qntDosesNecessarias = qntDosesNecessarias;
		this.qntDiasEntreDoses = qntDiasEntreDoses;
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
	
	public String getFabricante() {
		return fabricante;
	}
	
	public void setFabricante(String fabricante) {
		this.fabricante = fabricante;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fabricante == null) ? 0 : fabricante.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vacina other = (Vacina) obj;
		if (fabricante == null) {
			if (other.fabricante != null)
				return false;
		} else if (!fabricante.equals(other.fabricante))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public String toString() {
		return nome + " - " + fabricante.toUpperCase() + ", quantidade de doses necessárias = " + qntDosesNecessarias
				+ ", quantidade de dias entre as doses = " + qntDiasEntreDoses;
	}
}