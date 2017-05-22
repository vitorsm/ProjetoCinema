package br.com.vitor.cinema.entidade;

import java.util.Date;

public class Ingresso {
	private int fila;
	private int coluna;
	private Date dataCompra;
	
	
	public int getFila() {
		return fila;
	}
	public void setFila(int fila) {
		this.fila = fila;
	}
	public int getColuna() {
		return coluna;
	}
	public void setColuna(int coluna) {
		this.coluna = coluna;
	}
	public Date getDataCompra() {
		return dataCompra;
	}
	public void setDataCompra(Date dataCompra) {
		this.dataCompra = dataCompra;
	}
	
	@Override
	public boolean equals(Object objeto) {
		if (objeto instanceof Ingresso) {
			Ingresso ingresso = (Ingresso) objeto;
			
			return this.fila == ingresso.fila && this.coluna == ingresso.coluna;
		}
		
		return false;
	}
}
