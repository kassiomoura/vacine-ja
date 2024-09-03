package com.ufcg.psoft.vacinaja.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.vacinaja.DTO.PerfilVacinacaoDTO;
import com.ufcg.psoft.vacinaja.enums.ComorbidadesEnum;
import com.ufcg.psoft.vacinaja.enums.ProfissoesEnum;
import com.ufcg.psoft.vacinaja.model.PerfilVacinacao;
import com.ufcg.psoft.vacinaja.repository.PerfilVacinacaoRepository;

@Service
public class PerfilVacinacaoServiceImpl implements PerfilVacinacaoService {

	@Autowired
	private PerfilVacinacaoRepository perfilVacinacaoRepository;

	@Override
	public PerfilVacinacao criaPerfil(PerfilVacinacaoDTO perfilDTO) {
		verificaIdade(perfilDTO.getIdade());
		verificaProfissao(perfilDTO.getProfissao());
		verificaComorbidade(perfilDTO.getComorbidade());
		
		PerfilVacinacao perfil = new PerfilVacinacao(perfilDTO.getIdade(), perfilDTO.getProfissao(),
				perfilDTO.getComorbidade());
		
		perfilVacinacaoRepository.save(perfil);
		
		return perfil;

	}
	
	@Override
	public List<PerfilVacinacao> getPerfisVacinacao() {
		return perfilVacinacaoRepository.findAll();
	}

	private void verificaIdade(int idade) {
		if (idade <= 0) {
			throw new IllegalArgumentException("Idade Invalida");
		}
	}

	private void verificaProfissao(ProfissoesEnum profissao) {
		if (profissao == null) {
			throw new NullPointerException("Profissao Invalida");
		}
	}

	private void verificaComorbidade(ComorbidadesEnum comorbidade) {
		if (comorbidade == null) {
			throw new NullPointerException("Comorbidade Invalida");
		}
	}
}