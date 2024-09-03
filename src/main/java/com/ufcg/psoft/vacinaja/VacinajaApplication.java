package com.ufcg.psoft.vacinaja;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import com.ufcg.psoft.vacinaja.Filter.TokenFilter;

@SpringBootApplication
public class VacinajaApplication {
	
	@Bean
	public FilterRegistrationBean<TokenFilter> filterJwt() {
		FilterRegistrationBean<TokenFilter> filterRB = new FilterRegistrationBean<TokenFilter>();
		filterRB.setFilter(new TokenFilter());
		filterRB.addUrlPatterns("/api/admin/vacina",
								"/api/admin/funcionario",
								"/api/cidadao/alterar",
								"/api/cidadao/vacinacao/status",
								"/api/cidadao/vacinacao/agendar",								
								"/api/funcionario/vacinacao/perfil",
								"/api/funcionario/vacinacao/registra",
								"/api/funcionario/vacina",
								"/api/funcionario/vacina/lote");
		
		return filterRB;
	}

	public static void main(String[] args) {
		SpringApplication.run(VacinajaApplication.class, args);
	}

}
