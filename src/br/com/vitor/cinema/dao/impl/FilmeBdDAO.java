package br.com.vitor.cinema.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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

public class FilmeBdDAO extends DAO implements FilmeDAO {
	public final static String TAB_FILME = "F";
	
	private final static String SQL_INSERT = "INSERT INTO Filme "
			+ "(NmFilme, Lancamento, Duracao, Sinopse, Autor_CodUsuario) "
			+ "VALUES (?, ?, ?, ?, ?)";

	private final static String SQL_UPDATE = "UPDATE Filme SET "
			+ "NmFilme=?, Lancamento=?, Duracao=?, Sinopse=?, Autor_CodUsuario=? "
			+ "WHERE CodFilme=?";
	
	private final static String SQL_DELETE = "DELETE FROM Filme "
			+ "WHERE CodFilme=?";
	
	private final static String SQL_SELECT = "SELECT * FROM Filme AS " + TAB_FILME
			+ " LEFT JOIN Usuario AS " + UsuarioBdDAO.TAB_USUARIO + " ON "
			+ TAB_FILME + ".Autor_CodUsuario=" + UsuarioBdDAO.TAB_USUARIO + ".CodUsuario WHERE %s "
					+ "ORDER BY NmFilme"; //String.format(
	
	
	@Override
	public int inserir(Filme t) throws DAOException {
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int chaveGerada = -1;
		
		Date dataLancamento = null;
		
		if (t.getLancamento() != null) {
			dataLancamento = new Date(t.getLancamento().getTime());
		}
		
		try {
			conn = this.getConnection();
			ps = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, t.getNome());
			ps.setDate(2, dataLancamento);
			ps.setInt(3, t.getDuracao());
			ps.setString(4,  t.getSinopse());
			
			if (t.getAutor() != null && t.getAutor().getCodigo() > 0) {
				ps.setInt(5, t.getAutor().getCodigo());
			} else {
				ps.setString(5, NULL);
			}
			
			ps.execute();
			
			rs = ps.getGeneratedKeys();
			
			while (rs.next()) {
				chaveGerada = rs.getInt(1);
			}
			
			return chaveGerada;
		} catch (SQLException e) {
			throw new DAOException("Falha na SQL do método FilmeBdDAO.inserir(filme): " + e.getMessage(), e);
		} finally {
			this.close(conn, ps, rs);
		}
	}

	@Override
	public void editar(Filme t) throws DAOException {
		Connection conn;
		PreparedStatement ps;
		
		Date dataLancamento = null;
		
		if (t.getLancamento() != null) {
			dataLancamento = new Date(t.getLancamento().getTime());
		}
		
		try {
			conn = this.getConnection();
			ps = conn.prepareStatement(SQL_UPDATE);
			ps.setString(1, t.getNome());
			ps.setDate(2, dataLancamento);
			ps.setInt(3, t.getDuracao());
			ps.setString(4, t.getSinopse());
			
			if (t.getAutor() != null && t.getAutor().getCodigo() > 0) {
				ps.setInt(5, t.getAutor().getCodigo());
			} else {
				ps.setString(5, NULL);
			}
			
			ps.setInt(6, t.getCodigo());
			ps.execute();
			
		} catch (SQLException e) {
			throw new DAOException("Falha na SQL do método FilmeBdDAO.editar(filme): " + e.getMessage(), e);
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
			throw new DAOException("Falha na SQL do método FilmeBdDAO.deletar(" + id + "): " + e.getMessage(), e);
		}
	}

	@Override
	public List<Filme> consultar(FiltroBusca filtro) throws DAOException {
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null
				;
		
		List<Filme> filmes = new ArrayList<Filme>();
		UsuarioDAO usuarioDAO;
		
		if (filtro == null) {
			Filme filme = new Filme();
			filtro = new FiltroBD(filme);
		}
		
		try {
			conn = this.getConnection();
			ps = filtro.getPreparedStatement(conn, SQL_SELECT);
			rs = ps.executeQuery();
			usuarioDAO = DAOFactory.getUsuarioDAO();
			
			while (rs.next()) {
				Filme filme = instance(rs);
				Usuario autor = usuarioDAO.instance(rs);
				filme.setAutor(autor);
				
				filmes.add(filme);
			}
		} catch (FiltroException e) {
			throw new DAOException("FilmeBdDAO.consultar(filtro), falha no filtro: " + e.getMessage(), e);
		} catch (SQLException e) {
			throw new DAOException("FilmeBdDAO.consultar(filtro), erro de SQL: " + ps.toString() + " - " + e.getMessage(), e);
		} catch (DAOFactoryException e) {
			throw new DAOException("FilmeBdDAO.consultar(filtro), falha na fabrica de DAO: " + ps.toString() + " - " + e.getMessage(), e);
		}
		
		return filmes;
	}

	@Override
	public Filme instance(ResultSet rs) throws DAOException {
		Filme filme = new Filme();
		try {
			filme.setCodigo(rs.getInt(TAB_FILME + ".CodFilme"));
			filme.setDuracao(rs.getInt(TAB_FILME + ".Duracao"));
			filme.setLancamento(rs.getDate(TAB_FILME + ".Lancamento"));
			filme.setNome(rs.getString(TAB_FILME + ".NmFilme"));
			filme.setSinopse(rs.getString(TAB_FILME + ".Sinopse"));
			
		} catch (SQLException ex) {
			throw new DAOException("Erro ao instanciar novo objeto com o ResultSet: " + ex.getMessage(), ex);
		}
		
		return filme;
	}
	
	public static void main(String[] args) throws DAOException, DAOFactoryException {
		//
		/// Testes 
		//
		
		//Inserir
		
//		Filme filme = new Filme();
//		filme.setCodigo(1);
//		filme.setDuracao(80);
//		filme.setLancamento(new Date(System.currentTimeMillis()));
//		filme.setNome("Lagoa Azul");
//		
//		Usuario usuario = new Usuario();
//		usuario.setCodigo(1);
//		filme.setAutor(usuario);
//		
//		FilmeDAO fDao = DAOFactory.getFilmeDAO();
//		fDao.inserir(filme);
		
		//Editar
		
//		Filme filme = new Filme();
//		filme.setCodigo(1);
//		filme.setDuracao(802);
//		filme.setLancamento(new Date(System.currentTimeMillis()));
//		filme.setNome("Lagoa Azul2");
//		
//		Usuario usuario = new Usuario();
//		usuario.setCodigo(1);
//		filme.setAutor(usuario);
//		
//		FilmeDAO fDao = DAOFactory.getFilmeDAO();
//		
//		fDao.editar(filme);
		
		//Deletar
		
//		FilmeDAO fDao = DAOFactory.getFilmeDAO();
//		
//		fDao.deletar(1);
		
		//Consultar
		
//		FilmeDAO fDao = DAOFactory.getFilmeDAO();
//		List<Filme> filmes = fDao.consultar(null);
//		
//		for (Filme filme : filmes) {
//			System.out.println("nome: " + filme.getNome());
//		}
		
	}
}
