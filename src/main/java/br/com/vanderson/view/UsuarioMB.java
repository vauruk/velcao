package br.com.vanderson.view;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

import org.richfaces.component.SortOrder;

import br.com.vanderson.app.ExceptionApp;
import br.com.vanderson.app.MBGenerico;
import br.com.vanderson.app.StringUtil;
import br.com.vanderson.controller.UsuarioController;
import br.com.vanderson.model.Usuario;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

/**
 * @author vanderson
 *         Data e Hora: 07/09/2015 - 22:31:00
 */
@SessionScoped
@ManagedBean
public class UsuarioMB extends MBGenerico {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8030088075018980074L;
	/**
	 * A string contida nessa variavel faz referencia direta ao arquivo XHTML da mesma pasta
	 * Ex: listarIngrediente vai redirecionar para /listarIngrediente.xhtml
	 */
	private final String LISTAR_USUARIO = "listaUsuario";
	private final String USUARIO = "usuario";
	private UsuarioController usuarioController = new UsuarioController();
	private Usuario usuario = null;
	private List <Usuario> listaUsuario;
	private SortOrder nomeOrder = SortOrder.unsorted;
	private SortOrder idOrder = SortOrder.ascending;
	private List <SelectItem> listaDropDownUsuario = null;
	private List <SelectItem> listaDropDownUsuarioAdministrador = null;
	private String senhaCripto="";
	private String nomeUsuario ="";

