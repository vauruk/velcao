package br.com.vanderson.controller;

import java.util.List;

import br.com.vanderson.app.ExceptionApp;
import br.com.vanderson.app.StringUtil;
import br.com.vanderson.model.Usuario;
import br.com.vanderson.model.dao.UsuarioDAO;
/**
 * 
 * @author vanderson
 *
 */
public class UsuarioController extends ControllerGenerico {
	private UsuarioDAO usuarioDAO = null;

	public UsuarioController() {
		usuarioDAO = (UsuarioDAO) super.getDaoFactory().criarUsuarioDAO();
	}

	public void salvar(Usuario usuario) throws ExceptionApp {
		usuarioDAO.evict(usuario);
		usuarioDAO.merge(usuario);
		usuarioDAO.limparCache(usuario);
		
	}

	public void deletar(Usuario usuario) throws ExceptionApp {
		usuarioDAO.excluir(usuario);
	}

	public List <Usuario> pesquisar(String nomeUsuario ) throws ExceptionApp {
		return usuarioDAO.pesquisar(nomeUsuario);
	}
	public Usuario loadUsuario(String user ) throws ExceptionApp {
		return usuarioDAO.loadUsuario(user);
	}

	public Usuario loadUsuario(int idUsuario ) throws ExceptionApp {
		return (Usuario) usuarioDAO.load(Usuario.class , idUsuario);
	}
	public Usuario autenticarUsuario(final String login, final String password) throws ExceptionApp {
		return usuarioDAO.autenticarUsuario(login, StringUtil.criptografarPassword( password ));
	}
}
