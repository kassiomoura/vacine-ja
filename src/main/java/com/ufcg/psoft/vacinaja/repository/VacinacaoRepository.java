package com.ufcg.psoft.vacinaja.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ufcg.psoft.vacinaja.enums.NumeroDoseEnum;
import com.ufcg.psoft.vacinaja.model.Vacinacao;

@Repository
public interface VacinacaoRepository extends JpaRepository<Vacinacao, Long>{
	@Query(value="SELECT v FROM Vacinacao v WHERE v.cidadao.cpf = :#{#cpf} AND v.numeroDose = :#{#numeroDose}")
	public Optional<Vacinacao> findByCidadaoCpfAndNumeroDaDose(@Param("cpf") String cpf, @Param("numeroDose") NumeroDoseEnum numeroDose);
}