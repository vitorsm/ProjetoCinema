package br.com.vitor.cinema.dao;

import java.sql.ResultSet;
import java.util.List;

import br.com.vitor.cinema.dao.mySql.DAOException;

public interface EntidadeDAO<T> {
	public int inserir(T t) throws DAOException;
	public void editar(T t) throws DAOException;
	public void deletar(int id) throws DAOException;
	public List<T> consultar(FiltroBusca filtro) throws DAOException;
	
	public T instance(ResultSet rs) throws DAOException;
}