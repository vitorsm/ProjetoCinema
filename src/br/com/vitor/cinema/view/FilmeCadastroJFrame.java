package br.com.vitor.cinema.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import br.com.vitor.cinema.dao.DAOFactory;
import br.com.vitor.cinema.dao.DAOFactoryException;
import br.com.vitor.cinema.dao.FilmeDAO;
import br.com.vitor.cinema.dao.mySql.DAOException;
import br.com.vitor.cinema.dao.mySql.FiltroBD;
import br.com.vitor.cinema.entidade.Filme;

public class FilmeCadastroJFrame extends CadastroJFrame {
	private FilmeDAO filmeDAO;
	
	public FilmeCadastroJFrame() {
		super("Cadastro de filmes");
		
		try {
			String descricao = this.getTextoDescricao();
			
			preencheTabela(descricao);
		} catch (DAOFactoryException | DAOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	@Override
	protected void instanciaModeloTabela() {
		this.modeloTabela = new ModeloPadrao<Filme>(new String[]{"Código", "Nome", "Sinopse"}) {
			@Override
			public Object getValueAt(int linha, int coluna) {
				if (linha < this.objetos.size()) {
					Filme filme = objetos.get(linha);
					
					switch (coluna) {
					case 0:
						return filme.getCodigo() + "";
					case 1:
						return filme.getNome();
					case 2:
						return filme.getSinopse();
					default:
						return null;
					}
				}
				return null;
			}
			
		};
		
	}
	
	public static void main(String[] args) {
		FilmeCadastroJFrame tela = new FilmeCadastroJFrame();
		tela.setVisible(true);
	}

	@Override
	public void preencheTabela(String descricao) throws DAOFactoryException, DAOException {
		Filme filme = new Filme();
		try {
			int cod = Integer.parseInt(descricao);
			filme.setCodigo(cod);
		} catch (NumberFormatException ex) {
			filme.setNome(descricao);
		}
		
		FiltroBD filtroBD = new FiltroBD(filme);
		
		if (filmeDAO == null) {
			filmeDAO = DAOFactory.getFilmeDAO();
		}
		
		List<Filme> filmes = filmeDAO.consultar(filtroBD);
		this.modeloTabela.addObjetos(filmes);
		
		this.setEnabledBotoes(false);
	}
	
}
