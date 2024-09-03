package com.ufcg.psoft.vacinaja.service;

import java.util.List;

import com.ufcg.psoft.vacinaja.DTO.PerfilVacinacaoDTO;
import com.ufcg.psoft.vacinaja.model.PerfilVacinacao;

public interface PerfilVacinacaoService {

	public PerfilVacinacao criaPerfil(PerfilVacinacaoDTO perfilVacinacao);
	
	public List<PerfilVacinacao> getPerfisVacinacao();
	
}