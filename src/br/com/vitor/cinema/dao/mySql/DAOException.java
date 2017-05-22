package br.com.vitor.cinema.dao.mySql;

public class DAOException extends Exception {
	//criar getConnection
	//criar os closeConnection's
	public DAOException(String msg) {
		super(msg);
	}
	
	public DAOException(Throwable throwable) {
		super(throwable);
	}
	
	public DAOException(String msg, Throwable throwable) {
		super(msg, throwable);
	}
}
