package br.com.vitor.cinema.entidade;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Filme {
	private int 	codigo;
	private String 	nome;
	private Usuario autor;
	private int 	duracao; // Duração em minutos
	private Date 	lancamento;
	private List<Lingua> linguasLegenda;
	private List<Lingua> linguasAudio;
	
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Usuario getAutor() {
		return autor;
	}
	public void setAutor(Usuario autor) {
		this.autor = autor;
	}
	/***
	 * 
	 * @return: Retorna a duração do filme dm minutos
	 */
	public int getDuracao() {
		return duracao;
	}
	/***
	 * 
	 * @param duracao: Duração do filme em minutos
	 */
	public void setDuracao(int duracao) {
		this.duracao = duracao;
	}
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
}
