package br.com.vitor.cinema.entidade;

import java.util.Date;
import java.util.List;

public class Sessao {
	private Filme 			filme;
	private Date 			data;
	private List<Ingresso> 	ingressos;
	
	public Filme getFilme() {
		return filme;
	}
	public void setFilme(Filme filme) {
		this.filme = filme;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	
	@Override
	public boolean equals(Object objeto) {
		if (objeto instanceof Sessao) {
			Sessao sessao = (Sessao) objeto;
			
			return sessao.data == this.data && sessao.filme.equals(this.filme);
		}
		
		return false;
	}
}
