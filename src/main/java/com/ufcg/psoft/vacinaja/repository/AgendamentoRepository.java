package com.ufcg.psoft.vacinaja.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ufcg.psoft.vacinaja.enums.NumeroDoseEnum;
import com.ufcg.psoft.vacinaja.model.Agendamento;
import com.ufcg.psoft.vacinaja.model.Cidadao;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long>{
	public Optional<Agendamento> findByData(LocalDateTime data);
	
	public Optional<Agendamento> findByCidadaoAndDose(Cidadao cidadao, NumeroDoseEnum dose);
	
	@Query(value="SELECT a FROM Agendamento a WHERE a.cidadao.cpf = :#{#cpf} AND a.dose = :#{#dose}")
	public Optional<Agendamento> findByCidadaoCpfAndNumeroDaDose(@Param("cpf") String cpf, @Param("dose") NumeroDoseEnum dose);
}