package br.com.vitor.cinema.dao.mySql;

public class DadoNaoCorrespondenteException extends Exception {
	
	public DadoNaoCorrespondenteException(String msg, Throwable throwable) {
		super(msg, throwable);
	}
}
