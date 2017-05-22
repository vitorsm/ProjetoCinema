package br.com.vitor.cinema.entidade;

public class Lingua {
	private int 	codigo;
	private String 	nome;
	
	
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
	
	@Override
	public boolean equals(Object objeto) {
		if (objeto instanceof Lingua) {
			return this.codigo == ((Lingua) objeto).codigo;
		}
		
		return false;
	}
	
	@Override
	public String toString() {
		return this.nome;
	}
}
