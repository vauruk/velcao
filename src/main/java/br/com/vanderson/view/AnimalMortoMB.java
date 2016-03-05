package br.com.vanderson.view;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

import org.richfaces.component.SortOrder;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import br.com.vanderson.app.ExceptionApp;
import br.com.vanderson.app.MBGenerico;
import br.com.vanderson.controller.AnimalMortoController;
import br.com.vanderson.model.AnimalMorto;

@SessionScoped
@ManagedBean
public class AnimalMortoMB extends MBGenerico {
	/**
	 * 
	 */
	private static final long serialVersionUID = -480971975356554845L;
	private final String LISTAR_ANIMAL = "listaAnimalMorto";
	private final String ANIMAL_MORTO = "animalMorto";
	private AnimalMortoController animalMortoController = new AnimalMortoController();
	private AnimalMorto animalMorto;
	private SortOrder nomeOrder = SortOrder.unsorted;
	private SortOrder idOrder = SortOrder.ascending;
	private List <AnimalMorto> listaAnimalMorto = null;
	
	private List<SelectItem> listaDropDownAnimalMorto;
	

	public AnimalMorto getAnimalMorto( ) {
		if (animalMorto == null) {
			animalMorto = new AnimalMorto();
		}
		return animalMorto;
	}

	public void setAnimalMorto(AnimalMorto animalMorto) {
		this.animalMorto = animalMorto;
	}

	public void pesquisar( ) {
		try {
			listaAnimalMorto = animalMortoController.pesquisar();
		} catch (ExceptionApp e) {
			this.messageProcessing(e.getMessage(), null, FacesMessage.SEVERITY_ERROR);
		}
	}

	public String salvar( ) {
		try {
			getAnimalMorto().setUsuario(getUsuarioLogado());
			animalMortoController.salvar(getAnimalMorto());
		} catch (ExceptionApp e) {
			if (e.getCause().getCause() instanceof MySQLIntegrityConstraintViolationException) {
				String key[] = {"animal_morto", e.getCause().getCause().toString() };
				this.messageProcessing(e.getMessage(), key, FacesMessage.SEVERITY_ERROR);
			} else {
				this.messageProcessing(e.getMessage(), null, FacesMessage.SEVERITY_ERROR);
			}
			return "";
		}
		/**
		 * A string é uma chave q esta no arquivo bundle chamado .properties
		 */
		String key[] = {"animal_morto" };
		/**
		 * A string é uma chave q esta no arquivo bundle chamado
		 * nutricao_pt.properties
		 */
		this.messageProcessing("mensagem.sucesso", key, FacesMessage.SEVERITY_INFO);
		this.limparCampos();
		return LISTAR_ANIMAL;
	}

	public String botaoSalvarNovo( ) {
		return "";
	}

	/**
	 * Deleta usuario com chamada AJAX
	 */
	public void deletar( ) {
		AnimalMorto us = null;
		try {
			us = getListaAnimalMorto().get(currentIndex);
			animalMortoController.deletar(us);
			getListaAnimalMorto().remove(us);
			setAnimalMorto(null);
		} catch (ExceptionApp e) {
			if (e.getCause() instanceof MySQLIntegrityConstraintViolationException) {
				String key[] = {"animal_morto", String.valueOf(us.getIdAnimalMorto()) };
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
			this.setAnimalMorto((AnimalMorto) super.getBeanSelecionado());
		} else {
			limparCampos();
			getAnimalMorto().setDataRegistro(getDefatultTime());
		}
		return ANIMAL_MORTO;
	}

	public String irListaAnimalMorto( ) {
		limparCampos();
		pesquisar();
		return redirectPage(LISTAR_ANIMAL);
	}

	public List <AnimalMorto> getListaAnimalMorto( ) {
		pesquisar();
		if (listaAnimalMorto == null) {
			listaAnimalMorto = new ArrayList <AnimalMorto>();
		}
		return listaAnimalMorto;
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
		this.setAnimalMorto(null);
		this.setCurrentIndex(0);
		setBeanSelecionado(null);
		
	}

	public void setListaAnimalMorto(List <AnimalMorto> listaAnimalMorto) {
		this.listaAnimalMorto = listaAnimalMorto;
	}
	
	public List <SelectItem> getListaDropDownAnimalMorto( ) {
		try {
			List <AnimalMorto> animalMortos = animalMortoController.pesquisar();
			listaDropDownAnimalMorto = new ArrayList <SelectItem>();
			for (AnimalMorto item : animalMortos) {
				listaDropDownAnimalMorto.add(new SelectItem(item.getIdAnimalMorto(), item.getNome(),""));
			}
		} catch (ExceptionApp e) {
			messageProcessing(e.getMessage(), null, FacesMessage.SEVERITY_ERROR);
			return new ArrayList <SelectItem>();
		}
		return listaDropDownAnimalMorto;
	}
}
