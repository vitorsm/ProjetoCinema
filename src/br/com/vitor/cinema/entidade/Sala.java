package br.com.vitor.cinema.entidade;

import java.util.ArrayList;
import java.util.List;

import br.com.vitor.cinema.entidade.anotacoes.BDColuna;
import br.com.vitor.cinema.entidade.anotacoes.BDPrimaryKey;

public class Sala {
	private int codigo;
	private int qtdFilas;
	private int qtdColunas;
	private Usuario autor;
	private List<Sessao> sessoes;

	@BDPrimaryKey
	@BDColuna(nomeCampo = "CodSala", consideraNulo = false, likeCoringa = false, vazioNulo = true)
	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	@BDColuna(nomeCampo = "QtdFilas", consideraNulo = false, likeCoringa = false, vazioNulo = true)
	public int getQtdFilas() {
		return qtdFilas;
	}

	public void setQtdFilas(int qtdFilas) {
		this.qtdFilas = qtdFilas;
	}

	@BDColuna(nomeCampo = "QtdColunas", consideraNulo = false, likeCoringa = false, vazioNulo = true)
	public int getQtdColunas() {
		return qtdColunas;
	}

	public void setQtdColunas(int qtdColunas) {
		this.qtdColunas = qtdColunas;
	}
	
	@BDColuna(nomeCampo = "Autor_CodUsuario", consideraNulo = false, likeCoringa = false, vazioNulo = true)
	public Usuario getAutor() {
		return autor;
	}

	public void setAutor(Usuario autor) {
		this.autor = autor;
	}

	public List<Sessao> getSessoes() {
		return this.sessoes;
	}

	public void addSessao(Sessao sessao) {
		if (sessoes == null) {
			sessoes = new ArrayList<Sessao>();
		}

		if (!sessoes.contains(sessao)) {
			sessoes.add(sessao);
		}
	}
}
