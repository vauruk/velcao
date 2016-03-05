package br.com.vanderson.app;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.richfaces.component.SortOrder;
import org.richfaces.component.UIDataTable;

import br.com.vanderson.model.EntidadeApp;
import br.com.vanderson.model.Usuario;
import br.com.vanderson.view.LoginMB;

/**
 * Classe desenvolvida para facilitar e agilizar o desenvolvimento implementado
 * 
 * 
 * @author vanderson
 * 
 */
public abstract class MBGenerico   implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4451503459174406402L;
	protected SelectItem selecione = new SelectItem(null,messageProcessing("selecione", null, null));
	protected SelectItem selecioneInt = new SelectItem(0,"--");
	protected final Long ZERO = new Long(0);
	protected static final String NOME_VARIAVEL_NAVEGACAO = "textoNavegacaoSessao";
	protected static List<String> modules = new ArrayList <String>();
	protected EntidadeApp beanSelecionado = null;

	protected String patternData = DateUtil.pattern_DD_MM_yyyy;
	protected String patternDataTime = DateUtil.pattern_DD_MM_yyyy_hh_mm;
	protected String formatoSemanaData = "EEE dd/MM/yyyy";
	protected String localeDefault = "pt_BR";
	protected Date defatultTime = DateUtil.getHoje();

	private String contexto;
	private String pathReal;
	private String caminhoRelativoApp;
	protected String HOME = "home";
	protected String HOME_LOGIN = "login";
	protected SortOrder nomeOrder = SortOrder.unsorted;
	protected SortOrder idOrder = SortOrder.ascending;
	
	
	private int idUsuario = 0;
	
	protected int currentIndex;

	private int quantidadesRowsPorTabela = 10;

	private final String FORM_PRINCIPAL = "formGeral";

	private ResourceBundle bundle;
	
	
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

	/**
	 * Metodo responsavel por setar variavel de sessao, somente utilizar esse
	 * metodo quando nao haver situacao possivel de usar os MBs configurados no
	 * Faces-config.xml como escopo de Sessao.
	 * 
	 * @param nomeVariavelSessao
	 * @param objetoSessao
	 */
	public void setarVariavelDeSessao(String nomeVariavelSessao,
			Object objetoSessao) {
		this.getSession().setAttribute(nomeVariavelSessao, objetoSessao);
	}

	/**
	 * Seguir orientacao do metodo: -->> public void
	 * setarVariavelDeSessao(String nomeVariavelSessao, Object objetoSessao){}
	 * 
	 * @param nomeVariavelSessao
	 * @return
	 */
	public Object recuperarVariavelDeSessao(String nomeVariavelSessao) {
		return this.getSession().getAttribute(nomeVariavelSessao);

	}


	public HttpSession getSession() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(true);
		return session;

	}

	public HttpServletRequest getRequest() {
		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();
		return request;
	}

	public HttpServletResponse getResponse() {
		HttpServletResponse response = (HttpServletResponse) FacesContext
				.getCurrentInstance().getExternalContext().getResponse();
		return response;
	}

	public FacesContext getFacesContext() {
		return FacesContext.getCurrentInstance();
	}

	/**
	 * Implementacao para pegar o ROW atravez site
	 * http://forums.sun.com/thread.jspa?threadID=5331242
	 * 
	 * @param nomeForm
	 * @param idDataTable
	 * @return
	 */
	protected Object getRowData(String idDataTable) {

		UIComponent comp = getFacesContext().getViewRoot().findComponent(
				FORM_PRINCIPAL);
		UIDataTable compDataTable = (UIDataTable) comp
				.findComponent(idDataTable);

		return compDataTable.getRowData();
	}
	
	public ResourceBundle getResourceBundle() {
		if (bundle == null) {
			FacesContext context = this.getFacesContext();
			bundle = context.getApplication().getResourceBundle(context, "velcao");
		}
		
		return bundle;
	}

	/**
	 * Para gerar mensagem sem retorno, severety sempre deve ser nulo caso
	 * queira o retorno String para uma chave Bundle por exemplo deve passar a
	 * severety preenchida
	 * 
	 * FacesMessage.SEVERITY_ERROR referencia CSS name in StyleDefault.css =
	 * mensagemerro FacesMessage.SEVERITY_INFO referEncia CSS name in
	 * StyleDefault.css = mensagemsucesso FacesMessage.SEVERITY_WARN referencia
	 * CSS name in StyleDefault.css = mensagemalerta
	 * 
	 * @param message
	 *            Frase do bundle que tem lacunas a serem preenchidas pelas keys
	 *            complementares Ex: "Usuario {0} {1} com sucesso."
	 * @param keys
	 *            Parans pode se interpretar as chavez(texto bundle) que sao
	 *            necessarias para compor uma frase completa. Ex:
	 *            "Opcao {0} {1} executada  com sucesso." ...
	 *            "Opcao keys[0] keys [1] executada  com sucesso." ...
	 *            "Opcao "Gravar" "Usuario" executada com sucesso."
	 * @param severety
	 * @return
	 */
	public String messageProcessing(String message, String param[], Severity severety) {
		if(message == null) {
			message = "";
		}
		ResourceBundle bundle = getResourceBundle();
		boolean msgExist = bundle.containsKey(message);
		String msg = "";

		if (msgExist) {
			msg = bundle.getString(message);
		} else {
			msg = "Not Found key: " + "\"" + message + "\"";
		}

		if (param != null && msgExist) {
			String parans[] = this.getParams(param, severety);
			MessageFormat mf = new MessageFormat(msg, bundle.getLocale());
			msg = mf.format(parans, new StringBuffer(), null).toString();
		}
		if (severety != null) {
			FacesMessage facesMessage = new FacesMessage(msg);
			facesMessage.setSeverity(severety);
			// addMessage(String arg0, FacesMessage arg1)
			// arg0 == clientID = passar o clientID respectivo do JSF
			// arg1 == mensagem com sua severidade e descricao
			getFacesContext().addMessage(null, facesMessage);
			//getFacesContext().getExternalContext().log(msg);
			return "";
		}
		return msg;

	}

	public String formatarMensagem(String key, String params[]) {
		ResourceBundle bundle = getResourceBundle();

		String msg = bundle.getString(key);

		if (params != null) {
			MessageFormat mf = new MessageFormat(msg, bundle.getLocale());
			msg = mf.format(params, new StringBuffer(), null).toString();
		}
		return msg;

	}

	/**
	 * 
	 * @param keys
	 * @return
	 */
	private String[] getParams(String[] keys, Severity severety) {
		String[] params = new String[keys.length];
		String aspas = "\"";
		for (int i = 0; i < keys.length; i++) {
			try {
				String msg = "";
				if (getResourceBundle().containsKey(keys[i])) {
					msg = getResourceBundle().getString(keys[i]);
				} else {
					msg = keys[i];
				}

				if (severety == null)
					params[i] = msg;
				else
					params[i] = aspas + msg + aspas;

			} catch (MissingResourceException e) {
				params[i] = "\"" + keys[i] + "\"";
				// LOGGER.error( e.getMessage() );
			}
		}
		return params;
	}

	public String getLocaleDefault() {
		return localeDefault;
	}

	/**
	 * Devolve o context Path da aplicacao.
	 * 
	 * @return
	 */
	public String getContexto() {
		contexto = getSession().getServletContext().getContextPath();
		return contexto;
	}

	public void setContexto(String contexto) {
		this.contexto = contexto;
	}

	public String getCaminhoRelativoApp() {
		caminhoRelativoApp = getSession().getServletContext().getRealPath("/");
		return caminhoRelativoApp;
	}

	public void setCaminhoRelativoApp(String caminhoRelativoApp) {
		this.caminhoRelativoApp = caminhoRelativoApp;
	}
	
	public String getPathReal() {
		pathReal = getSession().getServletContext().getRealPath("/");
		return pathReal;
	}

	public void setPathReal(String pathReal) {
		this.pathReal = pathReal;
	}


	public String getFORM_PRINCIPAL() {
		return FORM_PRINCIPAL;
	}

	public int getQuantidadesRowsPorTabela() {
		return quantidadesRowsPorTabela;
	}

	public void setQuantidadesRowsPorTabela(int quantidadesRowsPorTabela) {
		this.quantidadesRowsPorTabela = quantidadesRowsPorTabela;
	}

	public String getFormatoSemanaData() {
		return formatoSemanaData;
	}

	public void setFormatoSemanaData(String formatoSemanaData) {
		this.formatoSemanaData = formatoSemanaData;
	}

	public String getPatternData( ) {
		return patternData;
	}

	public void setPatternData(String patternData) {
		this.patternData = patternData;
	}

	protected String redirectPage(final String page){
		return page+"?faces-redirect=true";
	}

	public EntidadeApp getBeanSelecionado( ) {
		return beanSelecionado;
	}

	public void setBeanSelecionado(EntidadeApp beanSelecionado) {
		this.beanSelecionado = beanSelecionado;
	}

	public int getCurrentIndex( ) {
		return currentIndex;
	}

	public void setCurrentIndex(int currentIndex) {
		this.currentIndex = currentIndex;
	}

	public Date getDefatultTime( ) {
		return defatultTime;
	}

	public void setDefatultTime(Date defatultTime) {
		this.defatultTime = defatultTime;
	}
	
	protected LoginMB getLoginMB() {
		LoginMB loginMB = (LoginMB) this.getSession().getAttribute("loginMB");
		return loginMB;
	}
	
	protected Usuario getUsuarioLogado(){
		return getLoginMB().getUsuarioLogado();
	}

	public int getIdUsuario( ) {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getPatternDataTime() {
		return patternDataTime;
	}

	public void setPatternDataTime(String patternDataTime) {
		this.patternDataTime = patternDataTime;
	}

}
