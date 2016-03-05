package br.com.vanderson.controller;

import br.com.vanderson.model.dao.DAOFactory;
import br.com.vanderson.model.dao.DAOFactoryHibernate;


public class ControllerGenerico {
	
	private static DAOFactory daoFactory = null ;

	protected static DAOFactory getDaoFactory() {
		if(daoFactory == null){
			daoFactory = new DAOFactoryHibernate();
		}
		return daoFactory;
	}

}
