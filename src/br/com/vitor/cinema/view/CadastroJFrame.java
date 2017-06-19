package br.com.vitor.cinema.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import br.com.vitor.cinema.dao.DAOFactoryException;
import br.com.vitor.cinema.dao.mySql.DAOException;

public abstract class CadastroJFrame extends JFrame {

	protected JButton btCadastrar;
	protected JButton btAlterar;
	protected JButton btExcluir;
	protected JButton btDetalhar;
	
	protected JTextField tDescricao;
	protected ModeloPadrao modeloTabela;
	protected JTable tabela;
	
	public CadastroJFrame(String titulo) {
		setLayout(new BorderLayout());
		setBounds(100, 100, 800, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle(titulo);
		
		instanciaModeloTabela();
		
		//
		/// Painel norte
		//
		
		btCadastrar = new JButton("+");
		
		JLabel lDescricao = new JLabel("Descrição: ");
		tDescricao = new JTextField(12);
		tDescricao.setText("Descrição");
		tDescricao.setForeground(Color.GRAY);
		
//		JPanel painelDescricao = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
		JPanel painelDescricao = new JPanel();
		painelDescricao.add(lDescricao);
		painelDescricao.add(tDescricao);
		
		JPanel painelBtCadastrar = new JPanel();
		painelBtCadastrar.add(btCadastrar);
		
		JPanel painelNorte = new JPanel(new BorderLayout());
		painelNorte.add(painelDescricao);
		painelNorte.add(painelBtCadastrar, BorderLayout.EAST);
		
		//
		/// Painel centro
		//
		
//		modeloTabela = new DefaultTableModel(new String[]{"Codigo", "Nome"},  0);
		tabela = new JTable(modeloTabela);
//		tabela.setRowSelectionAllowed(true);
		JScrollPane barra = new JScrollPane(tabela);
		
		//
		/// Painel sul
		//
		
		btAlterar = new JButton("Alterar");
		btExcluir = new JButton("Excluir");
		btDetalhar = new JButton("Detalhar");
		
		JPanel painelSul = new JPanel();
		painelSul.add(btAlterar);
		painelSul.add(btExcluir);
		painelSul.add(btDetalhar);
		
		
		this.add(painelNorte, BorderLayout.NORTH);
		this.add(barra, BorderLayout.CENTER);
		this.add(painelSul, BorderLayout.SOUTH);
		
		setEnabledBotoes(false);
		
		tDescricao.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				if (tDescricao.getText().equals("")) {
					tDescricao.setText("Descrição");
					tDescricao.setForeground(Color.GRAY);
				}
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				if (tDescricao.getText().equals("Descrição")) {
					tDescricao.setText("");
				}
				tDescricao.setForeground(Color.BLACK);
			}
		});
		
//		tabela.addMouseListener(new MouseListener() {
//			@Override
//			public void mouseReleased(MouseEvent arg0) {
//			}
//			@Override
//			public void mousePressed(MouseEvent arg0) {
//			}
//			@Override
//			public void mouseExited(MouseEvent arg0) {
//			}
//			@Override
//			public void mouseEntered(MouseEvent arg0) {
//			}
//			@Override
//			public void mouseClicked(MouseEvent arg0) {
//				
//			}
//		});
		
		tabela.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				int rS = tabela.getSelectedRow();
				
				if (rS >= 0) {
					setEnabledBotoes(true);
				} else {
					setEnabledBotoes(false);
				}
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				int rS = tabela.getSelectedRow();
				
				if (rS >= 0) {
					setEnabledBotoes(true);
				} else {
					setEnabledBotoes(false);
				}
			}
		});
		
		Action eventoEnter = new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					preencheTabela(getTextoDescricao());
				} catch (DAOFactoryException | DAOException e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
				}
			}
		};
		
		KeyStroke keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
		InputMap inMap = this.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		inMap.put(keyStroke, "ENTER");
		
		this.getRootPane().getActionMap().put("ENTER", eventoEnter);
	}
	
	public JButton getBtCadastrar() {
		return btCadastrar;
	}

	public JButton getBtAlterar() {
		return btAlterar;
	}

	public JButton getBtExcluir() {
		return btExcluir;
	}

	public JButton getBtDetalhar() {
		return btDetalhar;
	}

	public JTextField gettDescricao() {
		return tDescricao;
	}

	public AbstractTableModel getModeloTabela() {
		return this.modeloTabela;
	}
	
	public JTable getTabela() {
		return tabela;
	}	
	
	public String getTextoDescricao() {
		String texto = tDescricao.getText();
		
		if (texto.equals("Descrição")) {
			return "";
		}
		
		return texto;
	}
	public void setEnabledBotoes(boolean enabled) {
		this.btAlterar.setEnabled(enabled);
		this.btExcluir.setEnabled(enabled);
		this.btDetalhar.setEnabled(enabled);
	}
	
	protected abstract void instanciaModeloTabela();
	
	public abstract void preencheTabela(String descricao) throws DAOFactoryException, DAOException;
}
