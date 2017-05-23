package br.com.vitor.cinema.entidade;

import br.com.vitor.cinema.entidade.anotacoes.BDColuna;
import br.com.vitor.cinema.entidade.anotacoes.BDPrimaryKey;

public class Lingua {
	private int 	codigo;
	private String 	nome;
	
	
	@BDPrimaryKey
	@BDColuna(nomeCampo="CodLingua", consideraNulo=false, likeCoringa=false, vazioNulo=true)
	public int getCodigo() {
		return codigo;
	}
	
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	
	@BDColuna(nomeCampo="NmLingua", consideraNulo=false, likeCoringa=true, vazioNulo=false)
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
