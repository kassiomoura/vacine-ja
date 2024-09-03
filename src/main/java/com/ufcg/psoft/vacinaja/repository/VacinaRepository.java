package com.ufcg.psoft.vacinaja.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ufcg.psoft.vacinaja.model.Vacina;

@Repository
public interface VacinaRepository extends JpaRepository<Vacina, String> {
}