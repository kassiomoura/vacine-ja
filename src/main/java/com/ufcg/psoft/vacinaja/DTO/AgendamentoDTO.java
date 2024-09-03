package com.ufcg.psoft.vacinaja.DTO;

import java.time.LocalDateTime;

import com.ufcg.psoft.vacinaja.enums.NumeroDoseEnum;

public class AgendamentoDTO {
	private LocalDateTime data;
	private NumeroDoseEnum dose;
	
	public LocalDateTime getData() {
		return data;
	}

	public NumeroDoseEnum getDose() {
		return dose;
	}

}