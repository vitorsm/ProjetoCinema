package br.com.vitor.cinema.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import br.com.vitor.cinema.dao.DAOFactory;
import br.com.vitor.cinema.dao.DAOFactoryException;
import br.com.vitor.cinema.dao.UsuarioDAO;
import br.com.vitor.cinema.dao.mySql.DAOException;
import br.com.vitor.cinema.dao.mySql.FiltroBD;
import br.com.vitor.cinema.entidade.Usuario;

public class UsuarioCadastroJFrame extends CadastroJFrame {
	private UsuarioDAO usuarioDAO;
	
	public UsuarioCadastroJFrame() {
		super("Cadastro de usuário");
		
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
		this.modeloTabela = new ModeloPadrao<Usuario>(new String[]{"Código", "Nome", "UserName"}) {
			@Override
			public Object getValueAt(int linha, int coluna) {
				if (linha < this.objetos.size()) {
					Usuario usuario = objetos.get(linha);
					
					switch (coluna) {
					case 0:
						return usuario.getCodigo() + "";
					case 1:
						return usuario.getNome();
					case 2:
						return usuario.getUserName();
					default:
						return null;
					}
				}
				return null;
			}
			
		};
		
	}
	
	public static void main(String[] args) {
		UsuarioCadastroJFrame tela = new UsuarioCadastroJFrame();
		tela.setVisible(true);
	}

	@Override
	public void preencheTabela(String descricao) throws DAOFactoryException, DAOException {
		Usuario usuario = new Usuario();
		try {
			int cod = Integer.parseInt(descricao);
			usuario.setCodigo(cod);
		} catch (NumberFormatException ex) {
			usuario.setNome(descricao);
		}
		
		FiltroBD filtroBD = new FiltroBD(usuario);
		
		if (usuarioDAO == null) {
			usuarioDAO = DAOFactory.getUsuarioDAO();
		}
		
		List<Usuario> usuarios = usuarioDAO.consultar(filtroBD);
		this.modeloTabela.addObjetos(usuarios);
		
		this.setEnabledBotoes(false);
	}
	
}