	public Usuario getUsuario( ) {
		if (usuario == null) {
			usuario = new Usuario();
		}
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public void pesquisar( ) {
		try {
			listaUsuario = usuarioController.pesquisar(nomeUsuario);
		} catch (ExceptionApp e) {
			this.messageProcessing(e.getMessage(), null, FacesMessage.SEVERITY_ERROR);
		}
	}

	public String botaoSalvar( ) {
		/*
		 * try {
		 * usuarioController.salvar(getUsuario());
		 * } catch (ExceptionApp e) {
		 * if(e.getCause().getCause() instanceof MySQLIntegrityConstraintViolationException){
		 * String key[] = {"usuario",e.getCause().getCause().toString() };
		 * this.messageProcessing(e.getMessage(), key, FacesMessage.SEVERITY_ERROR);
		 * }else{
		 * this.messageProcessing(e.getMessage(), null, FacesMessage.SEVERITY_ERROR);
		 * }
		 * return "";
		 * }
		 * /**
		 * A string é uma chave q esta no arquivo bundle chamado nutricao_pt.properties
		 * /
		 * String key[] = {"usuario" };
		 * 
		 * /**
		 * A string é uma chave q esta no arquivo bundle chamado nutricao_pt.properties
		 * /
		 * this.messageProcessing("mensagem.sucesso", key, FacesMessage.SEVERITY_INFO);
		 * this.limparCampos();
		 */
		return LISTAR_USUARIO;
	}

	public String salvar( ) {
		try {
			if(getUsuario().getSenha().trim().length()==0){
				getUsuario().setSenha(senhaCripto);
			}
			getUsuario().setDataAdmissao(new Date());
			getUsuario().setSenha(StringUtil.criptografarPassword(getUsuario().getSenha()));
			getUsuario().setAtivo(true);
			
			usuarioController.salvar(getUsuario());
		} catch (ExceptionApp e) {
			if (e.getCause().getCause() instanceof MySQLIntegrityConstraintViolationException) {
				String key[] = {"usuario", e.getCause().getCause().toString() };
				this.messageProcessing(e.getMessage(), key, FacesMessage.SEVERITY_ERROR);
			} else {
				this.messageProcessing(e.getMessage(), null, FacesMessage.SEVERITY_ERROR);
			}
			return "";
		}
		/**
		 * A string é uma chave q esta no arquivo bundle chamado nutricao_pt.properties
		 */
		String key[] = {"usuario" };
		/**
		 * A string é uma chave q esta no arquivo bundle chamado nutricao_pt.properties
		 */
		this.messageProcessing("mensagem.sucesso", key, FacesMessage.SEVERITY_INFO);
		this.limparCampos();
		pesquisar();
		return LISTAR_USUARIO;
	}

	/**
	 * Deleta usuario com chamada AJAX
	 */
	public void deletar( ) {
		Usuario us = null;
		try {
			us = getListaUsuario().get(currentIndex);
			usuarioController.deletar(us);
			getListaUsuario().remove(us);
			setUsuario(null);
		} catch (ExceptionApp e) {
			if (e.getCause() instanceof MySQLIntegrityConstraintViolationException) {
				String key[] = {"usuario", String.valueOf(us.getIdUsuario()) };
				this.messageProcessing(e.getMessage(), key, FacesMessage.SEVERITY_ERROR);
			} else {
				this.messageProcessing(e.getMessage(), null, FacesMessage.SEVERITY_ERROR);
			}
		}
	}

	/**
	 * Direciona para a tela de criação ou de alteração
	 * 
	 * @return
	 */
	public String irParaAlterar( ) {
		if (super.getBeanSelecionado() != null) {
			this.setUsuario((Usuario) super.getBeanSelecionado());
			if(getUsuario().getSenha().length() > 10){
				senhaCripto=getUsuario().getSenha();
				getUsuario().setSenha("");
			}
		} else {
			limparCampos();
		}
		return USUARIO;
	}

	public String irListaUsuario( ) {
		limparCampos();
		pesquisar();
		return redirectPage(LISTAR_USUARIO);
	}

	public List <Usuario> getListaUsuario( ) {
		if (listaUsuario == null) {
			listaUsuario = new ArrayList <Usuario>();
		}
		return listaUsuario;
	}

	public void setListaUsuario(List <Usuario> listaUsuario) {
		this.listaUsuario = listaUsuario;
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

	public List <SelectItem> getListaDropDownUsuario( ) {
		try {
			List <Usuario> usuarios = usuarioController.pesquisar(null);
			listaDropDownUsuario = new ArrayList <SelectItem>();
			listaDropDownUsuario.add(selecione);
			for ( Usuario item : usuarios ) {
				listaDropDownUsuario.add(new SelectItem(item.getIdUsuario(), item.getNome(), ""));
			}
		} catch (ExceptionApp e) {
			messageProcessing(e.getMessage(), null, FacesMessage.SEVERITY_ERROR);
			return new ArrayList <SelectItem>();
		}
		return listaDropDownUsuario;
	}

	public void setListaDropDownUsuario(List <SelectItem> listaDropDownUsuario) {
		this.listaDropDownUsuario = listaDropDownUsuario;
	}

	public void limparCampos( ) {
		senhaCripto = "";
		this.setUsuario(null);
		this.setListaUsuario(null);
		this.setCurrentIndex(0);
		setBeanSelecionado(null);
	}

	public List <SelectItem> getListaDropDownUsuarioAdministrador( ) {
		/*
		 * try {
		 * List <Usuario> usuarios = usuarioController.pesquisarPorTipo(TipoUsuario.ADMINISTRADOR);
		 * listaDropDownUsuarioAdministrador = new ArrayList <SelectItem>();
		 * for (Usuario item : usuarios) {
		 * listaDropDownUsuarioAdministrador.add(new SelectItem(item.getIdUsuario(), item.getNome(),""));
		 * }
		 * } catch (ExceptionApp e) {
		 * messageProcessing(e.getMessage(), null, FacesMessage.SEVERITY_ERROR);
		 * return new ArrayList <SelectItem>();
		 * }
		 */
		return listaDropDownUsuarioAdministrador;
	}

	public void setListaDropDownUsuarioAdministrador(List <SelectItem> listaDropDownUsuarioAdministrador) {
		this.listaDropDownUsuarioAdministrador = listaDropDownUsuarioAdministrador;
	}

	public String getNomeUsuario( ) {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}
}
