package com.ufcg.psoft.vacinaja.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ufcg.psoft.vacinaja.model.Lote;
import com.ufcg.psoft.vacinaja.model.Vacina;


public interface LoteRepository extends JpaRepository<Lote, Long> {
	public List<Lote> findAllByVacina(Vacina vacina);
}