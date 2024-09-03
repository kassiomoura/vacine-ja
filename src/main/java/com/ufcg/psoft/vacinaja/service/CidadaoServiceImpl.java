package com.ufcg.psoft.vacinaja.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.vacinaja.DTO.AgendamentoDTO;
import com.ufcg.psoft.vacinaja.DTO.AlteraCidadaoDTO;
import com.ufcg.psoft.vacinaja.DTO.CidadaoDTO;
import com.ufcg.psoft.vacinaja.model.Cidadao;
import com.ufcg.psoft.vacinaja.model.Comorbidades;
import com.ufcg.psoft.vacinaja.model.PerfilVacinacao;
import com.ufcg.psoft.vacinaja.model.StatusVacinacao;
import com.ufcg.psoft.vacinaja.model.Vacina;
import com.ufcg.psoft.vacinaja.repository.CidadaoRepository;
import com.ufcg.psoft.vacinaja.repository.StatusVacinacaoRepository;
import com.ufcg.psoft.vacinaja.sendmail.SendMail;
import com.ufcg.psoft.vacinaja.util.Util;
import com.ufcg.psoft.vacinaja.enums.ComorbidadesEnum;
import com.ufcg.psoft.vacinaja.enums.EstagioVacinacaoEnum;
import com.ufcg.psoft.vacinaja.enums.ProfissoesEnum;
import com.ufcg.psoft.vacinaja.enums.TipoUsuario;

@Service
public class CidadaoServiceImpl implements CidadaoService {
	
	private static final TipoUsuario USUARIO = TipoUsuario.CIDADAO; 
	
	@Autowired
	private CidadaoRepository cidadaoRepository;
	
	@Autowired
	private StatusVacinacaoRepository statusVacinacaoRepository;

	@Autowired
	private JWTService jwtService;
	
	@Autowired
	private PerfilVacinacaoService perfilVacinacaoService;
	
	@Autowired
	private AgendamentoService agendamentoService;
	
	@Override
	public void cadrastarCidadao(CidadaoDTO cidadaoDTO) {
		verificaDadosDTO(cidadaoDTO);
		verificaCidadaoJaCadastrado(cidadaoDTO);
		StatusVacinacao statusVacinacao = new StatusVacinacao(cidadaoDTO.getCpf());
		this.statusVacinacaoRepository.save(statusVacinacao);
		Cidadao cidadao = geraCidadao(cidadaoDTO);
		cidadaoRepository.save(cidadao);
		cidadao.setStatusVacincao(statusVacinacao);
		cidadaoRepository.save(cidadao);
		
		this.habilitaVacinacaoCidadao(cidadao);
	}

	@Override
	public boolean existeCidadao(String cpf) {
		return cidadaoRepository.findById(cpf).isPresent();
	}

	@Override
	public Cidadao getCidadao(String cpf) {
		if (existeCidadao(cpf)) {
			return cidadaoRepository.findById(cpf).get();
		} else {
			throw new IllegalArgumentException("Cidadão não existente");
		}
	}

	@Override
	public boolean verificaSenha(String cpf, String senha) {
		Cidadao cidadao = getCidadao(cpf);
		return cidadao.getSenha().equals(senha);
	}

	@Override
	public ComorbidadesEnum[] listarComorbidades() {
		return ComorbidadesEnum.values();
	}

