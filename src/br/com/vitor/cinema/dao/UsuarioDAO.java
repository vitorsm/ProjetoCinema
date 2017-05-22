package br.com.vitor.cinema.dao;

import br.com.vitor.cinema.entidade.Usuario;

public interface UsuarioDAO extends EntidadeDAO<Usuario> {
	
	public Usuario autenticaUsuario(String userName, String senha);
}
