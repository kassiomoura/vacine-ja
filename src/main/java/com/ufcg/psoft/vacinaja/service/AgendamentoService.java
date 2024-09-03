package com.ufcg.psoft.vacinaja.service;

import java.util.Optional;

import com.ufcg.psoft.vacinaja.DTO.AgendamentoDTO;
import com.ufcg.psoft.vacinaja.enums.NumeroDoseEnum;
import com.ufcg.psoft.vacinaja.model.Agendamento;
import com.ufcg.psoft.vacinaja.model.Cidadao;

public interface AgendamentoService {
	public String agendar(AgendamentoDTO agendamentoDTO, Cidadao cidadao);
	
	public Optional<Agendamento> getAgendamentoByCidadaoCpfAndNumedoDaDose(String cpf, NumeroDoseEnum dose);
}