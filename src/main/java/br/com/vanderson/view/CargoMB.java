package br.com.vanderson.view;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

import org.richfaces.component.SortOrder;

import br.com.vanderson.app.ExceptionApp;
import br.com.vanderson.app.MBGenerico;
import br.com.vanderson.controller.CargoController;
import br.com.vanderson.model.Cargo;
import br.com.vanderson.model.Usuario;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

@SessionScoped
@ManagedBean
public class CargoMB extends MBGenerico {
	/**
	 * 
	 */
	private static final long serialVersionUID = -480971975356554845L;
	private final String LISTAR_CARGO = "listaCargo";//listaCargo.xhtml
	private final String CARGO = "cargo";//cargo.xhtml
	private CargoController cargoController = new CargoController();
	private Cargo cargo;
	private SortOrder nomeOrder = SortOrder.unsorted;
	private SortOrder idOrder = SortOrder.ascending;
	private List <Cargo> listaCargo = null;
	
	private List<SelectItem> listaDropDownCargo;

	public Cargo getCargo( ) {
		if (cargo == null) {
			cargo = new Cargo();
		}
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	public void pesquisar( ) {
		try {
			listaCargo = cargoController.pesquisar();
		} catch (ExceptionApp e) {
			this.messageProcessing(e.getMessage(), null, FacesMessage.SEVERITY_ERROR);
		}
	}

	public String salvar( ) {
		try {
			cargoController.salvar(getCargo());
		} catch (ExceptionApp e) {
			if (e.getCause().getCause() instanceof MySQLIntegrityConstraintViolationException) {
				String key[] = {"cargo", e.getCause().getCause().toString() };
				this.messageProcessing(e.getMessage(), key, FacesMessage.SEVERITY_ERROR);
			} else {
				this.messageProcessing(e.getMessage(), null, FacesMessage.SEVERITY_ERROR);
			}
			return "";
		}
		/**
		 * A string é uma chave q esta no arquivo bundle chamado .properties
		 */
		String key[] = {"cargo" };
		/**
		 * A string é uma chave q esta no arquivo bundle chamado
		 * nutricao_pt.properties
		 */
		this.messageProcessing("mensagem.sucesso", key, FacesMessage.SEVERITY_INFO);
		this.limparCampos();
		return LISTAR_CARGO;
	}

	public String botaoSalvarNovo( ) {
		return "";
	}

	/**
	 * Deleta usuario com chamada AJAX
	 */
	public void deletar( ) {
		Cargo us = null;
		try {
			us = getListaCargo().get(currentIndex);
			cargoController.deletar(us);
			getListaCargo().remove(us);
			setCargo(null);
		} catch (ExceptionApp e) {
			if (e.getCause() instanceof MySQLIntegrityConstraintViolationException) {
				String key[] = {"cargo", String.valueOf(us.getIdCargo()) };
				this.messageProcessing(e.getMessage(), key, FacesMessage.SEVERITY_ERROR);
			} else {
				this.messageProcessing(e.getMessage(), null, FacesMessage.SEVERITY_ERROR);
			}
		}
	}

	/**
	 * Direciona para a tela de criação ou de alteração ºø
	 * 
	 * @return
	 */
	public String irParaAlterar( ) {
		if (super.getBeanSelecionado() != null) {
			this.setCargo((Cargo) super.getBeanSelecionado());
		} else {
			limparCampos();
		}
		return CARGO;
	}

	public String irListaCargo( ) {
		limparCampos();
		pesquisar();
		return redirectPage(LISTAR_CARGO);
	}

	public List <Cargo> getListaCargo( ) {
		pesquisar();
		if (listaCargo == null) {
			listaCargo = new ArrayList <Cargo>();
		}
		return listaCargo;
	}

	public void setListaUsuario(List <Usuario> listaUsuario) {
		// this.listaUsuario = listaUsuario;
	}

	public SortOrder getNomeOrder( ) {
		return nomeOrder;
	}

	public void setNomeOrder(SortOrder nomeOrder) {
		this.nomeOrder = nomeOrder;
	}

	public SortOrder getIdOrder( ) {
		return idOrder;
	}

	public void setIdOrder(SortOrder idOrder) {
		this.idOrder = idOrder;
	}

	public void limparCampos( ) {
		this.setCargo(null);
		this.setListaUsuario(null);
		this.setCurrentIndex(0);
		setBeanSelecionado(null);
	}

	public void setListaCargo(List <Cargo> listaCargo) {
		this.listaCargo = listaCargo;
	}
	
	public List <SelectItem> getListaDropDownCargo( ) {
		try {
			List <Cargo> cargos = cargoController.pesquisar();
			listaDropDownCargo = new ArrayList <SelectItem>();
			for (Cargo item : cargos) {
				listaDropDownCargo.add(new SelectItem(item.getIdCargo(), item.getNome(),""));
			}
		} catch (ExceptionApp e) {
			messageProcessing(e.getMessage(), null, FacesMessage.SEVERITY_ERROR);
			return new ArrayList <SelectItem>();
		}
		return listaDropDownCargo;
	}
}
