package com.ufcg.psoft.vacinaja.service;

import java.util.List;
import java.util.Optional;

import com.ufcg.psoft.vacinaja.DTO.LoteDTO;
import com.ufcg.psoft.vacinaja.model.Lote;
import com.ufcg.psoft.vacinaja.model.Vacina;

public interface LoteService {
	public void criarLote(LoteDTO loteDTO);

	public List<LoteDTO> listarTodosOsLotes();
	
	public boolean existeDoseDisponivel();
	
	public boolean existeDoseDisponivel(Vacina vacina);
	
	public Optional<Lote> findById(long id);
	
	public void reduzQuantidade(long id);

	public List<Vacina> listarTiposVacina();
}