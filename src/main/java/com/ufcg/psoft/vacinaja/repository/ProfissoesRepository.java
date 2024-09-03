package com.ufcg.psoft.vacinaja.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ufcg.psoft.vacinaja.enums.ProfissoesEnum;
import com.ufcg.psoft.vacinaja.model.Profissoes;

public interface ProfissoesRepository extends JpaRepository<Profissoes, ProfissoesEnum> {

}
