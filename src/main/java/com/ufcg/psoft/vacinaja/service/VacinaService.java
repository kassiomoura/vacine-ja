package com.ufcg.psoft.vacinaja.service;

import java.util.List;

import com.ufcg.psoft.vacinaja.DTO.VacinaDTO;
import com.ufcg.psoft.vacinaja.model.Vacina;

public interface VacinaService {
	
	
	public List<Vacina> listaVacinas();
	
	public Vacina getVacinaById(String nome);
	
	public Vacina criaVacina(VacinaDTO vacinaDTO);
	
	public void salvaVacina(Vacina vacina);
	
	public void removeVacina(Vacina vacina);
}