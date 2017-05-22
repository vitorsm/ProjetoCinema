package br.com.vitor.cinema.dao;

import java.sql.DriverManager;
import java.sql.SQLException;

import br.com.vitor.cinema.util.ConfigApp;
import br.com.vitor.cinema.util.ConfigAppException;

import java.sql.Connection;

public abstract class DAO {
	
	protected final Connection getConnection() throws DAOException {
		Connection conn = null;
		
		try {
			ConfigApp configApp = ConfigApp.instance();
			
			Class.forName("com.mysql.jdbc.Driver");
			
			String nomeServer = configApp.getPropertie("nomeServer");
			String nomeBd = configApp.getPropertie("nomeBd");
			String nomeUsuario = configApp.getPropertie("nomeUsuario");
			String senha = "";
			
			String url = "jdbc:mysql://" + 	nomeServer + "/" + nomeBd;
			conn = DriverManager.getConnection(url, nomeUsuario, senha);
		} catch (ClassNotFoundException e) {
			throw new DAOException("Erro ao acessar BD, driver não encontrado. " + e.getMessage() + " - " + e.getClass().getName(), e);
		} catch (SQLException e) {
			throw new DAOException("Erro ao acessar BD, driver não encontrado. " + e.getMessage() + " - " + e.getClass().getName(), e);
		} catch (ConfigAppException e) {
			throw new DAOException("Erro ao ler arquivo de configurações. " + e.getMessage() + " - " + e.getClass().getName(), e);
		}
		
		return conn;
	}
	
	protected final void close(Connection conn) {
		try {
			conn.close();
		} catch (SQLException e) {
		}
	}
}
