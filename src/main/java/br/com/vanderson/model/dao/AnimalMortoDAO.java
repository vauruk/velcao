package br.com.vanderson.model.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.vanderson.app.ExceptionApp;
import br.com.vanderson.model.AnimalMorto;

public class AnimalMortoDAO extends DAOGenerico {

	public AnimalMortoDAO(Session hibernateSession) {
		super(hibernateSession);
	}
	
	@SuppressWarnings ( "unchecked" )
	public List <AnimalMorto> pesquisar() throws ExceptionApp{
		String hql ="SELECT m FROM AnimalMorto m";
		
		return (List <AnimalMorto>) listar(hql);
		
	}
	public AnimalMorto salvar(AnimalMorto modelo){
		try {
			gravar(modelo);
		} catch (ExceptionApp e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public AnimalMorto loadModelo(String modelo) throws ExceptionApp {
		Criteria criteria = hibernateSession.createCriteria(AnimalMorto.class)
				.add(Restrictions.eq("nome", modelo));
		return (AnimalMorto) load(criteria);
	}

}
