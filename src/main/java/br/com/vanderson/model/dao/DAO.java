package br.com.vanderson.model.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;

import br.com.vanderson.app.ExceptionApp;
import br.com.vanderson.model.EntidadeApp;

/**
 * 
 * @author vanderson
 *
 */
public interface DAO {
	
	public EntidadeApp load(Class<? extends EntidadeApp> obj, Serializable arg) throws ExceptionApp;
	
	public List<?> listar(Criteria criteria) throws ExceptionApp;
	
	public void gravar(EntidadeApp obj) throws ExceptionApp;
	
	public void excluir(EntidadeApp obj) throws ExceptionApp ;
	
	public void alterar(EntidadeApp obj) throws ExceptionApp ;
	
	public void merge(EntidadeApp obj) throws ExceptionApp;
	
	public EntidadeApp load(Criteria criteria) throws ExceptionApp;
	
}
