package br.com.vitor.cinema.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class UsuarioCadastroJFrame extends JFrame {

	private JButton btCadastrar;
	private JButton btAlterar;
	private JButton btExcluir;
	private JButton btDetalhar;
	
	private JTextField tDescricao;
	private DefaultTableModel modeloTabela;
	private JTable tabela;
	
	public UsuarioCadastroJFrame() {
		setLayout(new BorderLayout());
		setBounds(100, 100, 800, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Cadastro usuário");
		
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
		
		tabela = new JTable();
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
	}
	
	public static void main(String[] args) {
		UsuarioCadastroJFrame tela = new UsuarioCadastroJFrame();
		
		tela.setVisible(true);
	}
}
