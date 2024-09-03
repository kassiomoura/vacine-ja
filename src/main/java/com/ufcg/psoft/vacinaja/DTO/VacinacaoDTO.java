package com.ufcg.psoft.vacinaja.DTO;

import java.time.LocalDateTime;

import com.ufcg.psoft.vacinaja.enums.NumeroDoseEnum;

public class VacinacaoDTO {
	private String cpfCidadao;
	
	private long idLote;
	
	private NumeroDoseEnum numeroDose;
	
	private LocalDateTime data;

	public String getCpfCidadao() {
		return cpfCidadao;
	}

	public LocalDateTime getData() {
		return data;
	}

	public void setData(LocalDateTime data) {
		this.data = data;
	}

	public void setCpfCidadao(String cpfCidadao) {
		this.cpfCidadao = cpfCidadao;
	}

	public long getIdLote() {
		return idLote;
	}

	public void setIdLote(long idLote) {
		this.idLote = idLote;
	}

	public NumeroDoseEnum getNumeroDose() {
		return numeroDose;
	}

	public void setNumeroDose(NumeroDoseEnum numeroDose) {
		this.numeroDose = numeroDose;
	}
}