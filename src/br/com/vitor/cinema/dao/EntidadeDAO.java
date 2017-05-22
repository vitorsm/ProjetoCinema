package br.com.vitor.cinema.dao;

import java.util.List;

public interface EntidadeDAO<T> {
	public void cadastraEntidade(T t);
	public void editaEntidade(T t);
	public void deletaEntidade(T t);
	public List<T> getEntidades();
}
