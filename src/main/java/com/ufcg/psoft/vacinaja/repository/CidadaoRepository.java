package com.ufcg.psoft.vacinaja.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import com.ufcg.psoft.vacinaja.enums.EstagioVacinacaoEnum;
import com.ufcg.psoft.vacinaja.model.Cidadao;

@Repository
public interface CidadaoRepository extends JpaRepository<Cidadao, String>{
	
	@Query(value ="SELECT c FROM Cidadao c JOIN StatusVacinacao s ON c.cpf = s.cpfPessoa WHERE s.estagioVacinacao.estagioVacinacao = :#{#estagio}")
	public List<Cidadao> findAllByEstagioVacinacao(@Param("estagio") EstagioVacinacaoEnum estagio);
	
	public Optional<Cidadao> findByCartaoSUS(String cartaoSUS);
}
