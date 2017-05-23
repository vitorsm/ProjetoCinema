package br.com.vitor.cinema.entidade.anotacoes;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface BDTabela {
	String nomeTabela();
}
