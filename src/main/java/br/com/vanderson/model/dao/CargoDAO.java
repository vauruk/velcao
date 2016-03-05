package br.com.vanderson.model.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.vanderson.app.ExceptionApp;
import br.com.vanderson.model.Cargo;

public class CargoDAO extends DAOGenerico {

	public CargoDAO(Session hibernateSession) {
		super(hibernateSession);
	}
	
	@SuppressWarnings ( "unchecked" )
	public List <Cargo> pesquisar() throws ExceptionApp{
		String hql ="SELECT c FROM Cargo c";
		
		return (List <Cargo>) listar(hql);
		
	}
	public Cargo salvar(Cargo cargo){
		try {
			gravar(cargo);
		} catch (ExceptionApp e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Cargo loadCargo(String cargo) throws ExceptionApp {
		Criteria criteria = hibernateSession.createCriteria(Cargo.class)
				.add(Restrictions.eq("nome", cargo));
		return (Cargo) load(criteria);
	}

}
