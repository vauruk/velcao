package br.com.vanderson.model.dao;

import java.io.Serializable;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.StaleObjectStateException;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import br.com.vanderson.app.ExceptionApp;
import br.com.vanderson.model.EntidadeApp;

public class DAOGenerico implements DAO {
	protected static Session hibernateSession;

	@SuppressWarnings ( "static-access" )
	public DAOGenerico(Session hibernateSession) {
		if (hibernateSession == null) {
			System.out.println("NUll Session");
		}
		this.hibernateSession = hibernateSession;
	}

	public Session getSession( ) {
		return hibernateSession;
	}

	/**
	 * Método responsável por realizar o "load" de um objeto
	 * 
	 * @param obj
	 *        Tipo do objeto a ser lido
	 * @param arg
	 *        Chave(PK) do objeto a ser lido
	 * @return objeto carregado
	 * @throws Exception
	 */
	public EntidadeApp load(Class <? extends EntidadeApp> obj, Serializable arg) throws ExceptionApp {
		try {
			return (EntidadeApp) hibernateSession.get(obj, arg);
		} finally {
			// hibernateSession.close();
		}
	}

	public EntidadeApp load(Criteria criteria) throws ExceptionApp {
		try {
			return (EntidadeApp) criteria.uniqueResult();
		} finally {
			// closeSession();
		}
	}

	/**
	 * Método responsável por recuperar uma lista de objetos do tipo
	 * EntidadeApp
	 * 
	 * @param Criteria
	 *        query com o filtro a ser recuperado
	 * @return lista de objetos
	 * @throws Exception
	 *         EXEMPLO:
	 *         Criteria criteria = getSession().createCriteria(clazz.getName());
	 *         criteria.setFetchMode("listaUsuarioPcib", FetchMode.JOIN)
	 *         .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
	 */
	@Override
	public List <?> listar(Criteria criteria) throws ExceptionApp {
		try {
			return criteria.list();
		} finally {
			// closeSession();
		}
	}

	/**
	 * Método responsável por recuperar uma lista de objetos do tipo
	 * EntidadeApp
	 * 
	 * @param hqlQuery
	 *        query com o filtro a ser recuperado
	 * @return lista de objetos
	 * @throws Exception
	 */
	public List <?> listar(Query hqlQuery) throws ExceptionApp {
		try {
			return hqlQuery.list();
		} finally {
			// closeSession();
		}
	}

	/**
	 * Método responsável por gravar ou atualizar um objeto.
	 * 
	 * @param obj
	 *        Objeto a ser persistido
	 * @throws ExceptionApp
	 */
	@Override
	public void gravar(EntidadeApp obj) throws ExceptionApp {
		try {
			beginTransaction();
			hibernateSession.saveOrUpdate(obj);
			commitTransaction();
			limparCache(obj);
		} catch (Exception e) {
			if (e.getCause() instanceof StaleObjectStateException) {
				// throw new ExceptionApp(Mensagens.MSG_INFO_028.getKey());
				e = new ExceptionApp("erro ao atualizar");
			} else if (e.getCause() instanceof MySQLIntegrityConstraintViolationException) {
				throw new ExceptionApp("mensagem.erro.unique", e.getCause());
			} else {
				this.lancarExcecao(e);
			}
			rollbackTransaction();
			e.printStackTrace();
		} finally {
			closeSession();
		}
	}

	private void lancarExcecao(Exception e) throws ExceptionApp {
		e.printStackTrace();
		throw new ExceptionApp("", e);
	}

	/**
	 * Método responsável em atualizar o objeto
	 * 
	 * @param obj
	 *        Objeto a ser atualizado
	 * @throws ExceptionApp
	 */
	public void alterar(EntidadeApp obj) throws ExceptionApp {
		try {
			beginTransaction();
			hibernateSession.update(obj);
			commitTransaction();
		} catch (ExceptionApp e) {
			rollbackTransaction();
			// this.lancarExcecao(e, LOGGER);
			e.printStackTrace();
		} finally {
			// closeSession();
		}
	}

	/**
	 * Método responsável por gravar ou atualizar um objeto. Utilize este método
	 * quando precisar persistir e reutilizar o objeto na sequencia.
	 * 
	 * @param obj
	 *        Objeto a ser persistido
	 * @throws Exception
	 */
	public void merge(EntidadeApp obj) throws ExceptionApp {
		try {
			beginTransaction();
			hibernateSession.merge(obj);
			commitTransaction();
		} catch (ExceptionApp e) {
			rollbackTransaction();
			e.printStackTrace();
			// this.lancarExcecao(e, LOGGER);
		} finally {
			// closeSession();
		}
	}

