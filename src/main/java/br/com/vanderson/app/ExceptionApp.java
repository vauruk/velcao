package br.com.vanderson.app;


/**
 * Exceção base para o framework PK. Todas as mensagens de exceção devem ser
 * internacionalizadas, ou seja: Devem ser criadas exceções com códigos apenas, nunca
 * Strings literais.
 * 
 * @author vanderson
 */
public class ExceptionApp extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3376609644786305065L;
	/**
	 * Chave da exceção.
	 */
	private String key;
	/**
	 * módulo da exceção.
	 */
	private final String module = "web";
	/**
	 * Uma String de detalhe da exceção.
	 */
	private String detail = "";
	/**
	 * Cache da mensagem traduzida dessa exceção.
	 */
//	private String message = null;
	/**
	 * Array Default vazio.
	 */
	private static final Object[] EMPTY = new Object[] {};
	/**
	 * Parametros para mensagem.
	 */
	private Object[] params = EMPTY;
	/**
	 * Uma causa inicial para esta exceção. Por exemplo, uma NullPointerException
	 */
	private Throwable cause;
	
	public ExceptionApp(String key) {
		this.key = key;
	}

	/**
	 * Inicializa a exceção com uma chave e uma causa.
	 * 
	 * @param key Chave da exceção            
	 * @param cause Causa da exceção
	 */
	public ExceptionApp(String key, Throwable cause) {
		super(key,cause);
		this.key = key;
		this.cause = cause;
	}

	/**
	 * 
	 * Inicializa a exceção com uma chave e uma causa e parametros para substituição.
	 * @param key Chave da exceção            
	 * @param cause Causa da exceção
	 * @param objects Parâmetros a serem substituidos
	 */
	public ExceptionApp(String key, Throwable cause, Object[] objects) {
		this(key, cause);
		this.params = objects.clone();
	}

	/**
	 * Inicializa a exceção com uma chave e parametros para substituição.
	 * @param key Chave da exceção            
	 * @param objects Parâmetros a serem substituidos
	 */
	public ExceptionApp(String key, Object[] objects) {
		super(key);
		this.params = objects.clone();
	}

	/**
	 * Retorna uma String com a causa inicial cause, ou vazio caso não exista uma.
	 * 
	 * @return string de causa
	 */
	public String getParentCause( ) {
		if (cause == null) {
			return "";
		}
		return " - " + cause.getClass().getName() 
				+ (cause.getMessage() !=null ? " " + cause.getMessage() :"");
	}
	/**
	 * @return key
	 */
	public String getKey( ) {
		return key;
	}

	/**
	 * @param key
	 *            set key
	 */
	public void setKey(String key) {
	}

	/**
	 * @return module
	 */
	public String getModule( ) {
		return module;
	}

	/**
	 * @param module
	 *            set module
	 */
	public void setModule(String module) {
	}

	/**
	 * @return params
	 */
	public Object[] getParams( ) {
		return params;
	}

	/**
	 * @param params
	 *            set params
	 */
	public void setParams(Object[] params) {
		this.params = params.clone();
	}

	/**
	 * @param detail
	 *            set detail
	 */
	public void setDetail(String detail) {
	}

	/**
	 * @return detail
	 */
	public String getDetail( ) {
		return detail;
	}
	

}
