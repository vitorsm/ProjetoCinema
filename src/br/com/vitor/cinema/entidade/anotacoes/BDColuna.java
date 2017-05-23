package br.com.vitor.cinema.entidade.anotacoes;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface BDColuna {
	String nomeCampo();
	boolean vazioNulo();
	boolean consideraNulo();
	boolean likeCoringa();
}