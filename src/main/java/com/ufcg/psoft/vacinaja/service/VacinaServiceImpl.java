package com.ufcg.psoft.vacinaja.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.vacinaja.DTO.VacinaDTO;
import com.ufcg.psoft.vacinaja.model.Vacina;
import com.ufcg.psoft.vacinaja.repository.VacinaRepository;
import com.ufcg.psoft.vacinaja.util.Util;

@Service
public class VacinaServiceImpl implements VacinaService {
	private static int NUM_MIN_DOSES = 1;
	private static int NUM_MAX_DOSES = 2;
	
	@Autowired
	private VacinaRepository vacinaRepository;
	

	@Override
	public List<Vacina> listaVacinas() {
		return vacinaRepository.findAll();
	}

	@Override
	public Vacina getVacinaById(String nome) {
		Optional<Vacina> optVacina = vacinaRepository.findById(nome);
		
		if (optVacina.isEmpty()) {
			throw new NullPointerException("O retorno para este nome é nulo, esta vacina não existe!");
		}
		
		return optVacina.get();
	}
	
	@Override
	public Vacina criaVacina(VacinaDTO vacinaDTO) {
		
		String nome = vacinaDTO.getNome().toLowerCase();
		String fabricante = vacinaDTO.getFabricante();
		int qntDosesNecessarias = vacinaDTO.getQntDosesNecessarias();
		int qntDiasEntreDoses = vacinaDTO.getQntDiasEntreDoses();
		
		if(qntDosesNecessarias == 1) {
			qntDiasEntreDoses = 0;
		}
		
		Util.verificaNull(nome, "nome");
		Util.verificaStringVazia(nome, "Nome");
		
		Util.verificaNull(fabricante, "Fabricante");
		Util.verificaStringVazia(fabricante, "Fabricante");
		
		
		if (qntDiasEntreDoses < 0) {
			throw new IllegalArgumentException("A quantidade de dias entre as doses é inválida!");
		}
		
		if (qntDosesNecessarias > NUM_MAX_DOSES || qntDosesNecessarias < NUM_MIN_DOSES) {
			throw new IllegalArgumentException("A quantidade de doses necessárias é inválida!");
		}
		
	
		Optional<Vacina> optVacina = this.vacinaRepository.findById(nome);
		
		if(optVacina.isPresent()) {
			throw new IllegalArgumentException("A vacina que você está tentando cadastrar já existe!");
		}
		
		Vacina vacina = new Vacina(nome, fabricante, qntDosesNecessarias, qntDiasEntreDoses);
		
		salvaVacina(vacina);
		
		return vacina;
	}
	
	@Override
	public void salvaVacina(Vacina vacina) {
		vacinaRepository.save(vacina);
	}

	@Override
	public void removeVacina(Vacina vacina) {
		vacinaRepository.delete(vacina);
	}

	
	
}