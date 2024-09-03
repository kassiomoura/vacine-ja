package com.ufcg.psoft.vacinaja.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.ufcg.psoft.vacinaja.enums.ProfissoesEnum;

@Entity
public class Profissoes {

	@Id
	private ProfissoesEnum profissoesEnum;

	public Profissoes() {
	}

	public Profissoes(ProfissoesEnum profissoesEnum) {
		this.profissoesEnum = profissoesEnum;
	}

	public ProfissoesEnum getProfissoes() {
		return profissoesEnum;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((profissoesEnum == null) ? 0 : profissoesEnum.hashCode());
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
		Profissoes other = (Profissoes) obj;
		if (profissoesEnum == null) {
			if (other.profissoesEnum != null)
				return false;
		} else if (!profissoesEnum.equals(other.profissoesEnum))
			return false;
		return true;
	}

}
