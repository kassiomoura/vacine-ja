package com.ufcg.psoft.vacinaja.service;

import com.ufcg.psoft.vacinaja.DTO.FuncionarioDTO;
import com.ufcg.psoft.vacinaja.DTO.FuncionarioPendenteDTO;
import com.ufcg.psoft.vacinaja.DTO.LoteDTO;
import com.ufcg.psoft.vacinaja.DTO.PerfilVacinacaoDTO;
import com.ufcg.psoft.vacinaja.DTO.VacinacaoDTO;
import com.ufcg.psoft.vacinaja.model.Vacina;
import com.ufcg.psoft.vacinaja.DTO.FuncionarioCpfDTO;

import java.util.List;

public interface FuncionarioService {
	
	public void solicitaCadastroFuncionario(FuncionarioDTO funcionarioDTO);
	
	public void aprovaCadastroFuncionario(FuncionarioCpfDTO funcionarioCpfDTO);
	
	public List<FuncionarioPendenteDTO> listaFuncionariosPendentes();
	
	public boolean existeFuncionario(String cpf);

	public boolean verificaSenha(String cpf, String senha);

	public boolean isAdministrador(String cpf);
	
	public void registraVacinacao(VacinacaoDTO vacinacaoDTO, String token);

	public void criarLote(LoteDTO loteDTO, String token);

	public List<LoteDTO> listarTodosOsLotes(String token);

	public void criaPerfilVacinacao(PerfilVacinacaoDTO perfilDTO, String token);

	public List<Vacina> listaTiposVacinas(String token);
}