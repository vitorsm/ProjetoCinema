package br.com.vitor.cinema.dao;

import br.com.vitor.cinema.dao.mySql.DAO;
import br.com.vitor.cinema.util.ConfigApp;
import br.com.vitor.cinema.util.ConfigAppException;

public class DAOFactory {
	private static DAO getDAO(String propertieClassName) throws DAOFactoryException {
		DAO dao = null;

		try {
			ConfigApp cApp = ConfigApp.instance();
			String className = cApp.getPropertie(propertieClassName);

			Object obj = Class.forName(className).newInstance();

			dao = (DAO) obj;
		} catch (ConfigAppException e) {
			throw new DAOFactoryException("Não foi possível carregar o arquivo de configurações. DAOFactory.getDAO("
					+ propertieClassName + ")", e);
		} catch (InstantiationException e) {
			throw new DAOFactoryException(
					"Não foi possível instanciar o DAO. DAOFactory.getDAO(" + propertieClassName + ")", e);
		} catch (IllegalAccessException e) {
			throw new DAOFactoryException(
					"Não foi possível instanciar o DAO. DAOFactory.getDAO(" + propertieClassName + ")", e);
		} catch (ClassNotFoundException e) {
			throw new DAOFactoryException(
					"Não foi possível instanciar o DAO. DAOFactory.getDAO(" + propertieClassName + ")", e);
		} catch (Exception e) {
			throw new DAOFactoryException(
					"Não foi possível instanciar o DAO. DAOFactory.getDAO(" + propertieClassName + ")", e);
		}

		return dao;
	}

	public static FilmeDAO getFilmeDAO() throws DAOFactoryException {
		String propertiesClassName = "nameClassFilmeDAO";

		DAO dao = getDAO(propertiesClassName);
		
		if (dao instanceof FilmeDAO) {
			return (FilmeDAO) dao;
		} else {
			throw new DAOFactoryException("A classe recuperada do arquivo de configurações não implementa a interface FilmeDAO");
		}
	}
	
	public static UsuarioDAO getUsuarioDAO() throws DAOFactoryException {
		String propertiesClassName = "nameClassUsuarioDAO";

		DAO dao = getDAO(propertiesClassName);
		
		if (dao instanceof UsuarioDAO) {
			return (UsuarioDAO) dao;
		} else {
			throw new DAOFactoryException("A classe recuperada do arquivo de configurações não implementa a interface UsuarioDAO");
		}
	}
}
