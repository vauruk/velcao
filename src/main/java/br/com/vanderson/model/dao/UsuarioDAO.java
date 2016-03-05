package br.com.vanderson.model.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.vanderson.app.ExceptionApp;
import br.com.vanderson.model.Usuario;

public class UsuarioDAO extends DAOGenerico {
	public UsuarioDAO(Session hibernateSession) {
		super(hibernateSession);
	}

	@SuppressWarnings ( "unchecked" )
	public List <Usuario> pesquisar( String nomeUsuario ) throws ExceptionApp {
		String hql = "SELECT u FROM Usuario u"
			+ " inner join fetch u.cargo as cg  where 1=1 ";
		if(nomeUsuario != null && nomeUsuario.length()>0){
			nomeUsuario = nomeUsuario.trim();
			hql+= " and u.nome  like '%"+nomeUsuario+"%'";
		}
		
		return (List <Usuario>) listar(hql);
	}

	public Usuario loadUsuario(String user) throws ExceptionApp {
		Criteria criteria = hibernateSession.createCriteria(Usuario.class)
				.add(Restrictions.eq("email", user));
		return (Usuario) load(criteria);
	}

	public Usuario autenticarUsuario(final String login, final String password) throws ExceptionApp {
		Criteria criteria =
				hibernateSession.createCriteria(Usuario.class)
				.setFetchMode("cargo", FetchMode.JOIN)
				.add(Restrictions.eq("email", login))
				.add(Restrictions.eq("senha", password));
		Usuario user = (Usuario) load(criteria);
		if(user != null){
			limparCache(user);
		}
		return user;
	}
}
