package br.com.vitor.cinema.entidade;

import java.util.ArrayList;

import java.util.Date;
import java.util.List;

import br.com.vitor.cinema.entidade.anotacoes.BDColuna;
import br.com.vitor.cinema.entidade.anotacoes.BDPrimaryKey;
import br.com.vitor.cinema.entidade.anotacoes.BDTabela;

@BDTabela(nomeTabela = "Filme")
public class Filme {

	private int codigo;
	private String nome;
	private Usuario autor;
	private int duracao; // Duração em minutos
	private Date lancamento;
	private List<Lingua> linguasLegenda;
	private List<Lingua> linguasAudio;
	private String sinopse;

	@BDPrimaryKey()
	@BDColuna(consideraNulo = false, nomeCampo = "CodFilme", likeCoringa = false, vazioNulo = true)
	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	@BDColuna(nomeCampo = "NmFilme", vazioNulo = false, consideraNulo = false, likeCoringa = true)
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@BDColuna(nomeCampo = "Autor_CodUsuario", vazioNulo = true, consideraNulo = false, likeCoringa = false)
	public Usuario getAutor() {
		return autor;
	}

	public void setAutor(Usuario autor) {
		this.autor = autor;
	}

	@BDColuna(nomeCampo = "Duracao", vazioNulo = true, consideraNulo = false, likeCoringa = false)
	/***
	 * 
	 * @return: Retorna a duração do filme dm minutos
	 */
	public int getDuracao() {
		return duracao;
	}

	/***
	 * 
	 * @param duracao:
	 *            Duração do filme em minutos
	 */
	public void setDuracao(int duracao) {
		this.duracao = duracao;
	}

	@BDColuna(nomeCampo = "DataLancamento", vazioNulo = true, consideraNulo = false, likeCoringa = false)
	public Date getLancamento() {
		return lancamento;
	}

	public void setLancamento(Date lancamento) {
		this.lancamento = lancamento;
	}

	public List<Lingua> getLinguasLegenda() {
		return linguasLegenda;
	}

	public List<Lingua> getLinguasAudio() {
		return linguasAudio;
	}
	
	public String getSinopse() {
		return sinopse;
	}

	public void setSinopse(String sinopse) {
		this.sinopse = sinopse;
	}

	public void addLinguaAudio(Lingua lingua) {
		if (linguasAudio == null) {
			linguasAudio = new ArrayList<Lingua>();
		}

		if (!linguasAudio.contains(lingua)) {
			linguasAudio.add(lingua);
		}
	}

	public void addLinguaLegenda(Lingua lingua) {
		if (linguasLegenda == null) {
			linguasLegenda = new ArrayList<Lingua>();
		}

		if (!linguasLegenda.contains(lingua)) {
			linguasLegenda.add(lingua);
		}
	}

	@Override
	public String toString() {
		return this.nome;
	}
}
