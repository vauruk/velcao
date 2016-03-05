package br.com.vanderson.model.dao;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import br.com.vanderson.app.ExceptionApp;
import br.com.vanderson.app.StringUtil;
import br.com.vanderson.controller.CargoController;
import br.com.vanderson.controller.UsuarioController;
import br.com.vanderson.model.AnimalMorto;
import br.com.vanderson.model.Cargo;
import br.com.vanderson.model.Usuario;


public class PersistenceManager {
	private static SessionFactory sessionFactory;
	private static ServiceRegistry serviceRegistry;

	public PersistenceManager() {
		initialize();
		createUserAdmin();
	}

	public static void createUserAdmin( ) {
		String strAdmin = "admin";
		UsuarioController usuarioController = new UsuarioController();
		CargoController cargoController = new CargoController();
		try {
			Usuario usuario = usuarioController.loadUsuario(strAdmin);
			if (usuario == null) {
				usuario = new Usuario();
				usuario.setNome(strAdmin);
				usuario.setCpf("00000000000");
				usuario.setRg("00000000000");
				usuario.setTelefone("rg");
				usuario.setDataAdmissao(new Date());
				usuario.setEmail(strAdmin);
				usuario.setSenha(StringUtil.criptografarPassword(strAdmin));
				usuario.setAdministrador(true);
				usuario.setAtivo(true);
				Cargo cargo = cargoController.loadCargo(strAdmin);
				if (cargo == null) {
					cargo = new Cargo();
					cargo.setNome(strAdmin);
					cargo.setTipoCargo(strAdmin);
					cargo.setGerente(true);
					cargo.setOperacional(true);
					cargo.setSupervisao(true);
					cargoController.salvar(cargo);
					
				}
				usuario.setCargo(cargo);
				usuarioController.salvar(usuario);
			}
		} catch (ExceptionApp e) {
			e.printStackTrace();
		}
	}


	public static void initialize( ) {
		Configuration configuration = new Configuration().configure();
		configuration
		.addAnnotatedClass(Cargo.class)
		.addAnnotatedClass(AnimalMorto.class)
		.addAnnotatedClass(Usuario.class);
		
		serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		
	}

	public static SessionFactory getSessionFactory( ) {
		return sessionFactory;
	}

	public static void setSessionFactory(SessionFactory sessionFactory) {
		PersistenceManager.sessionFactory = sessionFactory;
	}

	public static Session getSession( ) throws ExceptionApp {
		Session session = sessionFactory.openSession();
		return session;
	}
}
