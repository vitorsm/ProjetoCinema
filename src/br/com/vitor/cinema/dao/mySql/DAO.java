package br.com.vitor.cinema.dao.mySql;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.vitor.cinema.util.ConfigApp;
import br.com.vitor.cinema.util.ConfigAppException;

import java.sql.Connection;

public abstract class DAO {
	public final static String NULL = "NULL";
	
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
			throw new DAOException("Erro ao obter conexão com BD, driver não encontrado. " + e.getMessage() + " - " + e.getClass().getName(), e);
		} catch (SQLException e) {
			throw new DAOException("Erro ao obter conexão com BD: " + e.getMessage() + " - " + e.getClass().getName(), e);
		} catch (ConfigAppException e) {
			throw new DAOException("Erro ao ler arquivo de configurações. " + e.getMessage() + " - " + e.getClass().getName(), e);
		}
		
		return conn;
	}
	
	protected final void close(Connection conn, PreparedStatement ps, ResultSet rs) {
		try {
			if (conn != null)
				conn.close();
			
			if (ps != null)
				ps.close();
			
			if (rs != null)
				rs.close();
			
		} catch (SQLException e) {
		}
	}
	
//	public static void main(String[] args) {
//		DAO dao = new DAO();
//		try {
//			dao.getConnection();
//			System.out.println("conectou");
//		} catch (DAOException e) {
//			e.printStackTrace();
//		}
//	}
}
