package com.ufcg.psoft.vacinaja.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.vacinaja.DTO.AgendamentoDTO;
import com.ufcg.psoft.vacinaja.enums.EstagioVacinacaoEnum;
import com.ufcg.psoft.vacinaja.enums.NumeroDoseEnum;
import com.ufcg.psoft.vacinaja.model.Agendamento;
import com.ufcg.psoft.vacinaja.model.Cidadao;
import com.ufcg.psoft.vacinaja.repository.AgendamentoRepository;
import com.ufcg.psoft.vacinaja.util.Util;

@Service
public class AgendamentoServiceImpl implements AgendamentoService{
	@Autowired
	private AgendamentoRepository agendamentoRepository;

	@Autowired
	private LoteService loteService;

	@Override
	public String agendar(AgendamentoDTO agendamentoDTO, Cidadao cidadao) {
		LocalDateTime data = Util.arredondaData(agendamentoDTO.getData());
		
		verificaNumeroDose(agendamentoDTO, cidadao);
		verificaDisponibilidade(data);
		if(agendamentoDTO.getDose() == NumeroDoseEnum.SEGUNDA_DOSE) {
			verificaData(agendamentoDTO, cidadao);
		}
		
		Agendamento agendamento;
		Optional<Agendamento> optAgendamento = agendamentoRepository.findByCidadaoAndDose(cidadao, agendamentoDTO.getDose());
		
		String msg = "";
		
		if (optAgendamento.isPresent()) {
			agendamento = optAgendamento.get();
			agendamento.setData(data);
			
			msg = geraMsg(data,"alterada");
			
		} else {
			
			agendamento = new Agendamento(cidadao, data, agendamentoDTO.getDose());
			msg = geraMsg(data,"marcada");
		}
		
		agendamentoRepository.save(agendamento);
		
		return msg;
	}
	
	@Override
	public Optional<Agendamento> getAgendamentoByCidadaoCpfAndNumedoDaDose(String cpf, NumeroDoseEnum dose) {
		return agendamentoRepository.findByCidadaoCpfAndNumeroDaDose(cpf, dose);
	}
	
	private String geraMsg(LocalDateTime data ,String s) {
		return String.format("Sua vacinação foi %s para o dia %s entre %s e %s.",
				s, formataDataApenasData(data), formataDataApenasHora(data),
				formataDataApenasHora(data.plusMinutes(Util.MINUTOS_ENTRE_AGENDAMENTOS -1)));
	}

	private void verificaData(AgendamentoDTO agendamentoDTO, Cidadao cidadao) {
		LocalDateTime dataPrimeiraDose = cidadao.getStatusVacinacao().getDataPrimeiraDose();
		int diasEntreDoses = cidadao.getStatusVacinacao().getVacina().getQntDiasEntreDoses();
		if(!agendamentoDTO.getData().isAfter(dataPrimeiraDose.plusDays(diasEntreDoses))) {
			throw new IllegalArgumentException(String.format(
					"A segunda dose só pode ser agendada para pelo menos %d dia(s) depois da primeira dose.",
					diasEntreDoses));
		}
		
	}

	private void verificaNumeroDose(AgendamentoDTO agendamentoDTO, Cidadao cidadao) {
		if (agendamentoDTO.getDose() == NumeroDoseEnum.PRIMEIRA_DOSE) {
			validaPrimeiraDose(agendamentoDTO, cidadao);
			
		} else if (agendamentoDTO.getDose() == NumeroDoseEnum.SEGUNDA_DOSE) {
			validaSegundaDose(agendamentoDTO, cidadao);
			
		} else {
			throw new IllegalArgumentException("Dose inválida");
		}
	}
	
	private void validaPrimeiraDose(AgendamentoDTO agendamentoDTO, Cidadao cidadao) {
		if (cidadao.getEstagioVacinacao() == EstagioVacinacaoEnum.NAO_HABILITADO) {
			throw new IllegalArgumentException("Não habilitado para primeira dose");
		
		} else if(cidadao.getEstagioVacinacao() != EstagioVacinacaoEnum.HABILITADO_PRIMEIRA_DOSE ) {
			throw new IllegalArgumentException("Já tomou a primeira dose");
		} else {
			if (!loteService.existeDoseDisponivel()) {
				throw new IllegalArgumentException("Não há dose disponivel");
			}
		}
	}
	
	private void validaSegundaDose(AgendamentoDTO agendamentoDTO, Cidadao cidadao){
		if (cidadao.getEstagioVacinacao() != EstagioVacinacaoEnum.HABILITADO_SEGUNDA_DOSE) {
			throw new IllegalArgumentException("Não habilitado para segunda dose");
		} else {
			if (!loteService.existeDoseDisponivel(cidadao.getStatusVacinacao().getVacina())) {
				throw new IllegalArgumentException("Não há dose disponivel");
			}
		}
	}

	private void verificaDisponibilidade(LocalDateTime data) {
		Optional<Agendamento> optAgendamento = agendamentoRepository.findByData(data);
		
		if (optAgendamento.isPresent()) {
			throw new IllegalArgumentException("Horário já ocupado");
		}
	}
	
	private String formataDataApenasData(LocalDateTime data) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		return data.format(formatter);
	}
	
	private String formataDataApenasHora(LocalDateTime data) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
		return data.format(formatter);
	}

	
}
