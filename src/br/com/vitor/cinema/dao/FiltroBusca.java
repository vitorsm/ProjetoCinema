package br.com.vitor.cinema.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import br.com.vitor.cinema.dao.mySql.FiltroException;

public interface FiltroBusca {
//	public String getWhere();
//	public void setPreparedStatement(PreparedStatement ps);
//	public void setSQL(String sql);
	public PreparedStatement getPreparedStatement(Connection conn, String sql) throws FiltroException;
}
