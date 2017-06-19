package br.com.vitor.cinema.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.vitor.cinema.InicioProg;
import br.com.vitor.cinema.dao.DAOFactory;
import br.com.vitor.cinema.dao.DAOFactoryException;
import br.com.vitor.cinema.dao.FilmeDAO;
import br.com.vitor.cinema.dao.FiltroBusca;
import br.com.vitor.cinema.dao.UsuarioDAO;
import br.com.vitor.cinema.dao.mySql.DAO;
import br.com.vitor.cinema.dao.mySql.DAOException;
import br.com.vitor.cinema.dao.mySql.FiltroBD;
import br.com.vitor.cinema.dao.mySql.FiltroException;
import br.com.vitor.cinema.entidade.Filme;
import br.com.vitor.cinema.entidade.Usuario;
import br.com.vitor.cinema.util.ConfigApp;

public class UsuarioBdDAO extends DAO implements UsuarioDAO {
	public final static String TAB_USUARIO = "U";
	
	private final static String SQL_LOGIN = "SELECT * FROM Usuario AS " + TAB_USUARIO
			+ " WHERE UserName=? AND Senha=?";
	
	private final static String SQL_INSERT = "INSERT INTO Usuario "
			+ "(NmUsuario, UserName, Senha) "
			+ "VALUES (?, ?, ?)";

	private final static String SQL_UPDATE = "UPDATE Usuario SET "
			+ "NmUsuario=?, UserName=?, Senha=? "
			+ "WHERE CodUsuario=?";
	
	private final static String SQL_DELETE = "DELETE FROM Usuario "
			+ "WHERE CodUsuario=?";
	
	private final static String SQL_SELECT = "SELECT * FROM Usuario AS " + TAB_USUARIO
			+ " WHERE %s"; //String.format(
	
	
	@Override
	public Usuario autenticaUsuario(String userName, String senha) throws DAOException {
		
		Connection conn;
		PreparedStatement ps;
		ResultSet rs;
		
		Usuario usuario = null;
		
		try {
			conn = this.getConnection();
			ps = conn.prepareStatement(SQL_LOGIN);
			ps.setString(1, userName);
			ps.setString(2, senha);
			
			rs = ps.executeQuery();
			
			
			while (rs.next()) {
				usuario = instance(rs);
			}
			
		} catch (SQLException e) {
			throw new DAOException("UsuarioBdDAO.autenticaUsuario(" + userName + ", " + senha + "), falha no SQL: " + e.getMessage(), e);
		}
		
		return usuario;
	}
	
	@Override
	public int inserir(Usuario t) throws DAOException {
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int chaveGerada = -1;
		
		try {
			conn = this.getConnection();
			ps = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, t.getNome());
			ps.setString(2, t.getUserName());
			ps.setString(3, t.getSenha());
			
			ps.execute();
			
			rs = ps.getGeneratedKeys();
			
			while (rs.next()) {
				chaveGerada = rs.getInt(1);
			}
			
			return chaveGerada;
		} catch (SQLException e) {
			throw new DAOException("Falha na SQL do método UsuarioBdDAO.inserir(usuario): " + ps.toString() + " - " + e.getMessage(), e);
		} finally {
			this.close(conn, ps, rs);
		}
	}

	@Override
	public void editar(Usuario t) throws DAOException {
		Connection conn;
		PreparedStatement ps;
		
		try {
			conn = this.getConnection();
			ps = conn.prepareStatement(SQL_UPDATE);
			ps.setString(1, t.getNome());
			ps.setString(2, t.getUserName());
			ps.setString(3, t.getSenha());
			ps.setInt(4, t.getCodigo());
			
			ps.execute();
			
		} catch (SQLException e) {
			throw new DAOException("Falha na SQL do método UsuarioBdDAO.editar(usuario): " + e.getMessage(), e);
		}
	}

	@Override
	public void deletar(int id) throws DAOException {
		Connection conn;
		PreparedStatement ps;
		
		try {
			conn = this.getConnection();
			ps = conn.prepareStatement(SQL_DELETE);
			ps.setInt(1, id);
			
			ps.execute();
		} catch (SQLException e) {
			throw new DAOException("Falha na SQL do método UsuarioBdDAO.deletar(" + id + "): " + e.getMessage(), e);
		}
	}

	@Override
	public List<Usuario> consultar(FiltroBusca filtro) throws DAOException {
		
		Connection conn;
		PreparedStatement ps;
		ResultSet rs;
		
		List<Usuario> usuarios = new ArrayList<Usuario>();
		
		if (filtro == null) {
			Usuario usuario = new Usuario();
			filtro = new FiltroBD(usuario);
		}
		
		try {
			conn = this.getConnection();
			ps = filtro.getPreparedStatement(conn, SQL_SELECT);
			rs = ps.executeQuery();
			
			while (rs.next()) {
				Usuario usuario = instance(rs);
				
				usuarios.add(usuario);
			}
		} catch (FiltroException e) {
			throw new DAOException("UsuarioBdDAO.consultar(filtro), falha no filtro: " + e.getMessage(), e);
		} catch (SQLException e) {
			throw new DAOException("UsuarioBdDAO.consultar(filtro), falha no SQL: " + e.getMessage(), e);
		}
		
		return usuarios;
	}

	@Override
	public Usuario instance(ResultSet rs) throws DAOException {
		Usuario usuario = new Usuario();
		
		try {
			usuario.setCodigo(rs.getInt(TAB_USUARIO + ".CodUsuario"));
			usuario.setNome(rs.getString(TAB_USUARIO + ".NmUsuario"));
			usuario.setUserName(rs.getString(TAB_USUARIO + ".UserName"));
			usuario.setSenha(rs.getString(TAB_USUARIO + ".Senha"));
		} catch (SQLException ex) {
			throw new DAOException("Não foi possível instanciar um Usuario: " + ex.getMessage(), ex);
		}
		
		return usuario;
	}

	public static void main(String []args) throws DAOFactoryException, DAOException {
		
		//
		/// Testes 
		//
		
		//Inserir
		
//		Usuario usuario = new Usuario();
//		usuario.setNome("Vítor de Sousa Moreira");
//		usuario.setUserName("vitor");
//		usuario.setSenha("senha");
//		
//		UsuarioDAO usuarioDAO = DAOFactory.getUsuarioDAO();
//		
//		usuarioDAO.inserir(usuario);
		
		//Editar
		
//		Usuario usuario = new Usuario();
//		usuario.setCodigo(1);
//		usuario.setNome("Vítor Moreira");
//		usuario.setUserName("vitor2");
//		usuario.setSenha("senha2");
//		
//		UsuarioDAO usuarioDAO = DAOFactory.getUsuarioDAO();
//		
//		usuarioDAO.editar(usuario);
		
		//Deletar
		
//		UsuarioDAO usuarioDAO = DAOFactory.getUsuarioDAO();
//		
//		usuarioDAO.deletar(1);
		
		//Consultar
		
//		try {
//			UsuarioDAO usuarioDAO = DAOFactory.getUsuarioDAO();
//			List<Usuario> usuarios = usuarioDAO.consultar(null);
//			
//			for (Usuario usuario : usuarios) {
//				System.out.println("UserName: " + usuario.getUserName());
//			}
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
}
