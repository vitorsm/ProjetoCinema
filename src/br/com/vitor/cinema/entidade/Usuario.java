package br.com.vitor.cinema.entidade;

import br.com.vitor.cinema.entidade.anotacoes.BDColuna;
import br.com.vitor.cinema.entidade.anotacoes.BDPrimaryKey;

public class Usuario {
	private int codigo;
	private String nome;
	private String userName;
	private String senha;

	@BDPrimaryKey()
	@BDColuna(nomeCampo = "CodUsuario", consideraNulo = false, likeCoringa = false, vazioNulo = true)
	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	@BDColuna(nomeCampo = "NmUsuario", consideraNulo = false, likeCoringa = true, vazioNulo = false)
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@BDColuna(nomeCampo = "UserName", consideraNulo = false, likeCoringa = false, vazioNulo = true)
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@BDColuna(nomeCampo = "Senha", consideraNulo = false, likeCoringa = false, vazioNulo = true)
	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

}
