package com.ufcg.psoft.vacinaja.config;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.ufcg.psoft.vacinaja.enums.ComorbidadesEnum;
import com.ufcg.psoft.vacinaja.enums.ProfissoesEnum;
import com.ufcg.psoft.vacinaja.model.Cidadao;
import com.ufcg.psoft.vacinaja.model.Comorbidades;
import com.ufcg.psoft.vacinaja.model.Funcionario;
import com.ufcg.psoft.vacinaja.model.Profissoes;
import com.ufcg.psoft.vacinaja.model.estagiovacinacao.EsperandoSegundaDose;
import com.ufcg.psoft.vacinaja.model.estagiovacinacao.HabilitadoPrimeiraDose;
import com.ufcg.psoft.vacinaja.model.estagiovacinacao.HabilitadoSegundaDose;
import com.ufcg.psoft.vacinaja.model.estagiovacinacao.NaoHabilitado;
import com.ufcg.psoft.vacinaja.model.estagiovacinacao.VacinacaoFinalizada;
import com.ufcg.psoft.vacinaja.repository.CidadaoRepository;
import com.ufcg.psoft.vacinaja.repository.ComorbidadesRepository;
import com.ufcg.psoft.vacinaja.repository.EstagioVacinacaoRepository;
import com.ufcg.psoft.vacinaja.repository.FuncionarioRepository;
import com.ufcg.psoft.vacinaja.repository.ProfissoesRepository;

@Configuration
@Profile("prod")
public class DatabaseConfig implements CommandLineRunner{
	
	@Autowired
	private ComorbidadesRepository comorbidadesRepository;
	
	@Autowired
	private ProfissoesRepository profissoesRepository;
	
	@Autowired
	private EstagioVacinacaoRepository estagioVacinacaoRepository;
	
	@Autowired
	private CidadaoRepository cidadaoRepository;
	
	@Autowired
	private FuncionarioRepository funcionarioRepository;
	
	

	@Override
	public void run(String... args) throws Exception {
		
		
		for (ComorbidadesEnum c : ComorbidadesEnum.values()) {
			comorbidadesRepository.save(new Comorbidades(c));
		}
		
		for (ProfissoesEnum p : ProfissoesEnum.values()) {
			profissoesRepository.save(new Profissoes(p));
		}
		
		estagioVacinacaoRepository.save(new NaoHabilitado());
		estagioVacinacaoRepository.save(new HabilitadoPrimeiraDose());
		estagioVacinacaoRepository.save(new EsperandoSegundaDose());
		estagioVacinacaoRepository.save(new HabilitadoSegundaDose());
		estagioVacinacaoRepository.save(new VacinacaoFinalizada());
		
		Cidadao c = new Cidadao("Admin", "enderecoAdmin", "11111111111",
				"111111111111111", "vacine.jagrupo9@gmail.com", "minhasenha",
			LocalDate.now(), "11111111111", ProfissoesEnum.MEDICO, new ArrayList<Comorbidades>());
		
		cidadaoRepository.save(c);
		
		Funcionario f = new Funcionario("11111111111", "Admin", "LocaltrabalhoAdmin",
				"minhasenha", c);
		f.setIsAdministrador(true);
		funcionarioRepository.save(f);
	}
	
}
