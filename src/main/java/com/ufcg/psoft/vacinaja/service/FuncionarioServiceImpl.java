package com.ufcg.psoft.vacinaja.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.vacinaja.DTO.FuncionarioDTO;
import com.ufcg.psoft.vacinaja.DTO.FuncionarioPendenteDTO;
import com.ufcg.psoft.vacinaja.DTO.LoteDTO;
import com.ufcg.psoft.vacinaja.DTO.PerfilVacinacaoDTO;
import com.ufcg.psoft.vacinaja.DTO.VacinacaoDTO;
import com.ufcg.psoft.vacinaja.enums.TipoUsuario;
import com.ufcg.psoft.vacinaja.DTO.FuncionarioCpfDTO;
import com.ufcg.psoft.vacinaja.model.Cidadao;
import com.ufcg.psoft.vacinaja.model.Funcionario;
import com.ufcg.psoft.vacinaja.model.FuncionarioPendente;
import com.ufcg.psoft.vacinaja.model.PerfilVacinacao;
import com.ufcg.psoft.vacinaja.model.Vacina;
import com.ufcg.psoft.vacinaja.repository.FuncionarioPendenteRepository;
import com.ufcg.psoft.vacinaja.repository.FuncionarioRepository;
import com.ufcg.psoft.vacinaja.util.Util;

@Service
public class FuncionarioServiceImpl implements FuncionarioService {
	
	private static final TipoUsuario USUARIO = TipoUsuario.FUNCIONARIO;
	
	@Autowired
	private JWTService jwtService;
	
	@Autowired
	private FuncionarioRepository funcionarioRepository;
	
	@Autowired
	private FuncionarioPendenteRepository funcionarioPendenteRepository;
	
	@Autowired
	private CidadaoService cidadaoService;
	
	@Autowired
	private VacinacaoService vacinacaoService;
	
	@Autowired
	private LoteService loteService;
	
	@Autowired
	private PerfilVacinacaoService perfilVacinacaoService;

	@Override
	public void solicitaCadastroFuncionario(FuncionarioDTO funcionarioDTO) {
		verificaDadosDTO(funcionarioDTO);
		verificaCadastro(funcionarioDTO.getCpf());
		Cidadao cidadao = cidadaoService.getCidadao(funcionarioDTO.getCpf());
		FuncionarioPendente funcionarioPendente = geraFuncionario(funcionarioDTO, cidadao);
		funcionarioPendenteRepository.save(funcionarioPendente);
	}
	
	@Override
	public void aprovaCadastroFuncionario(FuncionarioCpfDTO funcionarioCpfDTO) {
		verificaFuncionarioCpfDTO(funcionarioCpfDTO.getCpf());
			
		if (!existeFuncionarioPendente(funcionarioCpfDTO.getCpf())) {
			throw new IllegalArgumentException("Não existe solicitação de cadastro para este CPF!");	
		}
		
		FuncionarioPendente funcionarioPendente = getFuncionarioPendente(funcionarioCpfDTO.getCpf());
		
		if (funcionarioCpfDTO.getStatusAprova()) {	
			Funcionario funcionario = new Funcionario(funcionarioPendente.getCpf(), funcionarioPendente.getCargo(), 
					funcionarioPendente.getLocalTrabalho(), 
					funcionarioPendente.getSenha(), funcionarioPendente.getCidadao());
		
			funcionarioRepository.save(funcionario);
			funcionarioPendenteRepository.delete(funcionarioPendente);					
		} else {
			funcionarioPendenteRepository.delete(funcionarioPendente);
		}
	}
	
	@Override		
	public boolean existeFuncionario(String cpf) {
		return funcionarioRepository.findById(cpf).isPresent();
	}
	
	@Override
	public boolean verificaSenha(String cpf, String senha) {
		Funcionario funcionario = funcionarioRepository.findById(cpf).get();
		return funcionario.getSenha().equals(senha);
	}
	
	@Override
	public boolean isAdministrador(String cpf) {
		return funcionarioRepository.findById(cpf).get().isAdministrador();
	}
	
