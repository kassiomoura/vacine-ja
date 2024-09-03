package com.ufcg.psoft.vacinaja.service;

import java.time.LocalDateTime;

import com.ufcg.psoft.vacinaja.DTO.AgendamentoDTO;
import com.ufcg.psoft.vacinaja.DTO.AlteraCidadaoDTO;
import com.ufcg.psoft.vacinaja.DTO.CidadaoDTO;
import com.ufcg.psoft.vacinaja.enums.ComorbidadesEnum;
import com.ufcg.psoft.vacinaja.enums.EstagioVacinacaoEnum;
import com.ufcg.psoft.vacinaja.enums.ProfissoesEnum;
import com.ufcg.psoft.vacinaja.model.Cidadao;
import com.ufcg.psoft.vacinaja.model.PerfilVacinacao;
import com.ufcg.psoft.vacinaja.model.Vacina;

public interface CidadaoService {
	
	public void cadrastarCidadao(CidadaoDTO cidadaoDTO);	
	
	public void alterarCadastroCidadao(AlteraCidadaoDTO alteraCidadaoDTO, String token);
	
	public boolean existeCidadao(String cpf);
	
	public Cidadao getCidadao(String cpf);
	
	public boolean verificaSenha(String cpf, String senha);

	public ComorbidadesEnum[] listarComorbidades();

	public ProfissoesEnum[] listarProfissoes();
	
	public EstagioVacinacaoEnum getEstagioVacinacao(String token);
	
	public void habilitaVacinacao(PerfilVacinacao perfilVacinacao);
	
	public String agendar(AgendamentoDTO agendamentoDTO, String token);

	public void vacinaCidadao(String cpf, Vacina vacina, LocalDateTime dataVacinacao);	
	
}
