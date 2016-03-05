package br.com.vanderson.controller;

import java.util.List;

import br.com.vanderson.app.ExceptionApp;
import br.com.vanderson.model.Cargo;
import br.com.vanderson.model.dao.CargoDAO;
/**
 * 
 * @author vanderson
 *
 */
public class CargoController extends ControllerGenerico {
	private CargoDAO cargoDAO = null;

	public CargoController() {
		cargoDAO = (CargoDAO) super.getDaoFactory().criarCargoDAO();
	}

	public void salvar(Cargo cargo) throws ExceptionApp {
		
		cargoDAO.gravar(cargo);
	}

	public void deletar(Cargo cargo) throws ExceptionApp {
		cargoDAO.excluir(cargo);
	}

	public List <Cargo> pesquisar( ) throws ExceptionApp {
		return cargoDAO.pesquisar();
	}
	
	public Cargo loadCargo(String cargo ) throws ExceptionApp {
		return cargoDAO.loadCargo(cargo);
	}
	
}
