package br.com.vitor.cinema.dao;

import java.util.List;

public interface EntidadeDAO<T> {
	public int inserir(T t);
	public void editar(T t);
	public void deletar(int id);
	public T consultarId(int id);
}
