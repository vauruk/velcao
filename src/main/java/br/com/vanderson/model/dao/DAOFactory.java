package br.com.vanderson.model.dao;


public interface DAOFactory {

	public abstract DAO criarUsuarioDAO();

	public abstract DAO criarCargoDAO( );
	
	public abstract DAO criarModeloDAO( );
	

	

}
