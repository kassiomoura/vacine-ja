package com.ufcg.psoft.vacinaja.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ufcg.psoft.vacinaja.enums.EstagioVacinacaoEnum;
import com.ufcg.psoft.vacinaja.model.estagiovacinacao.EstagioVacinacao;

public interface EstagioVacinacaoRepository extends JpaRepository<EstagioVacinacao, EstagioVacinacaoEnum> {

}