	/**
	 * Método responsável por recuperar uma lista de objetos do tipo
	 * EntidadeApp.
	 * 
	 * @param hqlQuery
	 *        query com o filtro a ser recuperado
	 * @return lista de objetos
	 * @throws Exception
	 */
	public List <?> listar(String query) throws ExceptionApp {
		try {
			return this.listar(hibernateSession.createQuery(query));
		} catch (ExceptionApp e) {
			e.printStackTrace();
		} finally {
			// closeSession();
		}
		return null;
	}

	/**
	 * Método responsável em eliminar um objeto persistente
	 * 
	 * @param obj
	 *        Objeto a ser excluido
	 * @throws ExceptionApp
	 *         Erro de exclus�o do objeto
	 */
	public void excluir(EntidadeApp obj) throws ExceptionApp {
		try {
			beginTransaction();
			hibernateSession.delete(obj);
			hibernateSession.flush();
			commitTransaction();
		} catch (Exception e) {
			rollbackTransaction();
			// Caso ocorra erro de constraint
			if (e.getCause() instanceof MySQLIntegrityConstraintViolationException) {
				// if (e.getCause() instanceof ConstraintViolationException) {
				throw new ExceptionApp("mensagem.dependente", e.getCause());
			}
			e.printStackTrace();
			// this.lancarExcecao(new ExceptionApp(e.getMessage()), LOGGER);
		} finally {
			// closeSession();
		}
	}

	/**
	 * Metodo que "expulsa" o objeto da sessao do hibernate.
	 * evict = Expulsar
	 * 
	 * @param obj
	 */
	@SuppressWarnings ( "static-access" )
	public void limparCache(EntidadeApp obj) {
		this.hibernateSession.flush();
		this.hibernateSession.evict(obj);
	}

	@SuppressWarnings ( "static-access" )
	public void evict(EntidadeApp obj) {
		this.hibernateSession.evict(obj);
	}

	private static final ThreadLocal <Session> threadSession = new ThreadLocal <Session>();
	private static final ThreadLocal <Transaction> threadTransaction = new ThreadLocal <Transaction>();

	public static void beginTransaction( ) throws ExceptionApp {
		Transaction tx = (Transaction) threadTransaction.get();
		try {
			if (tx == null) {
				System.out.println("--->> Iniciando uma nova transação com o banco de dados na ThreadLocal <<--");
				tx = hibernateSession.beginTransaction();
				threadTransaction.set(tx);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void commitTransaction( ) throws ExceptionApp {
		Transaction tx = (Transaction) threadTransaction.get();
		try {
			if (tx != null) {
				System.out.println("--->> Executando commit de alterações no banco na ThreadLocal<<--");
				tx.commit();
			}
			threadTransaction.set(null);
		} catch (Exception e) {
			e.printStackTrace();
			// throw new ExceptionApp(e);
		}
	}

	public static void rollbackTransaction( ) throws ExceptionApp {
		Transaction tx = (Transaction) threadTransaction.get();
		try {
			threadTransaction.set(null);
			if (tx != null ) {
				System.out.println("--->> Executando Rollback das alterações no banco na ThreadLocal<<--");
				tx.rollback();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ExceptionApp(e.getMessage());
		} finally {
			// hibernateSession.close();
		}
	}

	/**
	 * @author Desconhecido
	 *         Closes the Session local to the thread.
	 */
	public static void closeSession( ) throws ExceptionApp {
		try {
			Transaction tx = (Transaction) threadTransaction.get();
			try {
				if (tx != null) {
					rollbackTransaction();
				}
				threadTransaction.set(null);
			} catch (HibernateException ex) {
				rollbackTransaction();
			}
			Session s = (Session) threadSession.get();
			threadSession.set(null);
			if (s != null && s.isOpen()) {
				System.out.println("--->> Fechando a Conexão na ThreadLocal <<--");
				s.clear();
				s.flush();
				// s.close();
			}
		} catch (HibernateException ex) {
			ex.printStackTrace();
			throw new ExceptionApp("", ex);
		}
	}

	public Connection getConnection( ) {
		Configuration configuration = new Configuration().configure();
		String driver = configuration.getProperty("hibernate.dialect");
		// driver = "org.hibernate.dialect.MySQLDialect";
		String url = configuration.getProperty("hibernate.connection.url");
		// url = "jdbc:mysql://127.0.0.1:3306/gerauto?autoReconnect=true";
		String username = configuration.getProperty("hibernate.connection.username");
		// username = "gerauto";
		String password = configuration.getProperty("hibernate.connection.password");
		// password = "useradm";
		Connection conn = null;
		try {
			Class.forName(driver);
			conn = (Connection) DriverManager.getConnection(url, username, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
}
