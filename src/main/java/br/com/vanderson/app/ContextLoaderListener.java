package br.com.vanderson.app;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import br.com.vanderson.model.dao.PersistenceManager;
/**
 *
 */
public class ContextLoaderListener implements ServletContextListener {

	
	public static ServletContext webContext;
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		System.out.println("sasdsa");
	}

	@Override
	public void contextInitialized(ServletContextEvent contextEvent) {
		PersistenceManager.initialize(); 
		PersistenceManager.createUserAdmin(); 
	}
	
	
}
