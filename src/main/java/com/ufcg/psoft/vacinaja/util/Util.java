package com.ufcg.psoft.vacinaja.util;

import java.time.LocalDateTime;

public class Util {
	
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	private static final String CPF_PATTERN = "^[0-9]{11}$";
	
	private static final String CARTAO_SUS_PATTERN = "^[0-9]{15}$";
	
	private static final String TELEFONE_PATTERN = "^[0-9]{11}$";
	
	private static final String SENHA_PATTERN = "^.{8,}$";
	
	public static final int MINUTOS_ENTRE_AGENDAMENTOS = 10;
	
	public static LocalDateTime arredondaData(LocalDateTime data) {
		int minutos = data.getMinute() % MINUTOS_ENTRE_AGENDAMENTOS;
		int segundos = data.getSecond();
		int nanoSegundos = data.getNano();
		
		return data.minusMinutes(minutos).minusSeconds(segundos).minusNanos(nanoSegundos);
	}
	
	public static void verificaNull(Object objeto, String nomeObjeto) {
		if(objeto == null) {
			throw new NullPointerException(String.format("O atributo %s não pode ser nulo.", nomeObjeto));
		}
	}
	
	public static void verificaStringVazia(String string, String nomeAtributo) {
		if(string.trim().equals("")) {
			throw new IllegalArgumentException(String.format("%s não pode vazio.", nomeAtributo));
		}
	}
	
	public static void verificaFormatoEmail(String email) {
		if(!email.matches(EMAIL_PATTERN)) {
			throw new IllegalArgumentException(String.format("%s não é um e-mail válido.", email));
		}
	}
	
	public static void verificaFormatoCPF(String cpf) {
		if(!cpf.matches(CPF_PATTERN)) {
			throw new IllegalArgumentException(String.format("%s não é um CPF válido, informe apenas os 11 dígitos do CPF.", cpf));
		}
	}
	
	public static void verificaFormatoCartaoSus(String cartaoSus) {
		if(!cartaoSus.matches(CARTAO_SUS_PATTERN)) {
			throw new IllegalArgumentException(String.format("%s não é um número do cartão do sus válido, informe apenas os 15 dígitos do cartão.", cartaoSus));
		}
	}
	
	public static void verificaFormatoSenha(String senha) {
		if(!senha.matches(SENHA_PATTERN)) {
			throw new IllegalArgumentException("A senha informada não é válida, informe uma senha com pelo menos 8 dígitos.");
		}
	}
	
	public static void verificaFormatoTelefone(String telefone) {
		if(!telefone.matches(TELEFONE_PATTERN)) {
			throw new IllegalArgumentException(String.format("%s não é um número de telefone válido, informe apenas os 11 dígitos do número.", telefone));
		}
	}
	

}