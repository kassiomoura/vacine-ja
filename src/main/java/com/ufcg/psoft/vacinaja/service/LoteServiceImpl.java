package com.ufcg.psoft.vacinaja.service;

import com.ufcg.psoft.vacinaja.repository.LoteRepository;
import com.ufcg.psoft.vacinaja.util.Util;
import com.ufcg.psoft.vacinaja.DTO.LoteDTO;
import com.ufcg.psoft.vacinaja.model.Lote;
import com.ufcg.psoft.vacinaja.model.Vacina;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service
public class LoteServiceImpl implements LoteService {
	
	@Autowired
	private LoteRepository loteRepository;
	
	@Autowired
	private VacinaService vacinaService;
	
	@Override
	public void criarLote(LoteDTO loteDTO) {
		verificaDadosDTO(loteDTO);
		Lote lote = gerarLote(loteDTO);
		loteRepository.save(lote);
		
	}

	@Override
	public List<LoteDTO> listarTodosOsLotes() {
		List<LoteDTO> loteDTO = new ArrayList<>();
		
		for (Lote lote : loteRepository.findAll()) {
			loteDTO.add(new LoteDTO(lote.getVacina().getNome(), lote.getQuantidadeDeDoses(), lote.getDataDeValidade()));
		}
		
		return loteDTO;	
	}

	@Override
	public boolean existeDoseDisponivel() {
		List<Lote> lotes = loteRepository.findAll();
		return this.existeDoseDisponivel(lotes);
	}

	@Override
	public boolean existeDoseDisponivel(Vacina vacina) {
		List<Lote> lotes = loteRepository.findAllByVacina(vacina);
		return this.existeDoseDisponivel(lotes);
	}
	
	@Override
	public Optional<Lote> findById(long id) {
		return loteRepository.findById(id);
	}

	@Override
	public void reduzQuantidade(long id) {
		Optional<Lote> optLote = findById(id);
		
		if (optLote.isEmpty()) {
			throw new IllegalArgumentException("O lote para o identificador indicado não existe");
		}
		
		Lote lote = optLote.get();
		lote.reduzQuantidade();
		
		loteRepository.save(lote);
	}

	@Override
	public List<Vacina> listarTiposVacina() {
		return this.vacinaService.listaVacinas();
	}
	
	private boolean existeDoseDisponivel(List<Lote> lotes) {
		
		for (Lote lote : lotes) {
			if(lote.getQuantidadeDeDoses() > 0 ) {
				return true;
			}
		}
		
		return false;
	}
	
	private void verificaDadosDTO(LoteDTO loteDTO) {
		String nomeDaVacina = loteDTO.getNomeDaVacina();
		int quantidadeDeDoses = loteDTO.getQuantidadeDeDoses();
		LocalDate dataDeValidade = loteDTO.getDataDeValidade();

		verificaNomeDaVacina(nomeDaVacina);
		verificaQntDeDoses(quantidadeDeDoses);
		Util.verificaNull(dataDeValidade, "Data de validade");
		
	}

	private void verificaQntDeDoses(int quantidadeDeDoses) {
		if (quantidadeDeDoses <= 0) {
			throw new IllegalArgumentException("Quantidade de doses inválido");
		}
		
	}

	private void verificaNomeDaVacina(String nomeDaVacina) {
		Util.verificaNull(nomeDaVacina, "Nome da vacina");
		Util.verificaStringVazia(nomeDaVacina, "Nome da vacina");
		
	}

	private Lote gerarLote(LoteDTO loteDTO) {
		
		Vacina vacina = vacinaService.getVacinaById(loteDTO.getNomeDaVacina().toLowerCase());
		return new  Lote(vacina, loteDTO.getQuantidadeDeDoses(), loteDTO.getDataDeValidade());
	}
}