	@Override
	public void alterarCadastroCidadao(AlteraCidadaoDTO alteraCidadaoDTO, String token) {
		jwtService.possuiAcesso(token, USUARIO);
		String cpf = jwtService.getUsuarioLogado(token);

		Cidadao cidadao = getCidadao(cpf);
		String atributo = alteraCidadaoDTO.getAtributo().toLowerCase();
		Object novoDado = alteraCidadaoDTO.getNovoDado();
		Util.verificaNull(atributo, "atributo");
		Util.verificaNull(novoDado, "novo dado");
		

		
		try {
			switch (atributo) {
			case ("nome"):
				alteraNomeCidadao(cidadao, novoDado);
				break;
			case ("endereco"):
				alteraEnderecoCidadao(cidadao, novoDado);				
				break;
			case ("senha"):
				alteraSenhaCidadao(cidadao, novoDado);				
				break;
			case ("telefone"):
				alteraTelefoneCidadao(cidadao, novoDado);
				break;
			case ("profissao"):
				alteraProfissaoCidadao(cidadao, novoDado);
				break;
			case ("comorbidade"):
				alteraComorbidadeCidadao(cidadao, novoDado);
				break;
			
			default:
				throw new IllegalArgumentException(String.format("%s não é um atributo alteravel do cidadão", atributo));
			}
		} catch (ClassCastException e) {
			throw new IllegalArgumentException(String.format("O novo dado %s não é do tipo do atributo %s", novoDado,atributo));
		}
		
		cidadaoRepository.save(cidadao);
	}
	
	@Override
	public ProfissoesEnum[] listarProfissoes() {
		return ProfissoesEnum.values();
	}
	
	@Override
	public EstagioVacinacaoEnum getEstagioVacinacao(String token) {
		jwtService.possuiAcesso(token, USUARIO);
		String cpf = this.jwtService.getUsuarioLogado(token);
		return this.cidadaoRepository.findById(cpf).get().getEstagioVacinacao();
	}

	@Override
	public void habilitaVacinacao(PerfilVacinacao perfilVacinacao) {
		List<Cidadao> listaCidadao = cidadaoRepository.findAllByEstagioVacinacao(EstagioVacinacaoEnum.NAO_HABILITADO);
		
		List<String> emails = new ArrayList<>();
		for (Cidadao cidadao : listaCidadao) {
			boolean habilitado = cidadao.habilitaVacinacao(perfilVacinacao);
			if (habilitado) {
				StatusVacinacao status = cidadao.getStatusVacinacao();
				statusVacinacaoRepository.save(status);
				emails.add(cidadao.getEmail());
			}
		}	
		
		if(emails.size() > 0) {
			SendMail.sendTo(emails, SendMail.MSG_HABILITADO_PRIMEIRA_DOSE);			
		}
	}
	
	@Override
	public void vacinaCidadao(String cpf, Vacina vacina, LocalDateTime dataVacinacao) {
		Cidadao cidadao = cidadaoRepository.findById(cpf).get();
		cidadao.vacina(vacina, dataVacinacao);
		StatusVacinacao status = cidadao.getStatusVacinacao();
		statusVacinacaoRepository.save(status);
	}

	@Override
	public String agendar(AgendamentoDTO agendamentoDTO, String token) {
		jwtService.possuiAcesso(token, USUARIO);
		String cpfUsuario = jwtService.getUsuarioLogado(token);
		Cidadao cidadao = this.getCidadao(cpfUsuario);
		return agendamentoService.agendar(agendamentoDTO, cidadao);
	}
	
	private Cidadao geraCidadao(CidadaoDTO cidadaoDTO) {
		ArrayList<Comorbidades> comorbidades = new ArrayList<Comorbidades>();
		for (ComorbidadesEnum comorbidade : cidadaoDTO.getComorbidades()) {
			comorbidades.add(new Comorbidades(comorbidade));
		}
		return new Cidadao(cidadaoDTO.getNome(), cidadaoDTO.getEndereco(), cidadaoDTO.getCpf(),
				cidadaoDTO.getCartaoSUS(), cidadaoDTO.getEmail(), cidadaoDTO.getSenha(),
				cidadaoDTO.getDataDeNascimento(), cidadaoDTO.getTelefone(), cidadaoDTO.getProfissao(),
				comorbidades);
	}
	