	@Override
	public List<FuncionarioPendenteDTO> listaFuncionariosPendentes() {
		List<FuncionarioPendente> listaFuncionariosPendentes = funcionarioPendenteRepository.findAll();
		
		List<FuncionarioPendenteDTO> listaFuncionariosPendentesDTO = new ArrayList<>();
		
		for (FuncionarioPendente funcionarioPendente : listaFuncionariosPendentes) {
			FuncionarioPendenteDTO funcionarioPendenteDTO = new FuncionarioPendenteDTO(funcionarioPendente.getCpf(), funcionarioPendente.getCargo(), 
						                                          funcionarioPendente.getLocalTrabalho());
			listaFuncionariosPendentesDTO.add(funcionarioPendenteDTO);
		}
		return listaFuncionariosPendentesDTO;
	}

	@Override
	public void registraVacinacao(VacinacaoDTO vacinacaoDTO, String token) {
		this.jwtService.possuiAcesso(token, USUARIO);
		this.vacinacaoService.registraVacinacao(vacinacaoDTO);
	}

	@Override
	public void criarLote(LoteDTO loteDTO, String token) {
		jwtService.possuiAcesso(token, TipoUsuario.FUNCIONARIO);
		loteService.criarLote(loteDTO);
		
	}

	@Override
	public List<LoteDTO> listarTodosOsLotes(String token) {
		this.jwtService.possuiAcesso(token, USUARIO);
		return this.loteService.listarTodosOsLotes();
	}

	@Override
	public void criaPerfilVacinacao(PerfilVacinacaoDTO perfilDTO, String token) {
		this.jwtService.possuiAcesso(token, USUARIO);
		
		PerfilVacinacao perfilVacinacao = this.perfilVacinacaoService.criaPerfil(perfilDTO);
		this.cidadaoService.habilitaVacinacao(perfilVacinacao);	
		
		
	}

	@Override
	public List<Vacina> listaTiposVacinas(String token) {
		this.jwtService.possuiAcesso(token, USUARIO);
		return this.loteService.listarTiposVacina();
	}
	
	
	private void verificaCadastro(String cpf) {
		if(existeFuncionario(cpf)) {
			throw new IllegalArgumentException("Já existe um funcionario cadastrado com o cpf informado.");
		} else if(existeFuncionarioPendente(cpf)) {
			throw new IllegalArgumentException("Já existe uma solicitação de cadastro pendente para o cpf informado.");
		}
		
	}
	
	private FuncionarioPendente geraFuncionario(FuncionarioDTO funcionarioDTO, Cidadao cidadao) {
		return new FuncionarioPendente(funcionarioDTO.getCpf(), funcionarioDTO.getCargo(), funcionarioDTO.getLocalTrabalho(), 
						       funcionarioDTO.getSenha(), cidadao);
	}
	
	private FuncionarioPendente getFuncionarioPendente(String cpf) {
		return funcionarioPendenteRepository.findById(cpf).get();
	}
	
	private void verificaFuncionarioCpfDTO(String cpf) {
		if (cpf == null) {
			throw new NullPointerException("CPF inválido!");
		}
	}
	
	private boolean existeFuncionarioPendente(String cpf) {
		return funcionarioPendenteRepository.findById(cpf).isPresent();
	}
	
	private void verificaDadosDTO(FuncionarioDTO funcionarioDTO) {
		verificaCpf(funcionarioDTO.getCpf());
		verificaCargo(funcionarioDTO.getCargo());
		verificaLocalTrabalho(funcionarioDTO.getLocalTrabalho());
		verificaSenha(funcionarioDTO.getSenha());
	}
	
	private void verificaSenha(String senha) {
		Util.verificaNull(senha, "Senha");
		Util.verificaFormatoSenha(senha);
	}

	private void verificaLocalTrabalho(String localTrabalho) {
		Util.verificaNull(localTrabalho, "Local de Trabalho");
		Util.verificaStringVazia(localTrabalho, "Local de trabalho");
	}

	private void verificaCargo(String cargo) {
		Util.verificaNull(cargo, "Cargo");
		Util.verificaStringVazia(cargo, "Cargo");
	}

	private void verificaCpf(String cpf) {
		Util.verificaNull(cpf, "Cpf");
		Util.verificaFormatoCPF(cpf);
	}


}