package br.com.vanderson.view;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.vanderson.app.ExceptionApp;
import br.com.vanderson.app.MBGenerico;
import br.com.vanderson.controller.UsuarioController;
import br.com.vanderson.model.Usuario;

@ManagedBean
@SessionScoped
public class LoginMB extends MBGenerico {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7284464358767887035L;
	private UsuarioController usuarioController = new UsuarioController();
	private String login = "";
	private String password = "";
	private Usuario usuarioLogado;
	private String saudacao;
	

	public String entrarNoSistema () { 
		try {
			usuarioLogado = usuarioController.autenticarUsuario(getLogin(), getPassword());
		} catch (ExceptionApp e) {
			e.printStackTrace();
		}
		if(usuarioLogado == null){
			messageProcessing("usuario.nao.encontrado", null,FacesMessage.SEVERITY_INFO );
			return "";
		}
		return HOME;
	}

	public String sairDoSistema( ) {
		this.setUsuarioLogado(null);
		return HOME_LOGIN;
	}

	public String getLogin( ) {
		return login;
	}
	
	public String saudacao(){
		Calendar cal = GregorianCalendar.getInstance();
		int hora = cal.get(Calendar.HOUR_OF_DAY);
		if(hora >= 0 && hora < 12){
			saudacao = messageProcessing("mensagem.bom.dia", null, null); 
		}if(hora >= 12 && hora <= 23){
			saudacao = messageProcessing("mensagem.boa.tarde", null, null); 
		}if(hora >18 && hora <= 23){
			saudacao = messageProcessing("mensagem.boa.noite", null, null); 
		}
		saudacao = saudacao +", " +getUsuarioLogado().getNome() ;
			
		return saudacao;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword( ) {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Usuario getUsuarioLogado( ) {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}
}
