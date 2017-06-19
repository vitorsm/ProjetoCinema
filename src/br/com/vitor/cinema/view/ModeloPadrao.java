package br.com.vitor.cinema.view;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public abstract class ModeloPadrao<T> extends AbstractTableModel{
	protected List<T> objetos;
	private String[] colunas;
	
	public ModeloPadrao(String[] colunas) {
		this.colunas = colunas;
		objetos = new ArrayList<T>();
	}
	
	@Override
	public int getColumnCount() {
		return colunas.length;
	}

	@Override
	public int getRowCount() {
		return objetos.size();
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}
	
	@Override
	public String getColumnName(int column) {
		if (column < colunas.length) {
			return colunas[column];
		}
		
		return null;
	}

	public void addLinha(T obj) {
		objetos.add(obj);
		int ultimoIndice = getRowCount() - 1;
		fireTableRowsInserted(ultimoIndice, ultimoIndice);
	}
	
	public void removerLinha(int linha) {
		objetos.remove(linha);
		fireTableRowsDeleted(linha, linha);
	}
	
	public void removerLinha(T obj) {
		int linha = objetos.indexOf(obj);
		objetos.remove(obj);
		fireTableRowsDeleted(linha, linha);
	}
	
	public void addObjetos(List<T> objetos) {
		this.objetos.removeAll(objetos);
		if (objetos != null) {
			this.objetos = objetos;
		}
		fireTableRowsInserted(0, objetos.size());
	}

}
