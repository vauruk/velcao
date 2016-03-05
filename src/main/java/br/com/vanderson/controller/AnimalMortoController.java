package br.com.vanderson.controller;

import java.util.List;

import br.com.vanderson.app.ExceptionApp;
import br.com.vanderson.model.AnimalMorto;
import br.com.vanderson.model.dao.AnimalMortoDAO;
/**
 * 
 * @author vanderson
 *
 */
public class AnimalMortoController extends ControllerGenerico {
	private AnimalMortoDAO modeloDAO = null;

	public AnimalMortoController() {
		modeloDAO = (AnimalMortoDAO) super.getDaoFactory().criarModeloDAO();
	}

	public void salvar(AnimalMorto Modelo) throws ExceptionApp {
		modeloDAO.gravar(Modelo);
	}

	public void deletar(AnimalMorto Modelo) throws ExceptionApp {
		modeloDAO.excluir(Modelo);
	}

	public List <AnimalMorto> pesquisar( ) throws ExceptionApp {
		return modeloDAO.pesquisar();
	}
	
	public AnimalMorto loadModelo(String modelo ) throws ExceptionApp {
		return modeloDAO.loadModelo(modelo);
	}
	
}
