package com.ufcg.psoft.vacinaja.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ufcg.psoft.vacinaja.enums.ComorbidadesEnum;
import com.ufcg.psoft.vacinaja.model.Comorbidades;

public interface ComorbidadesRepository extends JpaRepository<Comorbidades, ComorbidadesEnum>{
	

}
