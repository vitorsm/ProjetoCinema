package br.com.vitor.cinema.util;

public class ConfigAppException extends Exception {
	public ConfigAppException() {
		super();
	}
	
	public ConfigAppException(String msg) {
		super(msg);
	}
	
	public ConfigAppException(Throwable t) {
		super(t);
	}
	
	public ConfigAppException(String msg, Throwable t) {
		super(msg, t);
	}
}
