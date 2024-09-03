package com.ufcg.psoft.vacinaja.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.ufcg.psoft.vacinaja.enums.ComorbidadesEnum;

@Entity
public class Comorbidades {

	@Id
	private ComorbidadesEnum comorbidade;

	public Comorbidades() {
	}

	public Comorbidades(ComorbidadesEnum comorbidade) {
		this.comorbidade = comorbidade;
	}

	public ComorbidadesEnum getComorbidade() {
		return comorbidade;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((comorbidade == null) ? 0 : comorbidade.hashCode());
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
		Comorbidades other = (Comorbidades) obj;
		if (comorbidade != other.comorbidade)
			return false;
		return true;
	}

}