	private void alteraNomeCidadao(Cidadao cidadao, Object novoDado) {
		String nome = (String) novoDado;
		verificaNome(nome);
		cidadao.setNome(nome);
	}
	private void alteraEnderecoCidadao(Cidadao cidadao, Object novoDado) {
		String endereco = (String) novoDado;
		verificaEndereco(endereco);
		cidadao.setEndereco(endereco);
	}
	private void alteraSenhaCidadao(Cidadao cidadao, Object novoDado) {
		String senha = (String) novoDado;
		verificaSenha(senha);
		cidadao.setSenha(senha);
	}
	private void alteraTelefoneCidadao(Cidadao cidadao, Object novoDado) {
		String telefone = (String) novoDado;
		verificaTelefone(telefone);
		cidadao.setTelefone(telefone);
	}
	private void alteraProfissaoCidadao(Cidadao cidadao, Object novoDado) {
		ProfissoesEnum profissao = (ProfissoesEnum) novoDado;
		cidadao.setProfissao(profissao);
		this.habilitaVacinacaoCidadao(cidadao);
	}
	private void alteraComorbidadeCidadao(Cidadao cidadao, Object novoDado) {
		ComorbidadesEnum comorbidade = (ComorbidadesEnum) novoDado;
		cidadao.addComorbidade(comorbidade);
		this.habilitaVacinacaoCidadao(cidadao);
	}
	
	private void habilitaVacinacaoCidadao(Cidadao cidadao) {
		
		List<PerfilVacinacao> perfis = perfilVacinacaoService.getPerfisVacinacao();
		
		List<String> emails = new ArrayList<>();
		boolean habilitado = false;
		for (PerfilVacinacao perfilVacinacao : perfis) {
			habilitado = cidadao.habilitaVacinacao(perfilVacinacao);
			if (habilitado) {
				break;
			}
		}
		if (habilitado) {
			StatusVacinacao status = cidadao.getStatusVacinacao();
			statusVacinacaoRepository.save(status);
			emails.add(cidadao.getEmail());
		}
		
		if(emails.size() > 0) {
			SendMail.sendTo(emails, SendMail.MSG_HABILITADO_PRIMEIRA_DOSE);			
		}
	}
	
	private void habilitaVacinacaoCidadao(List<Cidadao> cidadaos) {
		List<PerfilVacinacao> perfis = perfilVacinacaoService.getPerfisVacinacao();
		
		List<String> emails = new ArrayList<>();
		
		for (Cidadao cidadao : cidadaos) {
			boolean habilitado = false;
			for (PerfilVacinacao perfilVacinacao : perfis) {
				habilitado = cidadao.habilitaVacinacao(perfilVacinacao);
				if (habilitado) {
					break;
				}
			}
			if (habilitado) {
				StatusVacinacao status = cidadao.getStatusVacinacao();
				statusVacinacaoRepository.save(status);
				emails.add(cidadao.getEmail());
			}
		}
		
		
		if(emails.size() > 0) {
			SendMail.sendTo(emails, SendMail.MSG_HABILITADO_PRIMEIRA_DOSE);			
		}
	}
		
	private void verificaCidadaoJaCadastrado(CidadaoDTO cidadaoDTO) {
		Optional<Cidadao> optCidadao = this.cidadaoRepository.findById(cidadaoDTO.getCpf());
		if(optCidadao.isPresent()) {
			throw new IllegalArgumentException("O cpf informado já foi cadastrado.");
		}
		optCidadao = this.cidadaoRepository.findByCartaoSUS(cidadaoDTO.getCartaoSUS());
		if(optCidadao.isPresent()) {
			throw new IllegalArgumentException("O cartão do sus informado já foi cadastrado.");
		}
	}
		
