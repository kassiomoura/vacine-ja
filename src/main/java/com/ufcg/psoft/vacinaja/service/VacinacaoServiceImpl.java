package com.ufcg.psoft.vacinaja.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.vacinaja.DTO.VacinacaoDTO;
import com.ufcg.psoft.vacinaja.enums.EstagioVacinacaoEnum;
import com.ufcg.psoft.vacinaja.enums.NumeroDoseEnum;
import com.ufcg.psoft.vacinaja.model.Agendamento;
import com.ufcg.psoft.vacinaja.model.Lote;
import com.ufcg.psoft.vacinaja.model.Vacinacao;
import com.ufcg.psoft.vacinaja.repository.VacinacaoRepository;
import com.ufcg.psoft.vacinaja.util.Util;

@Service
public class VacinacaoServiceImpl implements VacinacaoService {
	
	@Autowired
	private VacinacaoRepository vacinacaoRepository;
	
	@Autowired
	private AgendamentoService agendamentoService;
	
	@Autowired
	private LoteService loteService;
	
	@Autowired 
	private CidadaoService cidadaoService;
	
	@Override
	public void registraVacinacao(VacinacaoDTO vacinacaoDTO) {
		Optional<Agendamento> optAgendamento = agendamentoService.getAgendamentoByCidadaoCpfAndNumedoDaDose(vacinacaoDTO.getCpfCidadao(), vacinacaoDTO.getNumeroDose());
		if (optAgendamento.isEmpty()) {
			throw new IllegalArgumentException("Não exsite agendamento para a pessoa indicada");
		}
		
		Agendamento agendamento = optAgendamento.get();
		EstagioVacinacaoEnum estagioVacinacaoCidadao = agendamento.getCidadao().getEstagioVacinacao();
		
		verificaEstagioVacinacao(vacinacaoDTO.getNumeroDose(), estagioVacinacaoCidadao);			
		verificaData(agendamento.getData(),vacinacaoDTO.getData());		
		
		Optional<Lote> optLote = loteService.findById(vacinacaoDTO.getIdLote());		
		if (optLote.isEmpty()) {
			throw new IllegalArgumentException("O lote para este identificador não existe");
		}
		
		
		Lote lote = optLote.get();		
		validaLote(lote);		
		validaNumeroDose(vacinacaoDTO, lote);
				
		loteService.reduzQuantidade(vacinacaoDTO.getIdLote());
		cidadaoService.vacinaCidadao(vacinacaoDTO.getCpfCidadao(), lote.getVacina(), agendamento.getData());
		
		Vacinacao vacinacao = new Vacinacao(agendamento.getCidadao(), lote, vacinacaoDTO.getNumeroDose(), vacinacaoDTO.getData(), lote.getVacina());
		
		vacinacaoRepository.save(vacinacao);
		
		
	}

	private void validaNumeroDose(VacinacaoDTO vacinacaoDTO, Lote lote) {
		if ((vacinacaoDTO.getNumeroDose() == NumeroDoseEnum.SEGUNDA_DOSE) && 
				   !(vacinacaoRepository.findByCidadaoCpfAndNumeroDaDose(vacinacaoDTO.getCpfCidadao(), NumeroDoseEnum.PRIMEIRA_DOSE)).get().getVacina().equals(lote.getVacina())) {
					throw new IllegalArgumentException("A vacina indicada não corresponde ao da primeira dose");
				}
		
	}

	private void validaLote(Lote lote) {
		if(lote.isVencido()) {
			throw new IllegalArgumentException("O lote informado está fora de validade");
		}
		
		if (lote.getQuantidadeDeDoses() <= 0) {
			throw new IllegalArgumentException("Não há doses da vacina deste lote");
		}
		
	}

	private void verificaData(LocalDateTime dataAgendamento, LocalDateTime dataVacinacaoDTO) {
		if (!dataAgendamento.equals(Util.arredondaData(dataVacinacaoDTO))) {
			throw new IllegalArgumentException("A data informada não coincide com a do agendamento");
		}
		
	}

	private void verificaEstagioVacinacao(NumeroDoseEnum numeroDose, EstagioVacinacaoEnum estagioVacinacaoCidadao) {
		if(!((numeroDose == NumeroDoseEnum.PRIMEIRA_DOSE && estagioVacinacaoCidadao == EstagioVacinacaoEnum.HABILITADO_PRIMEIRA_DOSE)
		   ||(numeroDose == NumeroDoseEnum.SEGUNDA_DOSE && estagioVacinacaoCidadao == EstagioVacinacaoEnum.HABILITADO_SEGUNDA_DOSE))){
			   throw new IllegalArgumentException("Número da dose informado não coincide com o estágio de vacinação do cidadão informado.");
		   }
		
	}

	
}