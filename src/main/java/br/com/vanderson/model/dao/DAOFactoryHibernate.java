package br.com.vanderson.model.dao;

import br.com.vanderson.app.ExceptionApp;

public class DAOFactoryHibernate implements DAOFactory {

	@Override
	public DAO criarUsuarioDAO() {
		try {
			return new UsuarioDAO(PersistenceManager.getSession());
		} catch (ExceptionApp e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public DAO criarCargoDAO() {
		try {
			return new CargoDAO(PersistenceManager.getSession());
		} catch (ExceptionApp e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public DAO criarModeloDAO( ) {
		try {
			return new AnimalMortoDAO(PersistenceManager.getSession());
		} catch (ExceptionApp e) {
			e.printStackTrace();
		}
		return null;
	}

}