	private void verificaDadosDTO(CidadaoDTO cidadaoDTO) {
		
		verificaNome(cidadaoDTO.getNome());
		verificaEndereco(cidadaoDTO.getEndereco());
		verificaCpf(cidadaoDTO.getCpf());
		verificaCartaoSus(cidadaoDTO.getCartaoSUS());
		verificaEmail(cidadaoDTO.getEmail());
		verificaSenha(cidadaoDTO.getSenha());
		verificaTelefone(cidadaoDTO.getTelefone());

		
		
		Util.verificaNull(cidadaoDTO.getDataDeNascimento(), "Data de Nascimento");
		Util.verificaNull(cidadaoDTO.getProfissao(), "Profissão");
		Util.verificaNull(cidadaoDTO.getComorbidades(), "Comorbidades");
	}
	
	private void verificaNome(String nome) {
		Util.verificaNull(nome, "Nome");
		Util.verificaStringVazia(nome, "Nome");
	}
	
	private void verificaEndereco(String endereco) {
		Util.verificaNull(endereco, "Endereço");
		Util.verificaStringVazia(endereco, "Endereço");
	}
	
	private void verificaCpf(String cpf) {
		Util.verificaNull(cpf, "CPF");
		Util.verificaFormatoCPF(cpf);
	}
	
	private void verificaCartaoSus(String cartaoSus) {
		Util.verificaNull(cartaoSus, "Cartao do SUS");
		Util.verificaFormatoCartaoSus(cartaoSus);
	}
	
	private void verificaEmail(String email) {
		Util.verificaNull(email, "Email");
		Util.verificaFormatoEmail(email);
	}
	
	private void verificaSenha(String senha) {
		Util.verificaNull(senha, "Senha");
		Util.verificaStringVazia(senha, "Senha");
		Util.verificaFormatoSenha(senha);
	}

	private void verificaTelefone(String telefone) {
		Util.verificaNull(telefone, "Telefone");
		Util.verificaFormatoTelefone(telefone);
	}
	
	@Scheduled(fixedDelay = 10000)
	private void habilitaSegundaDose() {
		List<Cidadao> cidadaos = cidadaoRepository.findAllByEstagioVacinacao(EstagioVacinacaoEnum.ESPERANDO_SEGUNDA_DOSE);
		
		List<String> emails = new ArrayList<>();
		for (Cidadao cidadao : cidadaos) {
			StatusVacinacao statusVacinacao = cidadao.getStatusVacinacao();
			int diasNecessarios = statusVacinacao.getVacina().getQntDiasEntreDoses();
			
			LocalDateTime dataPrimeiraDose = statusVacinacao.getDataPrimeiraDose();
			LocalDateTime dataAtual = LocalDateTime.now();
			
			if (dataPrimeiraDose.plusDays(diasNecessarios).isBefore(dataAtual)) {
				statusVacinacao.habilitaVacinacao(cidadao);
				statusVacinacaoRepository.save(statusVacinacao);
				emails.add(cidadao.getEmail());
			}			
		}
		
		if(emails.size() > 0) {
			SendMail.sendTo(emails, SendMail.MSG_HABILITADO_SEGUNDA_DOSE);
		}
		
	}
	
	@Scheduled(fixedDelay = 86400000)
	private void habilitaPrimeiraDose() {
		List<Cidadao> cidadaos = cidadaoRepository.findAllByEstagioVacinacao(EstagioVacinacaoEnum.NAO_HABILITADO);
		
		List<Cidadao> cidadaosparaVerificarHabilitacao = new ArrayList<>();
		for (Cidadao cidadao : cidadaos) {
			
			LocalDate dataNascimento = cidadao.getDataDeNascimento();
			int mesNascimento = dataNascimento.getMonthValue();
			int diaNascimento = dataNascimento.getDayOfMonth();
			
			LocalDate dataAtual = LocalDate.now();
			int mesAtual = dataAtual.getMonthValue();
			int diaAtual = dataAtual.getDayOfMonth();
			
			
			if (mesAtual == mesNascimento && diaAtual == diaNascimento) {
				cidadaosparaVerificarHabilitacao.add(cidadao);
			}			
		}
		
		if(cidadaosparaVerificarHabilitacao.size() > 0) {
			habilitaVacinacaoCidadao(cidadaos);
		}
		
	}


	
}